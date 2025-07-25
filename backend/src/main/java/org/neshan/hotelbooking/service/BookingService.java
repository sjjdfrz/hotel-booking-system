package org.neshan.hotelbooking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.BookingDTO;
import org.neshan.hotelbooking.model.dto.BookingRequest;
import org.neshan.hotelbooking.model.entity.Booking;
import org.neshan.hotelbooking.model.entity.Room;
import org.neshan.hotelbooking.model.entity.User;
import org.neshan.hotelbooking.model.enumeration.BookingStatus;
import org.neshan.hotelbooking.model.factory.BookingDTOFactory;
import org.neshan.hotelbooking.repository.BookingRepository;
import org.neshan.hotelbooking.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService {
    RoomRepository roomRepository;
    BookingRepository bookingRepository;

    @Scheduled(fixedRate = 60000)
    public void expirePendingBookings() {
        List<Booking> expired = bookingRepository.findAllByStatusAndPendingExpiresAtBefore(BookingStatus.PENDING, LocalDateTime.now());
        for (Booking booking : expired) {
            booking.setStatus(BookingStatus.AVAILABLE);
            booking.setPendingExpiresAt(null);
            bookingRepository.save(booking);
        }
    }

    public ApiResponse<BookingDTO> bookRoom(BookingRequest request, User user) {
        var roomOptional = roomRepository.getAvailableRoomById(
                request.getRoomId(),
                request.getDateFrom(),
                request.getDateTo(),
                user);

        if (roomOptional.isEmpty()) {
            return ApiResponse.<BookingDTO>builder()
                    .message("اتاقی برای تاریخ‌های انتخاب‌ شده یافت نشد یا در دسترس نیست!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

        Room room = roomOptional.get();

        Random random = new Random();
        int paymentCode = 100000 + random.nextInt(900000);

        int nightsCount = Math.toIntExact(ChronoUnit.DAYS.between(request.getDateFrom(), request.getDateTo()));
        double totalAmount = room.getPricePerNight() * nightsCount;

        Booking booking = Booking.builder()
                .room(room)
                .checkInDate(request.getDateFrom())
                .checkOutDate(request.getDateTo())
                .user(user)
                .status(BookingStatus.PENDING)
                .pendingExpiresAt(LocalDateTime.now().plusMinutes(15))
                .paymentCode(paymentCode)
                .price(totalAmount)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        return ApiResponse.<BookingDTO>builder()
                .data(BookingDTOFactory.fromEntity(savedBooking))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ApiResponse<BookingDTO> getBooking(Long bookingId) {
        var bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return ApiResponse.<BookingDTO>builder()
                    .message("رزروی یافت نشد!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ApiResponse.<BookingDTO>builder()
                .data(BookingDTOFactory.fromEntity(bookingOptional.get()))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ApiResponse<List<BookingDTO>> getCurrentUserBookings(User user) {
        List<Booking> bookings = bookingRepository.findAllByUserAndStatusAndCheckInDateAfter(user, BookingStatus.BOOKED, LocalDateTime.now());

        List<BookingDTO> bookingDTOs = bookings.stream()
                .map(BookingDTOFactory::fromEntity)
                .collect(Collectors.toList());

        return ApiResponse.<List<BookingDTO>>builder()
                .data(bookingDTOs)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ApiResponse<List<BookingDTO>> getPastUserBookings(User user) {
        List<Booking> bookings = bookingRepository.findAllByUserAndStatusAndCheckInDateBefore(user, BookingStatus.BOOKED, LocalDateTime.now());

        List<BookingDTO> bookingDTOs = bookings.stream()
                .map(BookingDTOFactory::fromEntity)
                .collect(Collectors.toList());

        return ApiResponse.<List<BookingDTO>>builder()
                .data(bookingDTOs)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}

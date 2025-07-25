package org.neshan.hotelbooking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.RoomResponse;
import org.neshan.hotelbooking.model.dto.RoomRequest;
import org.neshan.hotelbooking.model.entity.Hotel;
import org.neshan.hotelbooking.model.entity.Room;
import org.neshan.hotelbooking.model.entity.User;
import org.neshan.hotelbooking.model.factory.RoomResponseFactory;
import org.neshan.hotelbooking.repository.HotelRepository;
import org.neshan.hotelbooking.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService {
    RoomRepository roomRepository;
    HotelRepository hotelRepository;

    public ApiResponse<RoomResponse> getRooms(RoomRequest request, User user) {
        List<Room> rooms = roomRepository.getAvailableRoomsByHotel(
                request.getHotelId(),
                request.getDateFrom(),
                request.getDateTo(),
                user);

        var hotelOptional = hotelRepository.findById(request.getHotelId());
        if (hotelOptional.isEmpty()) {
            return ApiResponse.<RoomResponse>builder()
                    .message("هتلی یافت نشد!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

        Hotel hotel = hotelOptional.get();

        int nightsCount = Math.toIntExact(ChronoUnit.DAYS.between(request.getDateFrom(), request.getDateTo()));

        return ApiResponse.<RoomResponse>builder()
                .data(RoomResponseFactory.fromEntities(rooms, hotel, nightsCount))
                .httpStatus(HttpStatus.OK)
                .build();
    }
}

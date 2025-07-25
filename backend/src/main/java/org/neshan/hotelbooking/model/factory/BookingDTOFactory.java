package org.neshan.hotelbooking.model.factory;

import org.neshan.hotelbooking.model.dto.BookingDTO;
import org.neshan.hotelbooking.model.entity.Booking;

import java.time.temporal.ChronoUnit;

public class BookingDTOFactory {

    public static BookingDTO fromEntity(Booking booking) {
        int nightsCount = Math.toIntExact(ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate()));

        return BookingDTO.builder()
                .id(booking.getId())
                .paymentCode(booking.getPaymentCode())
                .firstName(booking.getUser().getFirstName())
                .lastName(booking.getUser().getLastName())
                .roomTitle(booking.getRoom().getTitle())
                .roomCapacity(booking.getRoom().getCapacity())
                .price(booking.getPrice())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .status(booking.getStatus())
                .nightCount(nightsCount)
                .hotelStars(booking.getRoom().getHotel().getStars())
                .hotelTitle(booking.getRoom().getHotel().getName())
                .authority(booking.getAuthority())
                .refId(booking.getRefId())
                .build();
    }
} 

package org.neshan.hotelbooking.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.model.enumeration.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDTO {
    long id;
    long paymentCode;
    String firstName;
    String lastName;
    String roomTitle;
    int roomCapacity;
    double price;
    String hotelTitle;
    int hotelStars;
    LocalDateTime checkInDate;
    LocalDateTime checkOutDate;
    BookingStatus status;
    int nightCount;

    // Payment-related fields
    String authority;
    Long refId;
}

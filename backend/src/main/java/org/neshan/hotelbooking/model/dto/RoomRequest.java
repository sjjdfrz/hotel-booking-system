package org.neshan.hotelbooking.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.validation.DateRange;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@DateRange
public class RoomRequest {
    @NotNull(message = "شناسه هتل الزامی است.")
    @Positive(message = "شناسه هتل باید یک عدد مثبت باشد.")
    Long hotelId;

    @NotNull(message = "تاریخ ورود الزامی است.")
    @Future(message = "تاریخ ورود باید در آینده باشد.")
    LocalDateTime dateFrom;

    @NotNull(message = "تاریخ خروج الزامی است.")
    @Future(message = "تاریخ خروج باید در آینده باشد.")
    LocalDateTime dateTo;
}

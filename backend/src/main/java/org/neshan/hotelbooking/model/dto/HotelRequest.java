package org.neshan.hotelbooking.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.validation.DateRange;
import org.neshan.hotelbooking.validation.PriceRange;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@DateRange
@PriceRange
public class HotelRequest {
    @NotBlank(message = "نام شهر الزامی است.")
    @Size(min = 2, max = 100, message = "نام شهر باید بین ۲ تا ۱۰۰ کاراکتر باشد.")
    String city;

    @Min(value = 1, message = "تعداد ستاره‌ها باید حداقل ۱ باشد.")
    @Max(value = 5, message = "تعداد ستاره‌ها نباید بیشتر از ۵ باشد.")
    Integer stars;

    @PositiveOrZero(message = "حداقل قیمت باید عددی مثبت یا صفر باشد.")
    Double priceFrom;

    @Positive(message = "حداکثر قیمت باید عددی مثبت باشد.")
    Double priceTo;

    @NotNull(message = "تاریخ ورود الزامی است.")
    @Future(message = "تاریخ ورود باید در آینده باشد.")
    LocalDateTime dateFrom;

    @NotNull(message = "تاریخ خروج الزامی است.")
    @Future(message = "تاریخ خروج باید در آینده باشد.")
    LocalDateTime dateTo;

    @Builder.Default
    String sort = "price";
}

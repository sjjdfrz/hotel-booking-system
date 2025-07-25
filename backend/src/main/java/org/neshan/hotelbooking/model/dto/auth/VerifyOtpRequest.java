package org.neshan.hotelbooking.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyOtpRequest {

    @NotBlank(message = "وارد کردن ایمیل الزامی است.")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "فرمت ایمیل نامعتبر است."
    )
    String email;

    @NotBlank(message = "وارد کردن کد یک‌بار مصرف الزامی است.")
    @Size(min = 6, max = 6, message = "کد یک‌بار مصرف باید ۶ رقم باشد.")
    String code;
}
package org.neshan.hotelbooking.model.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @NotBlank(message = "وارد کردن نام الزامی است.")
    String firstName;

    @NotBlank(message = "وارد کردن نام خانوادگی الزامی است.")
    String lastName;

    @NotBlank(message = "وارد کردن ایمیل الزامی است.")
    @Email(message = "فرمت ایمیل نامعتبر است.")
    String email;

    @NotBlank(message = "وارد کردن رمز عبور الزامی است.")
    @Size(min = 8, message = "رمز عبور باید حداقل ۸ کاراکتر باشد.")
    String password;

    @NotBlank(message = "وارد کردن شماره موبایل الزامی است.")
    @Pattern(
            regexp = "^09\\d{9}$",
            message = "شماره موبایل باید با 09 شروع شده و 11 رقم باشد."
    )
    String phoneNumber;
}
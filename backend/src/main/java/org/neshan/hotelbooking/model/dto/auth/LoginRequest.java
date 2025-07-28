package org.neshan.hotelbooking.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @NotBlank(message = "وارد کردن ایمیل الزامی است.")
    @Email(message = "فرمت ایمیل نامعتبر است.")
    String email;

    @NotBlank(message = "وارد کردن رمز عبور الزامی است.")
    @Size(min = 8, message = "رمز عبور باید حداقل ۸ کاراکتر باشد.")
    String password;
}
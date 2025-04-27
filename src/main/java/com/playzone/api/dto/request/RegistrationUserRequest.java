package com.playzone.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    @Email(message = "Некорректный формат email. Пример: example@mail.com")
    private String email;
}

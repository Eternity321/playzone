package com.playzone.api.dto.request;

import lombok.Data;

@Data
public class RegistrationUserRequest {
    private String username;
    private String nickname;
    private String password;
    private String confirmPassword;
    private String email;
}

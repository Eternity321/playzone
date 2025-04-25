package com.playzone.api.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String nickname;
    private String email;
    private String password;
}
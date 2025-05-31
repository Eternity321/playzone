package com.playzone.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String nickname;
    private String avatarKey;
    private String username;
    private String email;
    private List<String> roles;
}

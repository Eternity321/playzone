package com.playzone.api.dto.mapper;


import com.playzone.api.dto.request.UserRequest;
import com.playzone.api.dto.response.UserResponse;
import com.playzone.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setNickname(userRequest.getNickname());
        return user;
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setNickname(user.getNickname());
        response.setAvatarKey(user.getUserAvatarKey());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRoles(
                user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList())
        );
        return response;
    }
}


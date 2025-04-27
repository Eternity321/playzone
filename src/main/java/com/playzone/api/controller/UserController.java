package com.playzone.api.controller;

import com.playzone.api.dto.mapper.UserMapper;
import com.playzone.api.dto.response.UserResponse;
import com.playzone.model.User;
import com.playzone.service.AuthService;
import com.playzone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;

    @Operation(summary = "Получение списка всех пользователей (ADMIN)")
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        List<UserResponse> response = StreamSupport.stream(users.spliterator(), false)
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получение профиля авторизованного пользователя (USER)")
    @Secured("ROLE_USER")
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        String username = authService.getCurrentUsername();
        User user = userService.getUserProfile(username);
        UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получение информации о пользователе по его ID (ADMIN)")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Смена никнейма пользователя (USER)")
    @Secured("ROLE_USER")
    @PutMapping("/nickname")
    public ResponseEntity<UserResponse> updateNickname(@RequestParam String newNickname) {
        String username = authService.getCurrentUsername();
        User updated = userService.updateNickname(username, newNickname);
        UserResponse response = userMapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Смена электронной почты пользователя (USER)")
    @Secured("ROLE_USER")
    @PutMapping("/email")
    public ResponseEntity<UserResponse> updateEmail(@RequestParam String newEmail) {
        String username = authService.getCurrentUsername();
        User updated = userService.updateEmail(username, newEmail);
        UserResponse response = userMapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Смена пароля пользователя (USER)")
    @Secured("ROLE_USER")
    @PutMapping("/password")
    public ResponseEntity<UserResponse> updatePassword(@RequestParam String currentPassword,
                                                       @RequestParam String newPassword) {
        String username = authService.getCurrentUsername();
        userService.changePassword(username, currentPassword, newPassword);
        User user = userService.getUserProfile(username);
        UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }



    @Operation(summary = "Добавить аватар пользователя (USER)")
    @Secured("ROLE_USER")
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadUserAvatar(@RequestParam("file") MultipartFile file) {
        String username = authService.getCurrentUsername();
        String avatarUrl = userService.uploadUserAvatar(username, file);
        return ResponseEntity.ok(avatarUrl);
    }

    @Operation(summary = "Добавить роль администратора пользователю (ADMIN)")
    @Secured("ROLE_ADMIN")
    @PostMapping("/{username}/addAdminRole")
    public ResponseEntity<UserResponse> addAdminRoleToUser(@RequestParam String username) {
        userService.addAdminRoleToUser(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Убрать роль администратора пользователю (ADMIN)")
    @Secured("ROLE_ADMIN")
    @PostMapping("/{username}/remove-admin")
    public ResponseEntity<String> removeAdminRole(@PathVariable String username) {
        userService.removeAdminRole(username);
        return ResponseEntity.ok("У пользователя " + username + " снята роль администратора");
    }

    @Operation(summary = "Заблокировать пользователя (ADMIN)")
    @Secured("ROLE_ADMIN")
    @PostMapping("/{username}/ban")
    public ResponseEntity<String> banUser(@PathVariable String username) {
        userService.banUser(username);
        return ResponseEntity.ok("Пользователь " + username + " забанен");
    }

    @Operation(summary = "Разблокировать пользователя (ADMIN)")
    @Secured("ROLE_ADMIN")
    @PostMapping("/{username}/unban")
    public ResponseEntity<String> unbanUser(@PathVariable String username) {
        userService.unbanUser(username);
        return ResponseEntity.ok("Пользователь " + username + " разбанен");
    }

    @Operation(summary = "Получить всех пользователей по роли (ADMIN)")
    @Secured("ROLE_ADMIN")
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        List<User> users = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(users);
    }
}
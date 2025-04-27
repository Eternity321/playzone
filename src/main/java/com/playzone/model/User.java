package com.playzone.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Schema(description = "Сущность пользователя")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Schema(description = "ID пользователя", example = "35")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Schema(description = "Nickname пользователя", example = "Власов Антон")
    @Column(name = "nickname")
    private String nickname;

    @Schema(description = "Ключ аватара пользователя")
    @Column(name = "user_avatar_key")
    private String userAvatarKey;

    @Schema(description = "Логин пользователя", example = "Vasya2007")
    @Column(name = "username")
    private String username;

    @Schema(description = "Электронная почта пользователя", example = "vasya@example.com")
    @Column(name = "email")
    private String email;

    @Schema(description = "Пароль пользователя", example = "password123")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return roles.stream().noneMatch(role -> "ROLE_BANNED".equals(role.getName()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

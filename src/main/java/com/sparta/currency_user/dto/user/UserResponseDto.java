package com.sparta.currency_user.dto.user;

import com.sparta.currency_user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id; // 사용자 고유 식별자

    private String name; // 사용자 이름
    private String email; // 사용자 이메일

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public UserResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}

package com.sparta.currency_user.dto.user;

import com.sparta.currency_user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank(message = "이름을 입력하세요")
    private String name;// 사용자 이름
    @Email(message = "이메일을 입력하세요")
    private String email; // 사용자 이메일

    public User toEntity() {
        return new User(
                this.name,
                this.email
        );
    }
}

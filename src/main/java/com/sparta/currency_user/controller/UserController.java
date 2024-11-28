package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.user.UserRequestDto;
import com.sparta.currency_user.dto.user.UserResponseDto;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping // 전체 사용자 조회
    public ResponseEntity<List<UserResponseDto>> findUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{id}") // 단건 사용자 조회
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping("signup") // 회원 가입
    public ResponseEntity<UserResponseDto> createUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok().body(userService.save(userRequestDto));
    }

    @DeleteMapping("/{id}") // 로그인한 사용자 id와 요청한 id가 일치할 때 삭제
    public ResponseEntity<String> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long sessionKey = (Long) session.getAttribute("SESSION_KEY");

        if (!sessionKey.equals(id)) { // 일치 하지않으면
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }
        userService.deleteUserById(id); // 일치하면 삭제
        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }

    @PostMapping("/login") // 로그인
    public ResponseEntity<Void> login(@Validated @RequestBody UserRequestDto requestDto, HttpServletRequest request) {
        User user = userService.login(requestDto);
        HttpSession session = request.getSession();
        session.setAttribute("SESSION_KEY", user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/logout") //로그아웃
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

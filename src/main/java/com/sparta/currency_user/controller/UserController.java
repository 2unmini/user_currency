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

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok().body(userService.save(userRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Validated @RequestBody UserRequestDto requestDto, HttpServletRequest request) {
        User user = userService.login(requestDto);
        HttpSession session = request.getSession();
        session.setAttribute("SESSION_KEY",user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session!=null){
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.currency.*;
import com.sparta.currency_user.service.CurrencyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyResponseDto>> findCurrencies() {
        return ResponseEntity.ok().body(currencyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponseDto> findCurrency(@PathVariable Long id) {
        return ResponseEntity.ok().body(currencyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CurrencyResponseDto> createCurrency(@Validated @RequestBody CurrencyRequestDto currencyRequestDto) {
        return ResponseEntity.ok().body(currencyService.save(currencyRequestDto));
    }
    @PostMapping("/{id}/exchange") // 환전 요청
    public ResponseEntity<ChangeCurrencyResponseDto> changeCurrency(@PathVariable Long id , @Validated @RequestBody ChangeCurrencyRequestDto currencyRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"세션이 없습니다");
        }
        Long userId = (Long) session.getAttribute("SESSION_KEY");

        ChangeCurrencyResponseDto changeCurrencyResponseDto = currencyService.chargeCurrency(id, userId, currencyRequestDto);
        return new ResponseEntity<>(changeCurrencyResponseDto, HttpStatus.OK);
    }
    @GetMapping("/history")
    public ResponseEntity<List<ChangeCurrencyHistoryResponseDto>>  checkHistory(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"세션이 없습니다.");
        }
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        List<ChangeCurrencyHistoryResponseDto> changeCurrencyHistory = currencyService.checkHistory(userId);
        return new ResponseEntity<>(changeCurrencyHistory,HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public void cancelChangeCurrency(@PathVariable Long id ,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"세션이 없습니다.");
        }
        currencyService.cancel(id);
    }
}

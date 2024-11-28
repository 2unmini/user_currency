package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.currency.ChangeCurrencyHistoryResponseDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyRequestDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyResponseDto;
import com.sparta.currency_user.dto.currency.TotalUserCurrencyResponseDto;
import com.sparta.currency_user.service.CurrencyExchangeService;
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
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyExchangeService currencyExchangeService;
    private final UserService userService;

    @PostMapping("/{currencyId}/exchange") // 환전 요청
    public ResponseEntity<ChangeCurrencyResponseDto> changeCurrency(@PathVariable Long currencyId, @Validated @RequestBody ChangeCurrencyRequestDto currencyRequestDto, HttpServletRequest request) {
        Long userId = getSessionKey(request);

        ChangeCurrencyResponseDto changeCurrencyResponseDto = currencyExchangeService.chargeCurrency(currencyId, userId, currencyRequestDto);
        return new ResponseEntity<>(changeCurrencyResponseDto, HttpStatus.OK);
    }

    @GetMapping("/history") //로그인한 사용자의 내역
    public ResponseEntity<List<ChangeCurrencyHistoryResponseDto>> checkHistory(HttpServletRequest request) {
        Long userId = getSessionKey(request);
        List<ChangeCurrencyHistoryResponseDto> changeCurrencyHistory = currencyExchangeService.checkHistory(userId);
        return new ResponseEntity<>(changeCurrencyHistory, HttpStatus.OK);
    }

    @PatchMapping("{receptionId}") // 환전 요청 취소
    public void cancelChangeCurrency(@PathVariable Long receptionId, HttpServletRequest request) {
        Long userId = getSessionKey(request);
        currencyExchangeService.cancel(receptionId, userId);
    }

    @GetMapping("/total") // 요청 완료된 전체 내역
    public ResponseEntity<List<TotalUserCurrencyResponseDto>> checkTotal(HttpServletRequest request) {
        Long userId = getSessionKey(request);
        List<TotalUserCurrencyResponseDto> totalUserCurrencyResponseDtos = currencyExchangeService.checkTotal(userId);
        return new ResponseEntity<>(totalUserCurrencyResponseDtos, HttpStatus.OK);
    }

    private static Long getSessionKey(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("SESSION_KEY");
    }


}

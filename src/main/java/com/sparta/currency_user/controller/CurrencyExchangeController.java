package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.currency.ChangeCurrencyHistoryResponseDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyRequestDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyResponseDto;
import com.sparta.currency_user.dto.currency.TotalUserCurrencyResponseDto;
import com.sparta.currency_user.service.CurrencyExchangeService;
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
public class CurrencyExchangeController {
    private final CurrencyExchangeService currencyExchangeService;

    @PostMapping("/{id}/exchange") // 환전 요청
    public ResponseEntity<ChangeCurrencyResponseDto> changeCurrency(@PathVariable Long id , @Validated @RequestBody ChangeCurrencyRequestDto currencyRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"세션이 없습니다");
        }
        Long userId = (Long) session.getAttribute("SESSION_KEY");

        ChangeCurrencyResponseDto changeCurrencyResponseDto = currencyExchangeService.chargeCurrency(id, userId, currencyRequestDto);
        return new ResponseEntity<>(changeCurrencyResponseDto, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChangeCurrencyHistoryResponseDto>>  checkHistory(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"세션이 없습니다.");
        }
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        List<ChangeCurrencyHistoryResponseDto> changeCurrencyHistory = currencyExchangeService.checkHistory(userId);
        return new ResponseEntity<>(changeCurrencyHistory,HttpStatus.OK);
    }
    @PatchMapping("{currencyId}")
    public void cancelChangeCurrency(@PathVariable Long currencyId ,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"세션이 없습니다.");
        }
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        currencyExchangeService.cancel(currencyId,userId);
    }
    @GetMapping("/total")
    public ResponseEntity<List<TotalUserCurrencyResponseDto>> checkTotal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"세션이 없습니다");
        }
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        List<TotalUserCurrencyResponseDto> totalUserCurrencyResponseDtos = currencyExchangeService.checkTotal(userId);
        return new ResponseEntity<>(totalUserCurrencyResponseDtos,HttpStatus.OK);
    }


}

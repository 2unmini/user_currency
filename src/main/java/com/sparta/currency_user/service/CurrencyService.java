package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.currency.*;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.repository.CurrencyExchangeRepository;
import com.sparta.currency_user.repository.CurrencyRepository;
import com.sparta.currency_user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final UserService userService;
    private final CurrencyRepository currencyRepository;
    private final CurrencyExchangeRepository currencyExchangeRepository;

    public CurrencyResponseDto findById(Long id) {
        return new CurrencyResponseDto(findCurrencyById(id));
    }

    public Currency findCurrencyById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("통화를 찾을 수 없습니다."));
    }

    public List<CurrencyResponseDto> findAll() {
        return currencyRepository.findAll().stream().map(CurrencyResponseDto::toDto).toList();
    }

    @Transactional
    public CurrencyResponseDto save(CurrencyRequestDto currencyRequestDto) {
        Currency savedCurrency = currencyRepository.save(currencyRequestDto.toEntity());
        return new CurrencyResponseDto(savedCurrency);
    }
    @Transactional
    public ChangeCurrencyResponseDto chargeCurrency(Long id,Long userId, ChangeCurrencyRequestDto currencyRequestDto) {
        Currency currency = currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("통화를 찾을수 없습니다"));
        User user = userService.findUserById(userId);
        CurrencyExchange currencyExchange = currencyExchangeRepository.save(new CurrencyExchange(user, currency, currencyRequestDto));
        return new ChangeCurrencyResponseDto(currencyExchange);


    }

    public List<ChangeCurrencyHistoryResponseDto> checkHistory(Long userId) {
        List<CurrencyExchange> history = currencyExchangeRepository.findCurrencyExchangeByUser_Id(userId);
        return history.stream().map(ChangeCurrencyHistoryResponseDto::todto).toList();

    }
@Transactional
    public void cancel(Long currencyId,Long userId) {
    User user = userService.findUserById(userId);
    CurrencyExchange currencyExchange = currencyExchangeRepository.findById(currencyId).orElseThrow(EntityNotFoundException::new);
    if(!user.getId().equals(currencyExchange.getUser().getId())){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"권한이 없습니다");
    }
        currencyExchange.cancel();
    }

    public List<TotalUserCurrencyResponseDto> checkTotal(Long userId) {
        User user = userService.findUserById(userId);
        CurrencyExchange currencyExchange = currencyExchangeRepository.findById(user.getId()).orElseThrow();//이건 일단 이상함 보류
        return currencyExchangeRepository.findUserCurrencyAndAmount(user.getId(),currencyExchange.getStatus());
    }
}

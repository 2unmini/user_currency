package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.currency.ChangeCurrencyHistoryResponseDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyRequestDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyResponseDto;
import com.sparta.currency_user.dto.currency.TotalUserCurrencyResponseDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.Status;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CurrencyExchangeService {
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final CurrencyService currencyService;
    private final UserService userService;


    @Transactional
    public ChangeCurrencyResponseDto chargeCurrency(Long id, Long userId, ChangeCurrencyRequestDto currencyRequestDto) {
        Currency currency = currencyService.findCurrencyById(id);
        User user = userService.findUserById(userId);
        CurrencyExchange currencyExchange = currencyExchangeRepository.save(new CurrencyExchange(user, currency, currencyRequestDto));
        return new ChangeCurrencyResponseDto(currencyExchange);
    }

    @Transactional
    public List<ChangeCurrencyHistoryResponseDto> checkHistory(Long userId) {
        List<CurrencyExchange> history = currencyExchangeRepository.findCurrencyExchangeByUser_Id(userId);
        return history.stream().map(ChangeCurrencyHistoryResponseDto::todto).toList();

    }

    @Transactional
    public void cancel(Long receptionId, Long userId) {
        User user = userService.findUserById(userId);
        CurrencyExchange currencyExchange = currencyExchangeRepository.findById(receptionId).orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다"));
        if (!user.getId().equals(currencyExchange.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
        }
        currencyExchange.cancel();
    }

    @Transactional
    public List<TotalUserCurrencyResponseDto> checkTotal(Long userId) {
        User user = userService.findUserById(userId);
        currencyExchangeRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다"));
        return currencyExchangeRepository.findUserCurrencyAndAmount(user.getId(), Status.NORMAL);
    }

}

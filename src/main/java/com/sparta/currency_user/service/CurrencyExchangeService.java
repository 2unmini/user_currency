package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.currency.ChangeCurrencyHistoryResponseDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyRequestDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyResponseDto;
import com.sparta.currency_user.dto.currency.TotalUserCurrencyResponseDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.repository.CurrencyExchangeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Service
public class CurrencyExchangeService {
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final CurrencyService currencyService;
    private final UserService userService;


    @Transactional
    public ChangeCurrencyResponseDto chargeCurrency(Long id, Long userId, ChangeCurrencyRequestDto currencyRequestDto) {
        Currency currency = currencyService.findCurrencyById(id);
        /*Currency currency = currencyExchangeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("통화를 찾을수 없습니다"));*/
        User user = userService.findUserById(userId);
        CurrencyExchange currencyExchange = currencyExchangeRepository.save(new CurrencyExchange(user, currency, currencyRequestDto));
        return new ChangeCurrencyResponseDto(currencyExchange);
    }

    /*
    =====
     */

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

package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.currency.ChangeCurrencyRequestDto;
import com.sparta.currency_user.dto.currency.ChangeCurrencyResponseDto;
import com.sparta.currency_user.dto.currency.CurrencyRequestDto;
import com.sparta.currency_user.dto.currency.CurrencyResponseDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.repository.CurrencyExchangeRepository;
import com.sparta.currency_user.repository.CurrencyRepository;
import com.sparta.currency_user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final UserRepository userRepository;
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
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        CurrencyExchange currencyExchange = currencyExchangeRepository.save(new CurrencyExchange(user, currency, currencyRequestDto));
        return new ChangeCurrencyResponseDto(currencyExchange);


    }
}

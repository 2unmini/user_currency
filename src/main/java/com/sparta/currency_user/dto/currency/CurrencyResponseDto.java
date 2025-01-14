package com.sparta.currency_user.dto.currency;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.CurrencyName;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyResponseDto {

    private Long id; // 통화 식별자
    private CurrencyName currencyName; //통화 이름
    private BigDecimal exchangeRate; // 환율
    private String symbol; // 통화 기호

    public CurrencyResponseDto(Currency currency) {
        this.id = currency.getId();
        this.currencyName = currency.getCurrencyName();
        this.exchangeRate = currency.getExchangeRate();
        this.symbol = currency.getSymbol();
    }

    public CurrencyResponseDto(Long id, CurrencyName currencyName, BigDecimal exchangeRate, String symbol) {
        this.id = id;
        this.currencyName = currencyName;
        this.exchangeRate = exchangeRate;
        this.symbol = symbol;
    }

    public static CurrencyResponseDto toDto(Currency currency) {
        return new CurrencyResponseDto(
                currency.getId(),
                currency.getCurrencyName(),
                currency.getExchangeRate(),
                currency.getSymbol()
        );
    }
}

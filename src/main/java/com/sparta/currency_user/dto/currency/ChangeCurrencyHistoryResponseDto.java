package com.sparta.currency_user.dto.currency;

import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.Status;
import lombok.Getter;

@Getter
public class ChangeCurrencyHistoryResponseDto {
    private Long id; // 환전 접수 식별자
    private Long preExchangeAmount; // 환전 전 금액
    private String postExchangeAmount; // 환전 후 금액
    private Status status; // 요청 상태

    public ChangeCurrencyHistoryResponseDto(CurrencyExchange currencyExchange) {
        this.id = currencyExchange.getId();
        this.preExchangeAmount = currencyExchange.getPreExchangeAmount();
        this.postExchangeAmount = currencyExchange.getPostExchangeAmount() + currencyExchange.getCurrency().getSymbol();
        this.status = currencyExchange.getStatus();
    }

    public static ChangeCurrencyHistoryResponseDto todto(CurrencyExchange currencyExchange) {
        return new ChangeCurrencyHistoryResponseDto(currencyExchange);
    }
}

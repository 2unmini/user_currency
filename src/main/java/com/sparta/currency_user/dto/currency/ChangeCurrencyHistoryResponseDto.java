package com.sparta.currency_user.dto.currency;

import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.Status;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ChangeCurrencyHistoryResponseDto {
    private Long id;
    private Long preExchangeAmount;
    private BigDecimal postExchangeAmount;
    private Status status;

    public ChangeCurrencyHistoryResponseDto() {
    }

    public ChangeCurrencyHistoryResponseDto(Long id, Long preExchangeAmount, BigDecimal postExchangeAmount, Status status) {
        this.id = id;
        this.preExchangeAmount = preExchangeAmount;
        this.postExchangeAmount = postExchangeAmount;
        this.status = status;
    }

    public static ChangeCurrencyHistoryResponseDto todto(CurrencyExchange currencyExchange) {
        return new ChangeCurrencyHistoryResponseDto(currencyExchange.getId(),currencyExchange.getPreExchangeAmount(),currencyExchange.getPostExchangeAmount(),currencyExchange.getStatus());
    }
}

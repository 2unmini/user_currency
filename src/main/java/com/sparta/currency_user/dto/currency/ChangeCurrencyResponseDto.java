package com.sparta.currency_user.dto.currency;

import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.Status;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ChangeCurrencyResponseDto {
    private Long preExchangeAmount; //환전 전 금액
    private BigDecimal postExchangeAmount; //환전 후 금액
    private Status status; // 환전 요청 상태

    public ChangeCurrencyResponseDto(CurrencyExchange currencyExchange) {
        this.preExchangeAmount = currencyExchange.getPreExchangeAmount();
        this.postExchangeAmount = currencyExchange.getPostExchangeAmount();
        this.status = currencyExchange.getStatus();
    }
}

package com.sparta.currency_user.dto.currency;

import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.Status;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class ChangeCurrencyResponseDto {
    private Long preExchangeAmount;
    private BigDecimal postExchangeAmount;
    private Status status;

    public ChangeCurrencyResponseDto(CurrencyExchange currencyExchange) {
        this.preExchangeAmount=currencyExchange.getPreExchangeAmount();
        this.postExchangeAmount=currencyExchange.getPostExchangeAmount();
        this.status=currencyExchange.getStatus();
    }
}

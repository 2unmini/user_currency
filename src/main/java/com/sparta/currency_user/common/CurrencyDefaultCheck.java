package com.sparta.currency_user.common;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyDefaultCheck {
    private final CurrencyRepository currencyRepository;

    /*
    curreny테이블에 유효하지 않은 환율이 들어갔다면 로그를 출력
     */
    @PostConstruct
    public void init() {
        List<Currency> currencys = currencyRepository.findAll();
        Stream<Currency> currencyStream = currencys.stream().filter(currency -> currency.getExchangeRate().intValue() <= 0);
        currencyStream.forEach(currency -> log.info("유효하지 않은 환율 입니다 통화 코드:{},환율:{}", currency.getSymbol(), currency.getExchangeRate()));
    }
}

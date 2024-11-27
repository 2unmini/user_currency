package com.sparta.currency_user.repository;

import com.sparta.currency_user.dto.currency.ChangeCurrencyHistoryResponseDto;
import com.sparta.currency_user.dto.currency.TotalUserCurrencyResponseDto;
import com.sparta.currency_user.entity.CurrencyExchange;
import com.sparta.currency_user.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

    List<CurrencyExchange> findCurrencyExchangeByUser_Id(Long userId);
    @Query(value = "select new com.sparta.currency_user.dto.currency.TotalUserCurrencyResponseDto(count(c.user.id),sum(c.preExchangeAmount)) from CurrencyExchange c WHERE c.status=:NOMAL")
    List<TotalUserCurrencyResponseDto> findUserCurrencyAndAmount(@Param("userId") Long userId, @Param("NOMAL") Status status);
}

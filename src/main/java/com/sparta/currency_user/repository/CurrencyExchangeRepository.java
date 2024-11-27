package com.sparta.currency_user.repository;

import com.sparta.currency_user.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

    List<CurrencyExchange> findCurrencyExchangeByUser_Id(Long userId);

}

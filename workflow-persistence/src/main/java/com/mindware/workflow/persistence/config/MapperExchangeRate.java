package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.ExchangeRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperExchangeRate {
    void add(ExchangeRate exchangeRate);

    void update(ExchangeRate exchangeRate);

    void delete(@Param("id") UUID id);

    ExchangeRate getById(@Param("id") UUID id);

    ExchangeRate getByValidityDate(@Param("startValidity") LocalDate startValidity, @Param("endValidity") LocalDate endValidity);

    ExchangeRate getActiveExchangeRateByCurrency(@Param("currency") String currency);

    List<ExchangeRate> getAll();
}

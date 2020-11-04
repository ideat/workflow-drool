package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.ExchangeRate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryExchangeRate {
    void add(ExchangeRate exchangeRate);

    void update(ExchangeRate exchangeRate);

    void delete(UUID id);

    Optional<ExchangeRate> getById(UUID id);

    Optional<ExchangeRate> getByValidityDate(LocalDate startValidity, LocalDate endValidity);

    Optional<ExchangeRate> getActiveExchangeRateByCurrency(String currency);

    List<ExchangeRate> getAll();
}

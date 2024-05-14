package com.pchnch.spcurrencyexchange.repository;


import com.pchnch.spcurrencyexchange.model.Audit;
import com.pchnch.spcurrencyexchange.model.CurrencyExchange;
import com.pchnch.spcurrencyexchange.model.CurrencyExchangeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, CurrencyExchangeId> {
}


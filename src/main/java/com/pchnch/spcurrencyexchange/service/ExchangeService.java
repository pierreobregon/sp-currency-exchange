package com.pchnch.spcurrencyexchange.service;

import com.pchnch.spcurrencyexchange.model.CurrencyExchange;
import com.pchnch.spcurrencyexchange.model.Audit;
import com.pchnch.spcurrencyexchange.model.CurrencyExchangeId;
import com.pchnch.spcurrencyexchange.repository.CurrencyExchangeRepository;
import com.pchnch.spcurrencyexchange.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class ExchangeService {
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private AuditRepository auditRepository;

    public Mono<CurrencyExchange> searchExchangeRate(String divisaOrigen, String divisaDestino) {
        return Mono.justOrEmpty(currencyExchangeRepository.findById(new CurrencyExchangeId(divisaOrigen, divisaDestino)));
    }
    public Flux<CurrencyExchange> getAllExchangeRates() {
        return Flux.fromIterable(currencyExchangeRepository.findAll());
    }

    public Flux<Audit> getAllAuditExchangeRates() {
        return Flux.fromIterable(auditRepository.findAll());
    }

    public Mono<Void> registerExchangeRate(CurrencyExchange currencyExchange) {
        currencyExchange.setFechaActualizacion(LocalDateTime.now());
        currencyExchangeRepository.save(currencyExchange);


        Audit audit = new Audit();
        audit.setDivisaOrigen(currencyExchange.getDivisaOrigen());
        audit.setDivisaDestino(currencyExchange.getDivisaDestino());
        audit.setTipoCambio(currencyExchange.getTipoCambio());
        audit.setUserApp(currencyExchange.getUserApp());
        audit.setFechaRegistro(LocalDateTime.now());
        auditRepository.save(audit);

        return Mono.empty();
    }

    public Mono<Void> updateExchangeRate(CurrencyExchange currencyExchange) {
        CurrencyExchange existing = currencyExchangeRepository.findById(new CurrencyExchangeId(currencyExchange.getDivisaOrigen(), currencyExchange.getDivisaDestino())).orElse(null);
        if (existing != null) {
            existing.setTipoCambio(currencyExchange.getTipoCambio());
            existing.setFechaActualizacion(LocalDateTime.now());
            currencyExchangeRepository.save(existing);

            Audit audit = new Audit();
            audit.setDivisaOrigen(currencyExchange.getDivisaOrigen());
            audit.setDivisaDestino(currencyExchange.getDivisaDestino());
            audit.setTipoCambio(currencyExchange.getTipoCambio());
            audit.setUserApp(currencyExchange.getUserApp());
            audit.setFechaRegistro(LocalDateTime.now());
            auditRepository.save(audit);
        } else {
            registerExchangeRate(currencyExchange);
        }
        return Mono.empty();
    }
}

package com.pchnch.spcurrencyexchange.controller;

import com.pchnch.spcurrencyexchange.model.Audit;
import com.pchnch.spcurrencyexchange.model.CurrencyExchange;
import com.pchnch.spcurrencyexchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api-sp/exchange")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @PostMapping("/search")
    public Mono<ResponseEntity<CurrencyExchange>> searchExchange(@RequestBody CurrencyExchange request) {
        return exchangeService.searchExchangeRate(request.getDivisaOrigen(), request.getDivisaDestino())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, String>>> registerExchange(@RequestBody CurrencyExchange request) {
        return exchangeService.searchExchangeRate(request.getDivisaOrigen(), request.getDivisaDestino())
                .flatMap(existing -> {
                    Map<String, String> errorResponse = Map.of(
                            "codigoError", "202",
                            "descripcionError", "El registro ya existe en base de datos"
                    );
                    return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse));
                })
                .switchIfEmpty(exchangeService.registerExchangeRate(request)
                        .then(Mono.just(ResponseEntity.ok(Map.of("mensaje", "Registro exitoso")))));

        /*return exchangeService.registerExchangeRate(request)
                .then(Mono.just(ResponseEntity.ok().build()));*/
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateExchange(@RequestBody CurrencyExchange request) {
        return exchangeService.updateExchangeRate(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @GetMapping
    public Flux<CurrencyExchange> getAllExchanges() {
        return exchangeService.getAllExchangeRates();
    }

    @GetMapping("/audit-exchange")
    public Mono<ResponseEntity<Flux<Audit>>> listAllAuditRecords() {
        return exchangeService.getAllAuditExchangeRates()
                .collectList()
                .map(auditRecords -> {
                    if (auditRecords.isEmpty()) {
                        return ResponseEntity.noContent().<Flux<Audit>>build();
                    }
                    return ResponseEntity.ok(Flux.fromIterable(auditRecords));
                });
    }
}

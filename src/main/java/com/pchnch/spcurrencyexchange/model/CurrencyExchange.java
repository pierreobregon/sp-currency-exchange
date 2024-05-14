package com.pchnch.spcurrencyexchange.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_currency_exchange")
@IdClass(CurrencyExchangeId.class)
public class CurrencyExchange {
    @Id
    private String divisaOrigen;
    @Id
    private String divisaDestino;
    private double tipoCambio;
    private String userApp;
    private LocalDateTime fechaActualizacion;
}


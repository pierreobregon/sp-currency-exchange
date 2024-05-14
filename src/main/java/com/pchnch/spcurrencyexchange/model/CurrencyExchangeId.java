package com.pchnch.spcurrencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeId implements Serializable {
    private String divisaOrigen;
    private String divisaDestino;
}

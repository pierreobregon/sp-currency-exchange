package com.pchnch.spcurrencyexchange.model;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_audit")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String divisaOrigen;
    private String divisaDestino;
    private double tipoCambio;
    private String userApp;
    private LocalDateTime fechaRegistro;
}

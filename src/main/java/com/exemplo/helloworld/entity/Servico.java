package com.exemplo.helloworld.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "servico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome_do_cliente;  

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data_de_inicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data_de_termino;

    @Column(nullable = false)
    private String descricao_servico;

    @Column(nullable = false)
    private Double valor_do_servico;

    private Double valor_pago;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data_de_pagamento;

    private String status;
}

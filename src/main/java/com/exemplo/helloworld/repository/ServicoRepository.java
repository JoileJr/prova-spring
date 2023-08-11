package com.exemplo.helloworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exemplo.helloworld.entity.Servico;

public interface ServicoRepository extends JpaRepository<Servico ,Long> {

    @Query("from Servico where status like %?1%")
    List<Servico> findByStatus(String status);

    @Query("from Servico where status <> 'excluido' or status is null and valor_pago is null and data_de_pagamento is null")
    List<Servico> findByStatusOpen();
}

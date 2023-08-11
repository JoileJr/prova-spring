package com.exemplo.helloworld.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.helloworld.entity.Servico;
import com.exemplo.helloworld.repository.ServicoRepository;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;

    public List<Servico> servicosAbertos(){
        return repository.findByStatusOpen();
    }

    public List<Servico> buscarPorStatus(String status){
        return repository.findByStatus(status);
    }

    public List<Servico> listarTodos(){
        return repository.findAll();
    }

    public Servico salvar(Servico obj){
        atualizarStatus(obj);
        verificarSituacaoPagamento(obj);
        return repository.saveAndFlush(obj);
    }

    public Servico atualizar(Servico obj) {
        atualizarStatus(obj);
        verificarSituacaoPagamento(obj);
        return repository.saveAndFlush(obj);
    }

    public void excluir(Long id){
        Servico obj = repository.findById(id).get();
        repository.delete(obj);
    }

    private void atualizarStatus(Servico servico){
        if(servico.getData_de_pagamento() == null){
            servico.setStatus("pendente");
        }
        if(servico.getValor_pago() == null){
            servico.setStatus("pendente");
        }
    }

    private void verificarSituacaoPagamento(Servico servico){
        if((servico.getValor_pago() == null && servico.getData_de_pagamento() != null) 
        || (servico.getValor_pago() != null && servico.getData_de_pagamento() == null) ){
            new IllegalAccessException("Informar data e valor de pagamento");
        }
    }
}

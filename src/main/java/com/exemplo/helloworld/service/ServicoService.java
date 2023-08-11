package com.exemplo.helloworld.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exemplo.helloworld.entity.Servico;
import com.exemplo.helloworld.entity.Variancia;
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
        obj.setStatus("Excluido");
        repository.saveAndFlush(obj);
    }

    private void atualizarStatus(Servico servico){
        if(servico.getData_de_pagamento() == null){
            servico.setStatus("pendente");
        }
        if(servico.getValor_pago() == null){
            servico.setStatus("pendente");
        }
    }

    public void excluir(Date dataInicial, Date dataFinal){
        List<Servico> servicos = repository.servicoDataInicio(dataInicial, dataFinal);
        for(Servico s:servicos){
            s.setStatus("excluido");
            repository.saveAndFlush(s);
        }
    }

    public ResponseEntity<Variancia> calculoVarianciaMedia(){
        List<Servico> servicos = repository.servicosPagos();
        Double media = calculoMedia(servicos);
        Double variancia = calculoVariancia(servicos, media);
        return new ResponseEntity<>(new Variancia(media, variancia), HttpStatus.OK);
    }

    private void verificarSituacaoPagamento(Servico servico){
        if((servico.getValor_pago() == null && servico.getData_de_pagamento() != null) 
        || (servico.getValor_pago() != null && servico.getData_de_pagamento() == null) ){
            new IllegalAccessException("Informar data e valor de pagamento");
        }
    }

    private Double calculoVariancia(List<Servico> servicos, Double media){
        Double variancia = 0.;
        for(Servico s:servicos){
            variancia += Math.pow(s.getValor_pago() - media, 2);
        }
        variancia = variancia/servicos.size();
        return variancia;
    }

    private Double calculoMedia(List<Servico> servicos ){
        Double media = 0.;
        for(Servico s:servicos){
            media+=s.getValor_pago();
        }
        return media/servicos.size();
    }
}

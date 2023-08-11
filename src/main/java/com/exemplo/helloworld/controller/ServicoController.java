package com.exemplo.helloworld.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.helloworld.entity.Servico;
import com.exemplo.helloworld.service.ServicoService;

@RestController
@RequestMapping("/servicos")
@CrossOrigin
public class ServicoController {

    @Autowired
    ServicoService service;

    @GetMapping("/status")
    public List<Servico> listarPorStatus(@RequestParam(value = "status", defaultValue = "pendente") String status){
        return service.buscarPorStatus(status);
    }

    @GetMapping
    public List<Servico> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/open")
    public List<Servico> listarServicoAbertos(){
        return service.servicosAbertos();
    }

    @PostMapping
    public Servico salvar(@RequestBody Servico obj){
        return service.salvar(obj);
    }

    @PutMapping
    public Servico atualizar(@RequestBody Servico obj){
        return service.atualizar(obj);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable("id") Long id){
        service.excluir(id);
    }

    @DeleteMapping("/{dataInicio}/{dataTermino}")
    public void excluirPorData(@PathVariable("dataInicio")@DateTimeFormat(pattern = "yyyy-mm-dd") Date dataInicio, 
    @PathVariable("dataInicio") @DateTimeFormat(pattern = "yyyy-mm-dd") Date dataTermino){
        service.excluir(dataInicio, dataTermino);
    }
}

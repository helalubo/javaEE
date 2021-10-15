package com.helalubo.mook.laboratorios.application.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.helalubo.mook.laboratorios.infrastructure.repositories.LaboratorioRepository;
import com.helalubo.mook.laboratorios.model.Laboratorio;

public class LaboratorioService {

    private LaboratorioRepository repo;

    public LaboratorioService(LaboratorioRepository repo) {
        this.repo = repo;
    }

    public List<Laboratorio> findAll() throws JsonMappingException, JsonProcessingException {

        return repo.findAll();
        
    }

}

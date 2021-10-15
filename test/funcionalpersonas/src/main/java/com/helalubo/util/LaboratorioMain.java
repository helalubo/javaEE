package com.helalubo.util;


import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.helalubo.mook.laboratorios.application.services.LaboratorioService;
import com.helalubo.mook.laboratorios.infrastructure.repositories.LaboratorioRepository;
import com.helalubo.mook.laboratorios.model.Laboratorio;

public class LaboratorioMain {



	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		
		LaboratorioRepository laboratorioRepository = new LaboratorioRepository();
		 LaboratorioService laboratorioService = new LaboratorioService(laboratorioRepository);
			
		 
		 
		 List<Laboratorio> labs = laboratorioService.findAll();
		 
		 labs.forEach(System.out::println);
		
	}

}

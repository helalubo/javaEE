package com.helalubo.mook.laboratorios.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helalubo.mook.laboratorios.model.Laboratorio;
import com.helalubo.util.Json;

public class LaboratorioRepository {

    public List<Laboratorio> findAll() throws JsonMappingException, JsonProcessingException {
        JSONArray data = null;
        List<Laboratorio> labs = new ArrayList<Laboratorio >();
        data = Json.LeerArrayJson("/src/main/java/com/helalubo/database/laboratorio.json");
        ObjectMapper mapper = new ObjectMapper();
        labs = mapper.readValue(data.toJSONString(), new TypeReference<List<Laboratorio>>() {
        });

        return labs;
    }

}

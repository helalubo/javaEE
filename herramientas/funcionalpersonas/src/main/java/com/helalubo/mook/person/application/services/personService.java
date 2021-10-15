package com.helalubo.mook.person.application.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.helalubo.mook.person.infrastructure.repositories.personRepository;
import com.helalubo.mook.person.model.Person;

public class personService {

    private personRepository repo;

    public personService(personRepository repo) {
        this.repo = repo;
    }

    public List<Person> findAllPerson() throws JsonMappingException, JsonProcessingException {

        return repo.findAllPerson();

    }

}

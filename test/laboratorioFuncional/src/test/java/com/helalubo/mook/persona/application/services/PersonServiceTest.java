package com.helalubo.mook.persona.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.helalubo.mook.person.application.services.personService;
import com.helalubo.mook.person.infrastructure.repositories.personRepository;
import com.helalubo.mook.person.model.Person;

class PersonRepositoryTest {
	
	personRepository personRepository = new personRepository();
	personService personService = new personService(personRepository);

	@BeforeAll
	static void init() {
		System.out.println("Inicio la clase PersonaServiceTest");

	}
	
	@Test	
	@DisplayName("Diferente de cero")
	void SizeDiferenteDeCero() throws JsonMappingException, JsonProcessingException  {
		
		List<Person> personas = new ArrayList<Person>();		
		personas = personService.findAllPerson();
		personas.forEach(System.out::println);
		Integer actual = personas.size();
		
		
		assertNotEquals(0, actual.intValue());
		
		
	}

}

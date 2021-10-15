package com.helalubo.mook.person.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.helalubo.mook.person.infrastructure.repositories.personRepository;
import com.helalubo.mook.person.model.Person;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonServiceTest  {
	private List<Person> personas;
	private personRepository personRepository;
	private personService personService;

	@BeforeAll
	static void init() {
		System.out.println("Inicio la clase PersonaServiceTest");

	}

	@BeforeEach
	void begin() throws JsonMappingException, JsonProcessingException{
		this.personRepository = new personRepository();
		this.personService = new personService(personRepository);

		this.personas = this.personService.findAllPerson();
	}

	@Test
	@DisplayName("Diferente de cero")
	void SizeDiferenteDeCero()  {

		personas.forEach(System.out::println);
		Integer actual = personas.size();

		assertNotEquals(0, actual.intValue());

	}

	@Test
	@DisplayName("Diferente de null")
	void listaPersonasDiferenteDeNull()throws JsonMappingException, JsonProcessingException  {

		List<Person> personas = null;
		personas = personService.findAllPerson();

		assertNotNull(personas);

	}
}

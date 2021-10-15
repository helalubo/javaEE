/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.web;

import com.helalubo.mook.persona.application.services.PersonaService;
import com.helalubo.mook.persona.domain.Persona;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author User
 */
@Named(value = "personaBean")
@RequestScoped
public class PersonaBean {
    
    Logger log = LogManager.getRootLogger();

    @Inject
    private PersonaService personaService;

    private Persona personaSeleccionada;

    List<Persona> personas;

    public PersonaBean() {
    log.debug("iniciando objeto persona bean");
    }

    @PostConstruct
    public void inicializar() {
        //iniciamos las variables

        personas = personaService.listarPersonas();
        personaSeleccionada = new Persona();
        log.debug("Personas recuperadas en ManagedBean" + personas);

    }

    public void editListener(RowEditEvent event) {
        Persona persona = (Persona) event.getObject();
        personaService.modificarPersona(persona);

    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    
    public void agregarPersona()
    {
        this.personaService.registrarPersona(personaSeleccionada);
        this.personas.add(personaSeleccionada);
        
        this.personaSeleccionada = null;
    }
    
       
    public void eliminarPersona()
    {
        this.personaService.eliminarPersona(personaSeleccionada);
        this.personas.remove(this.personaSeleccionada);
        this.personaSeleccionada = null;
    }
    
    public  void reiniciarPersonaSeleccionada(){
        this.personaSeleccionada = new Persona();
        
    }
    
}

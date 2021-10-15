/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.util;

import com.helalubo.Mook.persona.application.serviceRemote.PersonaServiceRemote;

import com.helalubo.Mook.persona.domain.Persona;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class PruebaManejoTransacciones {
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args){
        
        
        try {
            Context jndi = new InitialContext();
            PersonaServiceRemote personaService = (PersonaServiceRemote)jndi.lookup("java:global/sga-jee-web/PersonaServiceImpl!com.helalubo.mook.persona.application.serviceRemote.PersonaServiceRemote");
                
            log.debug("pruebade manejo transaccional Persona");
        
            Persona personaSearch = personaService.encontrarPersonaPorId(new Persona(5));
            
            log.debug(personaSearch);
        
        } catch (NamingException ex) {
         log.debug(ex.getMessage());
        }

    
    }
}

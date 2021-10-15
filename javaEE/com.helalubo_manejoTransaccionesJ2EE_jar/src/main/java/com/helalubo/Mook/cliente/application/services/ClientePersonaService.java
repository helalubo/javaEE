/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.Mook.cliente.application.services;

import com.helalubo.Mook.persona.application.serviceRemote.PersonaServiceRemote;
import com.helalubo.Mook.persona.domain.Persona;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.*;

/**
 *
 * @author User
 */
public class ClientePersonaService {

    public static void main(String[] args) {
        System.out.println("iniciando Llamada al EjB desde el cliente");

        try {

            Context jndi =  new InitialContext();
            PersonaServiceRemote personaService = (PersonaServiceRemote) jndi.lookup("ava:global/sga-jee-web/PersonaServiceImpl!com.helalubo.mook.persona.application.services.PersonaServiceRemote");
            
            List<Persona> personas= personaService.ListarPersonas();
            
            personas.forEach(System.out::println);
            
            
        } catch (NamingException ex) {
            ex.printStackTrace(System.out);
        }
    }

}

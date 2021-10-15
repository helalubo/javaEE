/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.mook.cliente.infrastructure.association;

import com.helalubo.mook.persona.domain.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class ClienteAsociacionesJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

        List<Persona> personas = em.createNamedQuery("Persona.findAll").getResultList();

        personas.forEach(p -> {
            log.debug("persona: " + p.getApellido());
            p.getUsuarioList().forEach(u ->  log.debug("usuario: " + u.getUsername()));
        }
        );
    


           
    
    
    }
    
}

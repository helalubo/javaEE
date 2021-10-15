/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.util;

import com.helalubo.mook.persona.domain.Persona;
import static com.helalubo.util.PersistirObjetoJPA.log;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class ActualizarObjetoSesionLarga {
    
     static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //inicia la transaccion
        //inicio transaccion
        tx.begin();

        //ejecuta sql
        Persona personaUpdate = em.find(Persona.class, 10);
        log.debug("Objeto persistido - estado detached " + personaUpdate);

        //estoy en estado de detache
        personaUpdate.setTelefono("77777");

        //commit/rollback
        tx.commit();
 log.debug("Objeto persistido - estado actualizado " + personaUpdate);
     



        

        em.close();

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.util;

import com.helalubo.mook.persona.domain.Persona;
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
public class ActializarObjetoJPA {

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

        //estoy en estado de detache
        log.debug("Objeto antes de actualizar");

        //commit/rollback
        tx.commit();

        log.debug("Objeto persistido - estado detached " + personaUpdate);

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();

        personaUpdate.setTelefono("112556666");
        em.merge(personaUpdate);

        tx2.commit();

        log.debug("Objeto ya actualizado - estado detached " + personaUpdate);

        em.close();

    }
}

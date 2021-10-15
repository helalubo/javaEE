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
public class PersistirObjetoJPA {
    
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        
        //inicia la transaccion
        
        
        //Paso uno nuevo objeto en estado transitivo
        Persona persona1 = new Persona("Mica", "Rodriguez", "mica@gmail.com", "1545644545");
        
        //inicio transaccion
        tx.begin();
        
        //ejecuta sql
        em.persist(persona1);
        
        //commit/rollback
        tx.commit();
        
        log.debug("Objeto persistido - estado detached " + persona1);
        
        em.close();
        
        
        
        
    }
}

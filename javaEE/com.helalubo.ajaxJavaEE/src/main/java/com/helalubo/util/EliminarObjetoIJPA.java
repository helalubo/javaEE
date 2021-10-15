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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class EliminarObjetoIJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //inicia la transaccion
        tx.begin();

        //Paso uno nuevo objeto en estado transitivo
        Persona persona1 = em.find(Persona.class, 10);
        log.debug("objeto persona1:" );
        


        tx.commit();

    
        
        log.debug("objeto persona1:"
                +persona1);
        
        
      EntityTransaction tx2 = em.getTransaction();

        //inicia la transaccion
        tx2.begin();

        //el merge maneja el error de si esta representado en la base de datos o si no se encuetnra lo que se quiere borrar
        em.remove(em.merge(persona1));
        //Paso uno nuevo objeto en estado transitivo
    
        //topdavia lo puedo imprimir
        log.debug("objeto en detached ya eliminado " + persona1 );
        


        tx2.commit();

        em.close();
        
    //inicio transaccion
    }
}

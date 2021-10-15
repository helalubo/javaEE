/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.test;

import com.helalubo.sgawebhibernatejpa.mook.contacto.domain.Contacto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Helalubo
 */
public class Estado1Persistido {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaSource");
        EntityManager em = emf.createEntityManager();

        //1.Estado transitivo
        Contacto contacto = new Contacto();
        contacto.setTelefono("1132426886");
        contacto.setEmail("alejandro@gmail.com");

        //2-persistimos el objeto iniciando una trasaccion
        em.getTransaction().begin();
        //persisto
        em.persist(contacto);
 
        em.getTransaction().commit();
        
        //3 paso a detached (separado) ya tendra el id
        System.out.println(contacto);

    }

}

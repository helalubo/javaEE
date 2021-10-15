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
public class Estado4EiminarObjeto {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaSource");
        EntityManager em = emf.createEntityManager();

        //No es necesario abrir una transaccion apra recuperar un objeto, puedo 
        //puedo usar el em directamente en vez de usar ele getTransaction.begin();
        Contacto contacto = null;

        contacto = em.find(Contacto.class, 3);
        contacto.setEmail("Alejandrodemoraiz@gmail.com");

        em.getTransaction().begin();

        //se recomienta usar merge dentro del remove para sincronizar y evitar errores
        em.remove(em.merge(contacto));

        em.getTransaction().commit();

        System.out.println("contacto borrado" +contacto);

    }
}

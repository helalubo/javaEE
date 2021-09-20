/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.test;

import com.helalubo.sgawebhibernatejpa.mook.alumno.domain.Alumno;
import com.helalubo.sgawebhibernatejpa.mook.contacto.domain.Contacto;
import com.helalubo.sgawebhibernatejpa.mook.domicilio.model.Domicilio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Helalubo 
 * Usando     @ManyToOne(cascade = CascadeType.ALL)
 */
public class PersistenciaCascadaTest {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaSource");
        EntityManager em = emf.createEntityManager();
        
        Domicilio d = new Domicilio();
        d.setCalle("Hernandarias");
        d.setNoCalle("957");
        d.setPais("Argentina");
        Contacto c = new Contacto();
        c.setEmail("Alejandrodemoraiz@gmail.com");
        c.setTelefono("1132426886");
        
        Alumno a = new Alumno();
        a.setNombre("Alejandro");
        a.setApellido("De Moraiz");
        a.setContacto(c);
        a.setDomicilio(d);
        
        em.getTransaction().begin();
        
        em.persist(a);
        
        em.getTransaction().commit();
        
        System.out.println(a);
        
        
        
        
        
        
        
    }
}

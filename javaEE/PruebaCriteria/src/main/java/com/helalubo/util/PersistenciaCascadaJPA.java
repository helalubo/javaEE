/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.util;

import com.helalubo.mook.persona.domain.Persona;
import com.helalubo.mook.usuario.domain.Usuario;
import static com.helalubo.util.ActializarObjetoJPA.log;
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
public class PersistenciaCascadaJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Persona persona = new Persona("Miguel", "De moraiz", "miguel.demroaiz@gmai.com", "546546578");

        Usuario usuario = new Usuario("mdem", "1234", persona);

        em.persist(usuario);

        tx.commit();

        log.debug("Objeto ya actualizado - estado detached " + persona);

        em.close();
    }
}

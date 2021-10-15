/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.contacto.infrastructure.repositories;

import com.helalubo.sgawebhibernatejpa.connection.ConnectionManager;
import com.helalubo.sgawebhibernatejpa.mook.contacto.domain.Contacto;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Helalubo
 */
public class ContactoRepository extends ConnectionManager {

    public List<Contacto> Listar() {

        String consulta = "SELECT c FROM Contacto c";
        em = getEntityManager();
        Query query = em.createQuery(consulta);

        return query.getResultList();

    }

    public void insertar(Contacto contacto) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contacto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
//        finally {
//            if (em != null) {
//                em.close();
//            }
//        }

    }

    public void actualizar(Contacto contacto) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(contacto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
//        finally {
//            if (em != null) {
//                em.close();
//            }
//        }

    }

    public void eliminar(Contacto contacto) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(contacto));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
//        finally {
//            if (em != null) {
//                em.close();
//            }
//        }

    }

    public Contacto buscarPorId(Contacto contacto) {

        em = getEntityManager();
        return em.find(Contacto.class, contacto.getIdContacto());

    }

}

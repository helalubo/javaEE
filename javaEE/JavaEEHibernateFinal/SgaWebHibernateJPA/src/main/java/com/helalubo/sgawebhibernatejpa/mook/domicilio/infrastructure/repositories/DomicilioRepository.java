/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.domicilio.infrastructure.repositories;

import com.helalubo.sgawebhibernatejpa.connection.ConnectionManager;
import com.helalubo.sgawebhibernatejpa.mook.domicilio.model.Domicilio;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Helalubo
 */
public class DomicilioRepository extends ConnectionManager {

    public List<Domicilio> Listar() {

        String consulta = "SELECT d FROM Domicilio d";
        em = getEntityManager();
        Query query = em.createQuery(consulta);

        return query.getResultList();

    }

    public void insertar(Domicilio domicilio) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(domicilio);
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

    public void actualizar(Domicilio domicilio) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(domicilio);
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

    public void eliminar(Domicilio domicilio) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(domicilio));
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

    public Domicilio buscarPorId(Domicilio domicilio) {

        em = getEntityManager();
        return em.find(Domicilio.class, domicilio.getIdDomicilio());

    }

}

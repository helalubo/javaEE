/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.asignacion.infrastructure.repositories;

import com.helalubo.sgawebhibernatejpa.connection.ConnectionManager;
import com.helalubo.sgawebhibernatejpa.mook.asignacion.domain.Asignacion;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Helalubo
 */
public class AsignacionRepository extends ConnectionManager {

    public List<Asignacion> Listar() {

        String consulta = "SELECT a FROM Asignacion a";
        em = getEntityManager();
        Query query = em.createQuery(consulta);

        return query.getResultList();

    }

    public void insertar(Asignacion asignacion) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asignacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public void actualizar(Asignacion asignacion) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(asignacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public void eliminar(Asignacion asignacion) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(asignacion));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public Asignacion buscarPorId(Asignacion asignacion) {

        em = getEntityManager();
        return em.find(Asignacion.class, asignacion.getIdAsignacion());

    }

}

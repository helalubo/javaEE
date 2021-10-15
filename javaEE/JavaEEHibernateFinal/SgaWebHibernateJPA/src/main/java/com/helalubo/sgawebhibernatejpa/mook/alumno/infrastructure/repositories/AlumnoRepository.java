/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.alumno.infrastructure.repositories;

import com.helalubo.sgawebhibernatejpa.connection.ConnectionManager;
import com.helalubo.sgawebhibernatejpa.mook.alumno.domain.Alumno;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Helalubo
 */
public class AlumnoRepository extends ConnectionManager {

    public List<Alumno> Listar() {

        String consulta = "SELECT a FROM Alumno a";
        em = getEntityManager();
        Query query = em.createQuery(consulta);

        return query.getResultList();

    }

    public void insertar(Alumno alumno) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(alumno);
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

    public void actualizar(Alumno alumno) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(alumno);
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

    public void eliminar(Alumno alumno) {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(alumno));
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

    public Alumno buscarPorId(Alumno alumno) {

        em = getEntityManager();
        return em.find(Alumno.class, alumno.getIdAlumno());

    }

}

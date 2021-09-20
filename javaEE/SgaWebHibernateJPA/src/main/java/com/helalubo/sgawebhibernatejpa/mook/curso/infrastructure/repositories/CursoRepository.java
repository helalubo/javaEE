/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.curso.infrastructure.repositories;

import com.helalubo.sgawebhibernatejpa.connection.ConnectionManager;
import com.helalubo.sgawebhibernatejpa.mook.curso.domain.Curso;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Helalubo
 */
public class CursoRepository extends ConnectionManager {

    public List<Curso> Listar() {

        String consulta = "SELECT c FROM Curso c";
        em = getEntityManager();
        Query query = em.createQuery(consulta);

        return query.getResultList();

    }

    public void insertar(Curso curso) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public void actualizar(Curso curso) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public void eliminar(Curso curso) {

        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(curso));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public Curso buscarPorId(Curso curso) {

        em = getEntityManager();
        return em.find(Curso.class, curso.getIdCurso());

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.util;

import com.helalubo.mook.persona.domain.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class PruebaApiCriteria {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder cb = null;
        CriteriaQuery<Persona> criteriaQuery = null;
        Root<Persona> fromPersona = null;
        TypedQuery<Persona> query = null;
        Persona persona = null;
        List<Persona> personas = null;

        //Query utilizando el API CITERIA MUY UTIL PARA QUERY DINAMICO
        //1. CONSULTA DE TODAS LAS PERSONAS
//        cb = em.getCriteriaBuilder();
//        
//        //creo el objeto criteriaQuery
//
//        criteriaQuery = cb.createQuery(Persona.class);
//        
//         //creo el objeto raiz de query
//        fromPersona = criteriaQuery.from(Persona.class);
//        
//        //seleccionamos lo necesario del from
//        
//        criteriaQuery.select(fromPersona);
//        
//        
//        //creamos el tipo de query;
//        query = em.createQuery(criteriaQuery);
//        
//        //ejecutamos consulta
//        personas = query.getResultList();
//        
//       personas.forEach(p -> log.debug(p));
        //    consulta de persona con id =1
//        // jpql = "select p from Persona p where p.idPersona =1" primera forma ;
//        cb = em.getCriteriaBuilder();
//
//        //creo el objeto criteriaQuery
//        criteriaQuery = cb.createQuery(Persona.class);
//
//        //creo el objeto raiz de query
//        fromPersona = criteriaQuery.from(Persona.class);
//        criteriaQuery.select(fromPersona).where(cb.equal(fromPersona.get("idPersona"), 1));
//
//        //seleccionamos lo necesario del from
//        try{
//            
//           persona = em.createQuery(criteriaQuery).getSingleResult();
//        }catch( NoResultException e){
//               log.debug("No se encontro resultado");
//        }
//
//        //creamos el tipo de query;
        // jpql = "select p from Persona p where p.idPersona =1" segunda forma usando clase Predicate ;
        cb = em.getCriteriaBuilder();

        //creo el objeto criteriaQuery
        criteriaQuery = cb.createQuery(Persona.class);

        //creo el objeto raiz de query
        fromPersona = criteriaQuery.from(Persona.class);
        criteriaQuery.select(fromPersona);

        //La clasePredicate permite agregar varios criterios inamicamente
        List<Predicate> criterios = new ArrayList<Predicate>();
        Integer idPersonaParm = 3;
        ParameterExpression<Integer> parameter = cb.parameter(Integer.class, "idPersona");
        criterios.add(cb.equal(fromPersona.get("idPersona"), parameter));

        //se agregan los criterios
        if (criterios.isEmpty()) {
            throw new RuntimeException("Sin criterios");
        } else if (criterios.size() == 1) {
            criteriaQuery.where(criterios.get(0));
        } else {
            criteriaQuery.where(cb.and(criterios.toArray(new Predicate[0])));
        }
        
        query =  em.createQuery(criteriaQuery);
        query.setParameter("idPersona", idPersonaParm);
        
        persona = query.getSingleResult();
        
        

        //ejecutamos consulta
        log.debug(persona);
    }

}

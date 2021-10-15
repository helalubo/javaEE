
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.util;

import com.helalubo.mook.persona.domain.Persona;
import com.helalubo.mook.usuario.domain.Usuario;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class PruebaJPQL {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        String jpql = null;
        Query q = null;
        Persona persona = null;
        List<Persona> personas = null;
        Iterator iter = null;
        Object[] tupla;
        List<String> nombres = null;
        List<Usuario> usuarios = null;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

//        
//        log.debug("\n1.consulta de todas las personas");
//        jpql = "select p from Persona p";s
//        personas = em.createQuery(jpql).getResultList();
//        mostrarPersonas(personas);
//      log.debug("\n2.consulta de la persona por id = 3");
//      jpql = "select p from Persona p where p.idPersona =3";
//      persona = (Persona) em.createQuery(jpql).getSingleResult();
//        log.debug(persona);
//      log.debug("\n3-consulta por nombre de persona ");
//      jpql = "select p from Persona p where p.nombre = 'Maite'";
//      persona = (Persona) em.createQuery(jpql).getSingleResult();
//      
//      personas = em.createQuery(jpql).getResultList();
//       log.debug("\n4-consulta de datos indibidiales, se copia un arreglo que es una tupla de  3 columnas \n");
//      jpql = "select p.nombre as Nombre, p.apellido as Apellido, p.email as Email from Persona p";
//      
//      
//      iter = em.createQuery(jpql).getResultList().iterator();
//      
//      while(iter.hasNext()){
//          tupla = (Object[]) iter.next();
//          String nombre = (String) tupla[0];
//          String apellido = (String) tupla[1];
//          String email = (String) tupla[2];
//        log.debug(nombre +  apellido +email);
//      }
//      
//      log.debug("\n5-consulta de datos indibidiales, se copia un arreglo que es una tupla de tipo Object []de  2 columnas \n");
//      jpql = "select p, p.idPersona from Persona p";
//      iter = em.createQuery(jpql).getResultList().iterator();
//      
//      
//
//      
//      while(iter.hasNext()){
//          tupla = (Object[]) iter.next();
//           persona = (Persona) tupla[0];
//          int idPersona = (int) tupla[1];
//
//        log.debug("id: " +  idPersona);
//        log.debug("persona: : " +  persona);
//      }
//        log.debug("\n6-consulta de personas pero poniendo algunos atributos en null por temas de seguridad \n");
//        jpql = "select new com.helalubo.mook.persona.domain.Persona( p.idPersona )from Persona p";
//        personas = em.createQuery(jpql).getResultList();
//
//        mostrarPersonas(personas);
//
//        log.debug("\n7-regresa valor minimo y maximo con idPersona\n");
//        jpql = "select min(p.idPersona) as MinId, max(p.idPersona)as MaxId,count(p.idPersona) as Contador from Persona p";
//        iter = em.createQuery(jpql).getResultList().iterator();
//      
//      
//
//      
//      while(iter.hasNext()){
//          tupla = (Object[]) iter.next();
//           Integer idMin= (Integer) tupla[0];
//          Integer idMax = (Integer) tupla[1];
//          Long count = (Long) tupla[2];
//
//        log.debug("idMin: " +  idMin);
//        log.debug("idMax: " +  idMax);
//        log.debug("count: : " +  count);
//        
//         }   
//         log.debug("\n8-Cuenta los nombres dellas personas que son distintos\n");
//         
//         jpql="select count(distinct p.nombre) from Persona p";
//         
//         Long contador = (Long) em.createQuery(jpql).getSingleResult();
//         
//         System.out.println(contador);
//  
//        log.debug("\n9-concatena y convierte en mayuscula el nobmre y el apellido\n");
//
//        jpql = "select concat(p.nombre,' ',p.apellido) as Nombre from Persona p";
//
//        nombres = em.createQuery(jpql).getResultList();
//
//        nombres.forEach(log::debug);
//
//        log.debug("\n10-obtengo persona por id igual al parametro proporsionado, bueno para clave y contraseña\n");
//        int idPersona = 3;
//       
//         
//        jpql = "select p from Persona p where p.idPersona = :id";
//
//        q = em.createQuery(jpql);
//        q.setParameter("id", idPersona);
//        persona = (Persona)q.getSingleResult();
//        
//        log.debug(persona);
//        
//           log.debug("\n11-obtengo las personas que contengan una letra a en el nombre , sin importar si es mayusculas o minuscula, bueno para busqueda de carga de datos al tipeo (like) en base de datos\n");
//           
//           jpql = "select p from Persona p where upper(p.nombre) like upper(:parametro)";
//           //parametro de like
//           String parametro = "%a%";
//           q = em.createQuery(jpql);
//           q.setParameter("parametro", parametro);
//           personas = q.getResultList();
//           
//           mostrarPersonas(personas);
//           
//           log.debug("\n12-uso de between\n");
//           
//           jpql = "select p from Persona p where p.idPersona between 4 and 100";
//           
//           personas = em.createQuery(jpql).getResultList();
// 
//           
//           mostrarPersonas(personas);
////           
//        log.debug("\n13-uso de ordenamiento\n");
//        
//        jpql ="select p from Persona p where p.idPersona > 3 order by p.nombre desc, p.apellido desc";
//        personas = em.createQuery(jpql).getResultList();
//        
//        mostrarPersonas(personas );
//        
//        
//        log.debug("\n15-uso de join\n");
//        //retorna Persona{idPersona=3, nombre=Helalubo, apellido=inc, email=helalubo@mail.com, telefono=1564491741}
//        jpql = "select p from Persona p where p.idPersona in (select min(p1.idPersona) from Persona p1)";
//        personas = em.createQuery(jpql).getResultList();
//        mostrarPersonas(personas);
//        log.debug("\n15-uso de join con loading\n");
//        jpql = "select u from Usuario u join u.persona p where u.idUsuario =3";
//        usuarios = em.createQuery(jpql).getResultList();
//        usuarios.forEach(p -> log.debug(p));

//         log.debug("\n15-uso de join con eager  loading - la diferencia es la palabra fetch\n");
//         // retorna Usuario{idUsuario=3, username=helalubo, password=1234, persona=Persona{idPersona=3, nombre=Helalubo, apellido=inc, email=helalubo@mail.com, telefono=1564491741}}
//        jpql = "select u from Usuario u left join fetch u.persona";
//        usuarios = em.createQuery(jpql).getResultList();
//        usuarios.forEach(p -> log.debug(p));
    }

    public static void mostrarPersonas(List<Persona> personas) {
        personas.forEach(p -> log.debug(p));
    }
}

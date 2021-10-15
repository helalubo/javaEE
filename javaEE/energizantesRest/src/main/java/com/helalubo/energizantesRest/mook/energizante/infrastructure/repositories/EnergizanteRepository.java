/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.energizantesRest.mook.energizante.infrastructure.repositories;

import com.helalubo.energizantesRest.mook.energizante.domain.Energizante;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Helalubo
 */

@Stateless
public class EnergizanteRepository  implements IEnergizanteRepository{

    
    @PersistenceContext(unitName = "energizanteSource")
    EntityManager em;
    
    
    
    @Override
    public List<Energizante> encontrarTodos() {
        return em.createNamedQuery("Energizante.encontrarTodosEnergizante").getResultList();
    }

    @Override
    public Energizante encontrarPorId(Energizante energizante) {
       return em.find(Energizante.class, energizante.getIdEnergizante());
    }

    @Override
    public void insertar(Energizante energizante) {
        em.persist(energizante);
        em.flush();
    }

    @Override
    public void actualizar(Energizante energizante) {
         em.merge(energizante);
   
    }

    @Override
    public void eliminar(Energizante energizante) {
        em.remove(em.merge(energizante));
    }

    @Override
    public boolean actualizarFoto(int id, String nombreFoto) {
        
         int rowsUpdated = 0;
         boolean rta = false;
         Query query = em.createQuery("UPDATE Energizante e SET e.foto = :foto WHERE e.idEnergizante = :id");
         query.setParameter("foto", nombreFoto);
         query.setParameter("id", id);
         rowsUpdated = query.executeUpdate();
//         em.getTransaction().commit();
         
         if(rowsUpdated != 0){
             rta = true; 
         }
         
         return rta;
    }
    
}

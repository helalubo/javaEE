/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.smartpointrest.mook.usuario.infrastructure.repositories;

import com.helalubo.smartpointrest.mook.usuario.domain.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Helalubo
 */

@Stateless
public class UsuarioRepository  implements IUsuarioRepository{

    
    @PersistenceContext(unitName = "SmartPointSource")
    EntityManager em;
    
    
    
    @Override
    public List<Usuario> encontrarTodosUsuarios() {
        return em.createNamedQuery("Usuario.encontrarTodosUsuarios").getResultList();
    }

    @Override
    public Usuario encontrarUsuario(Usuario usuario) {
       return em.find(Usuario.class, usuario.getIdUsuario());
    }

    @Override
    public void insertarUsuario(Usuario usuario) {
        em.persist(usuario);
        em.flush();
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        em.merge(usuario);
    }

    @Override
    public void eliminarPersona(Usuario usuario) {
        em.remove(em.merge(usuario));
    }
    
}

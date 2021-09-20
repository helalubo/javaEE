/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sga.mook.usuario.infrastructure.repositories;

import com.helalubo.sga.mook.usuario.domain.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless 
public class UsuarioRepository  implements IUsuarioRepository{

    
    @PersistenceContext(unitName="PersonaDb")
    EntityManager em;
    
    @Override
    public List<Usuario> findAll() {
         return em.createNamedQuery("Usuario.findAll").getResultList();
    }

    @Override
    public Usuario findUsuarioByEmailAndPassword(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

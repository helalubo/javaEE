package com.helalubo.mook.usuario.infrastructure.respositories;

import com.helalubo.mook.usuario.domain.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;


@Stateless 
public class UsuarioRepository implements IUsuarioRepository{
    
    @PersistenceContext(unitName="SgaPU")
    EntityManager em;

    @Override
    public List<Usuario> findAll(){
        return em.createNamedQuery("Usuario.findAll").getResultList();
    }

    
}

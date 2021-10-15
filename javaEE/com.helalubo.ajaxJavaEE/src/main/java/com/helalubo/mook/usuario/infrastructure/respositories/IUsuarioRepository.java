package com.helalubo.mook.usuario.infrastructure.respositories;

import com.helalubo.mook.usuario.domain.Usuario;
import java.util.List;


public interface IUsuarioRepository {
    public List<Usuario> findAll();
    
   
    
}

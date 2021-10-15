package com.helalubo.mook.usuario.application.services;

import com.helalubo.mook.usuario.domain.Usuario;
import java.util.List;
import javax.ejb.Local;


@Local
public interface UsuarioService {
      public List<Usuario> listarUsuarios();
    
    
    
}

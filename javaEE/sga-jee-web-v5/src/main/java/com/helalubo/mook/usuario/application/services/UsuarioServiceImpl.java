package com.helalubo.mook.usuario.application.services;

import com.helalubo.mook.usuario.domain.Usuario;
import com.helalubo.mook.usuario.infrastructure.respositories.IUsuarioRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;



@Stateless
public class UsuarioServiceImpl implements  UsuarioService{

    @Inject
    private IUsuarioRepository usuarioRepository;
    
    @Override
    public List<Usuario> listarUsuarios() {
       return usuarioRepository.findAll();
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sga.mook.usuario.application.services;

import com.helalubo.sga.mook.usuario.domain.Usuario;
import com.helalubo.sga.mook.usuario.infrastructure.repositories.UsuarioRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@Stateless
public class UsuarioService implements IUsuarioService{
    
        @Inject
        private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
      return usuarioRepository.findAll();
    }
    
}

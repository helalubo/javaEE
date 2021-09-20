/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.smartpointrest.mook.usuario.application.services;

import com.helalubo.smartpointrest.mook.usuario.domain.Usuario;
import com.helalubo.smartpointrest.mook.usuario.infrastructure.repositories.IUsuarioRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Helalubo
 */
@Stateless
public class UsuarioService implements IUsuarioService {

    @Inject
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> encontrarTodosUsuarios() {
       return usuarioRepository.encontrarTodosUsuarios();
    }

    @Override
    public Usuario encontrarUsuario(Usuario usuario) {
       return usuarioRepository.encontrarUsuario(usuario);

    }

    @Override
    public void insertarUsuario(Usuario usuario) {
        usuarioRepository.insertarUsuario(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.actualizarUsuario(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepository.eliminarPersona(usuario);
    }

}

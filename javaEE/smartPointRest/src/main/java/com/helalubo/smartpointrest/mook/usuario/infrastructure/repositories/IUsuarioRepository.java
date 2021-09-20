/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.smartpointrest.mook.usuario.infrastructure.repositories;

import com.helalubo.smartpointrest.mook.usuario.domain.Usuario;
import java.util.List;

/**
 *
 * @author Helalubo
 */
public interface IUsuarioRepository {
    
    public List<Usuario> encontrarTodosUsuarios();
    public Usuario encontrarUsuario(Usuario usuario);
    public void insertarUsuario(Usuario usuario);
    public void actualizarUsuario(Usuario usuario);
    public void eliminarPersona(Usuario usuario);
    
    
}

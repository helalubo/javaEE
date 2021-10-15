/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sga.mook.usuario.application.services;

import com.helalubo.sga.mook.usuario.domain.Usuario;
import java.util.List;

/**
 *
 * @author User
 */
public interface IUsuarioService {
        public List<Usuario> findAll();
}

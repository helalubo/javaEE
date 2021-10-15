/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.energizantesRest.mook.energizante.infrastructure.repositories;

import com.helalubo.energizantesRest.mook.energizante.domain.Energizante;
import java.util.List;

/**
 *
 * @author Helalubo
 */
public interface IEnergizanteRepository {
    
    public List<Energizante> encontrarTodos();
    public Energizante encontrarPorId(Energizante energizante);
    public void insertar(Energizante energizante);
    public void actualizar(Energizante energizante);
    public void eliminar(Energizante energizante);
    public boolean actualizarFoto(int id, String nombreFoto);
    
    
}

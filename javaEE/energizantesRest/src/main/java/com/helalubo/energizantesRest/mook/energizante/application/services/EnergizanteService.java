/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.energizantesRest.mook.energizante.application.services;

import com.helalubo.energizantesRest.mook.energizante.domain.Energizante;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.helalubo.energizantesRest.mook.energizante.infrastructure.repositories.IEnergizanteRepository;

/**
 *
 * @author Helalubo
 */
@Stateless
public class EnergizanteService implements IEnergizanteService {

    @Inject
    private IEnergizanteRepository energizanteRespository;

    @Override
    public List<Energizante> encontrarTodos() {
       return energizanteRespository.encontrarTodos();
    }

    @Override
    public Energizante encontrarPorId(Energizante energizante) {
       return energizanteRespository.encontrarPorId(energizante);

    }

    @Override
    public void insertar(Energizante energizante) {
        energizanteRespository.insertar(energizante);
    }

    @Override
    public void actualizar(Energizante energizante) {
        energizanteRespository.actualizar(energizante);
    }

    @Override
    public void eliminar(Energizante energizante) {
        energizanteRespository.eliminar(energizante);
    }

    @Override
    public boolean actualizarFoto(int id, String nombreFoto) {
     return  energizanteRespository.actualizarFoto(id,nombreFoto);
    }

}

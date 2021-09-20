/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.alumno.application.services;

import com.helalubo.sgawebhibernatejpa.mook.alumno.domain.Alumno;
import com.helalubo.sgawebhibernatejpa.mook.alumno.infrastructure.repositories.AlumnoRepository;
import java.util.List;

/**
 *
 * @author Helalubo
 */
public class AlumnoService {

    private AlumnoRepository alumnoRepository = new AlumnoRepository();

    public List<Alumno> listarAlumnos() {
        return alumnoRepository.Listar();
    }

    public void guardarAlumno(Alumno alumno) {

        if (alumno != null && alumno.getIdAlumno() == null) {
            alumnoRepository.insertar(alumno);
        } else {
            alumnoRepository.actualizar(alumno);

        }
    }

    public void eliminarAlumno(Alumno alumno) {
        alumnoRepository.eliminar(alumno);

    }

    public Alumno encontrarAlumno(Alumno alumno) {
        return alumnoRepository.buscarPorId(alumno);
    }

}

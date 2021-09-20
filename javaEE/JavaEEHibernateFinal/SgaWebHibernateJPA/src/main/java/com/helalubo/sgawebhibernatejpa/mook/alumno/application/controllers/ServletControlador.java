/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.alumno.application.controllers;

import com.helalubo.sgawebhibernatejpa.mook.alumno.application.services.AlumnoService;
import com.helalubo.sgawebhibernatejpa.mook.alumno.domain.Alumno;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Helalubo
 */
@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AlumnoService alumnoService = new AlumnoService();

        List<Alumno> alumnos = alumnoService.listarAlumnos();

        try {

            req.setAttribute("alumnos", alumnos);
            req.getRequestDispatcher("/WEB-INF/listarAlumnos.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

}

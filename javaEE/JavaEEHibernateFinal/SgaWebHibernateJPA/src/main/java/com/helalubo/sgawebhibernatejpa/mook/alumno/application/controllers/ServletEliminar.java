/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.alumno.application.controllers;

import com.helalubo.sgawebhibernatejpa.mook.alumno.application.services.AlumnoService;
import com.helalubo.sgawebhibernatejpa.mook.alumno.domain.Alumno;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ServletEliminar", urlPatterns = {"/ServletEliminar"})
public class ServletEliminar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AlumnoService alumnoService = new AlumnoService();
        Integer idAlumno = Integer.parseInt(req.getParameter("idAlumno"));
        Alumno alumno = new Alumno(idAlumno);

        alumnoService.eliminarAlumno(alumno);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }

}

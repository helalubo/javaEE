/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.mook.alumno.application.controllers;

import com.helalubo.sgawebhibernatejpa.mook.alumno.application.services.AlumnoService;
import com.helalubo.sgawebhibernatejpa.mook.alumno.domain.Alumno;
import com.helalubo.sgawebhibernatejpa.mook.contacto.domain.Contacto;
import com.helalubo.sgawebhibernatejpa.mook.domicilio.model.Domicilio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Helalubo
 */
@WebServlet(name = "ServletModificar", urlPatterns = {"/ServletModificar"})
public class ServletModificar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AlumnoService alumnoService = new AlumnoService();

        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String calle = req.getParameter("calle");
        String noCalle = req.getParameter("noCalle");
        String pais = req.getParameter("pais");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        HttpSession sesion = req.getSession();

        Alumno alumno = (Alumno) sesion.getAttribute("alumno");

        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.getDomicilio().setCalle(calle);
        alumno.getDomicilio().setNoCalle(noCalle);
        alumno.getDomicilio().setPais(pais);
        alumno.getContacto().setEmail(email);
        alumno.getContacto().setTelefono(telefono);

        alumnoService.guardarAlumno(alumno);
        sesion.removeAttribute("alumno");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AlumnoService alumnoService = new AlumnoService();

        Integer idAlumno = Integer.parseInt(req.getParameter("idAlumno"));
        Alumno alumno = new Alumno(idAlumno);
        alumno = alumnoService.encontrarAlumno(alumno);

//        req.setAttribute("alumno", alumno);
        HttpSession sesion = req.getSession();
        sesion.setAttribute("alumno", alumno);

        req.getRequestDispatcher("/WEB-INF/modificarAlumno.jsp").forward(req, resp);

    }

}

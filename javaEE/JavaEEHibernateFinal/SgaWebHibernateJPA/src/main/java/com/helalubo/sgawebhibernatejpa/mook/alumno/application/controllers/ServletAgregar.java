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

/**
 *
 * @author User
 */
@WebServlet(name = "ServletAgregar", urlPatterns = {"/ServletAgregar"})
public class ServletAgregar extends HttpServlet {

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

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(calle);
        domicilio.setNoCalle(noCalle);
        domicilio.setPais(pais);

        Contacto contacto = new Contacto();
        contacto.setEmail(email);
        contacto.setTelefono(telefono);

        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        
        
        alumno.setContacto(contacto);
        alumno.setDomicilio(domicilio);
        
        alumnoService.guardarAlumno(alumno);
        
        
         req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }

}

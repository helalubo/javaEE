<%-- 
    Document   : listarAlumnos
    Created on : 17-sep-2021, 0:47:34
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous"/>

        <title>Listado de Alumnos</title>
    </head>
    <body>
                    
        <div class="container-fluid text-center ">
            
            <h1 class="text-success">Listado de Alumnos</h1>
        </div>
        <div class="container-fluid m-auto mt-5 ">
            
            <a  href="${pageContext.request.contextPath}/ServletRedireccionar">Agregar</a>
        </div>
        

        
        <div class="container m-auto my-5">
            
        
        <table class="table">
           
            <tr>
                <th>Alumno ID</th>
                <th>Nombre</th>
                <th>Domicilio</th>
                <th>pais</th>
                <th>Telefono</th>
                <th>Email</th>
                <th>modificar</th>
                <th>eliminar</th>
                
            
            </tr>

            <c:forEach var="alumno" items="${alumnos}">
                <tr>
                    <td>${alumno.idAlumno}</td>
                    <td>${alumno.nombre}   ${alumno.apellido}</td>
                    <td>${alumno.domicilio.calle}   ${alumno.domicilio.noCalle}</td>
                    <td>${alumno.domicilio.pais}</td>
                    <td>${alumno.contacto.email}</td>
                    <td>${alumno.contacto.telefono}</td>
                    <td> <a class="btn btn-warning" href="${pageContext.request.contextPath}/ServletModificar?idAlumno=${alumno.idAlumno}" >Modificar</a></td>
                    <td> <a class="btn btn-danger" href="${pageContext.request.contextPath}/ServletEliminar?idAlumno=${alumno.idAlumno}">Eliminar</a></td>
                </tr>
            </c:forEach>

        </table>
        </div>
    </body>
</html>

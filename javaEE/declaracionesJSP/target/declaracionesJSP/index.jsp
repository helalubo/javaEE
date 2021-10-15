<%-- 
    Document   : index
    Created on : 7 oct, 6:36:40
    Author     : ubaldo
--%>

<%--   declaracaion global --%>
<%!   private String usuario = "Helalubo" ;

public String getUsuario(){
    return this.usuario;
}

private int contadorVisitas = 1;


%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Uso declaraciones</title>
    </head>
    <body>
        <h1>Uso declaraciones</h1>
        Valor de usuario por medio del atributo : <%= this.getUsuario()     %>
        Contador de visitas : <%= this.contadorVisitas++   %>
       
    </body>
</html>

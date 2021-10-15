<%-- 
    Document   : index
    Created on : 7 oct, 6:36:40
    Author     : Helalubo
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP con expresiones</title>
    </head>
    <body>
        <h1>JSP con expresiones</h1>
        <ul class="container">

            <h4>  <%= "saludos" + " " + "JSP" %>   </h4>
            <br>
                <h4> operacion matematica <%= 5+5 %>   </h4>
            <br>
                <h4> operacion matematica <%= session.getId()%>   </h4>
             <br>

             <a href="index.html">volver al inicio</<a>
             <br>
             <a href="expresiones.jsp">ver expresiones</<a>


        </ul>
    </body>
</html>

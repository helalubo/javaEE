<%-- 
    Document   : index
    Created on : 7 oct, 6:36:40
    Author     : ubaldo
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Ejemplo inclusion estatica</title>
    </head>
    <body>
        <h1>Ejemplo inclusion estatica</h1>
        <br/>
        <ul>
            <li> <%@include file="paginas/noticias1.html"%> </li>
            <li> <%@include file="paginas/noticias2.jsp"%> </li>
            <li> <%= "HolaMundo con Expresiones" %>   </li>
            <li> <c:out value="HolaMundo con JSTL" /> </li>
        </ul>
    </body>
</html>

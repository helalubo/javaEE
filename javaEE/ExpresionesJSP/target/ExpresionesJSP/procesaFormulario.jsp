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
        <title>HolaMundo JSPs</title>
    </head>
    <body>
     
        Usuario: <%= request.getParameter("usuario")    %>
        <br>
        Password: <%= request.getParameter("password")    %>

    </body>
</html>

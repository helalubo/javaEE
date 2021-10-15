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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <br/>
        Inclusion de contenido dinamico desde JSP publico
        <br/>
        Nombre de la aplicacion: <%=request.getContextPath()   %>
    </body>
</html>

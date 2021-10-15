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
        <title>ejemplo inclusion dinamica/title>
    </head>
    <body>
        <h1>ejemplo inclusion dinamica/h1>
        <br/>
     
          <jsp:include page="paginas/recursoPublico.jsp" /> 
          <jsp:include page="WEB-INF/recursoPrivado.jsp" > 
          <jsp:param name="colorFondo" value="blue"/>
          </jsp:include>


        </ul>
    </body>
</html>

<%@page isErrorPage="true" %>
<%@page import="java.io.*" %>
<!DOCTYPE html>
<html>


<head>
    <meta contentType="text/html" pageEncoding="UTF-8">
    <title>JSP MANEJO ERRORES</title>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>

<body>
    <h1>Generacion de Reporte Excel</h1>

    <%-- <br> --%>
    <%-- Ocurrio una excepcion: <%= excepcion.getMessage()    %> --%>
    <%-- <br> --%>
    <textarea cols="30" rows="5">
    <pre>
    <% exception.printStackTrace(new PrintWriter(out));   %>
    </pre>
    </textarea>

   
</body>

</html>
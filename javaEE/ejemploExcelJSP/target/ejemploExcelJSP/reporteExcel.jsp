
<%@page errorPage="/WEB-INF/manejoErrores.jsp"%>
<%@page import="util.Conversiones, java.util.Date"%>
<%@page contentType="application/vnd.ms-excel"%>

<% 
String nombreArchivo ="reporte.xls";
response.setHeader("Content-Disposition","inline;filename=" + nombreArchivo);
 %>

<!DOCTYPE html>
<html>


<head>
    <title>Generacion de Reporte Excel</title>
</head>

<body class="container">
    <h1>reporte de excel</h1>
   
   <br>

   <table border="1">
   
   <tr>
    <th>Curso</th>
    <th>Descripcion</th>
    <th>Fecha</th>
   </tr>
      <tr>
    <td>1-fundamentos de java</td>
    <td>Sintaxis basica </td>
    <td><%= Conversiones.format(new Date())    %></td>
   </tr>
         <tr>
    <td>1-fundamentos de java</td>
    <td>Sintaxis basica </td>
    <%-- <td><%= Conversiones.format("500")    %></td> --%>
   </tr>
   </table>

</body>

</html>
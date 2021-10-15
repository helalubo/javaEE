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
        <title>variables implicitas</title>
    </head>
    <body>
        <h1>variables implicitas</h1>
        
        <ul>
        <li>
         ${pageContext.request.contextPath}
        </li>
         <li>
             ${header["User-Agent"]}
        </li>
          <li>
         ${cookie.JSESSIONID.value}
        </li>
              <li>
         ${pageContext.servletContext.serverInfo}
        </li>
                    <li>
         ${param.usuario}
        </li>
        </ul>


   <br/>
   <a href="index.jsp" >inicio</a>

    </body>
</html>

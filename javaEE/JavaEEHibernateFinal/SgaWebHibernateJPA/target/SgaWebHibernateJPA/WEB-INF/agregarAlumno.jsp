<%-- 
    Document   : agregarAlumno
    Created on : 17-sep-2021, 1:15:46
    Author     : Helalubo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous"/>

        <title>Agregar Alumno</title>
    </head>
    <body>
        <h1>Agregar Alumno</h1>

        <form  class="container " name="form1" action="${pageContext.request.contextPath}/ServletAgregar" method="post">
            <div class="form-row">
                <h3 for="">Datos Personales:</h3>
                <div class="col-md-4 mb-3">
                    <label for="validationServer013">Nombre</label>
                    <input type="text" class="form-control " id="validationServer013" placeholder="nombre"  name="nombre"
                           >

                </div>
                <div class="col-md-4 mb-3">
                    <label for="validationServer023">Apellido</label>
                    <input type="text" class="form-control " id="validationServer023" placeholder="apellido"  name="apellido"
                           >

                </div>
                <h3 for="validationServerUsername33">Datos de domicilio:</h3>

                <div class="col-md-4 mb-3">
                    <div class="col-md-4 mb-3">
                        <label for="validationServer023">calle</label>
                        <input type="text" class="form-control "   placeholder="calle" name="calle"
                               >

                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationServer023">No. Calle</label>
                        <input type="text" class="form-control "   placeholder="Nro Calle" name="noCalle"
                               >

                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationServer023">Pais</label>
                        <input type="text" class="form-control "  placeholder="pais" name="pais"
                               >

                    </div>
                    <h3 for="validationServerUsername33">Datos de contacto</h3>

                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroupPrepend33">@</span>
                        </div>
                        <input type="text" class="form-control " id="validationServerUsername33" placeholder="email" name="email"
                               aria-describedby="inputGroupPrepend33" >

                    </div>
                </div>
            </div>
            <div class="form-row">

                <div class="col-md-3 mb-3">
                    <label for="validationServer043">telefono</label>
                    <input type="tel" class="form-control " id="validationServer043" placeholder="telefono" name="telefono"
                           >

                </div>

            </div>

            <button class="btn btn-primary" type="submit">Agregar</button>
        </form>


    </body>
</html>

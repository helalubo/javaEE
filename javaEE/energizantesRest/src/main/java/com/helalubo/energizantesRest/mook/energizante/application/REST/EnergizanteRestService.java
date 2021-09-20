/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.energizantesRest.mook.energizante.application.REST;

import com.helalubo.energizantesRest.mook.energizante.domain.Energizante;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.helalubo.energizantesRest.mook.energizante.application.services.IEnergizanteService;
import com.helalubo.energizantesRest.upload.Upload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Helalubo
 */
@Stateless
@Path("/energizante")
public class EnergizanteRestService {

    @Inject
    private IEnergizanteService energizanteService;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarEnergizantes() {
        Map<String, Object> response = new HashMap<>();
        List<Energizante> energizantes = energizanteService.encontrarTodos();

        if (energizantes != null) {
            response.put("energizantes", energizantes);
            response.put("success", true);

        } else {
            response.put("message", "No se encontraron energizantes");
            response.put("success", false);
        }

        return Response.status(Status.OK).entity(response).build();
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("{id}") //hace referencia al path: /usuario/{id} ej. /usuario/1;
    public Response encontrarEnergizante(@PathParam("id") int id) {

        Map<String, Object> response = new HashMap<>();

        Energizante energizante = energizanteService.encontrarPorId(new Energizante(id));

        if (energizante != null) {
            response.put("energizante", energizante);
            response.put("success", true);
            response.put("message", "Se encontro energizante exitosamente");
            return Response.status(Status.OK).entity(response).build();

        } else {
            response.put("message", "No se encontro energizante");
            response.put("success", false);
            return Response.status(Status.BAD_REQUEST).entity(response).build();

        }

    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response agregarEnergizante(Energizante energizante) {

        Map<String, Object> response = new HashMap<>();

        if (energizante.getNombre() != null) {
            energizanteService.insertar(energizante);
            response.put("energizante", energizante);
            System.out.println("energizante agregado " + energizante);
            response.put("success", true);
            response.put("mensaje", "Se pudo agregar el energizante");
            return Response.status(Status.OK).entity(response).build();
        } else {
            response.put("success", false);
            response.put("mensaje", "NO Se pudo agregar el energizante");
            return Response.status(Status.BAD_REQUEST).entity(response).build();
        }

    }

    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("{id}") //hace referencia al path: /usuario/{id} ej. /usuario/1;
    public Response modificarEnergizante(@PathParam("id") int id, Energizante energizanteModificado) {

        Map<String, Object> response = new HashMap<>();
        Energizante energizante = energizanteService.encontrarPorId(new Energizante(id));

        if (energizante != null) {
            energizanteModificado.setIdEnergizante(id);
            energizanteService.actualizar(energizanteModificado);

            response.put("mensaje", "se realizo la actualizacion correctamente");
            response.put("energizante", energizanteModificado);
            response.put("exito", true);

            System.out.println("Persona modificada:" + energizanteModificado);
            return Response.ok().entity(response).build();
        } else {
            response.put("mensaje", "No se encontro energizante a actualizar");
            response.put("exito", false);

            return Response.status(Status.NOT_FOUND).entity(response).build();

        }

    }

//    "C:/fotitos/"
    @POST
    @Consumes(value = MediaType.MULTIPART_FORM_DATA)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("upload/{id}")
    public Response actualizarFoto(
            @PathParam("id") int id,
            @FormDataParam("archivo") InputStream fileInputStream,
            @FormDataParam("archivo") FormDataContentDisposition fileFormDataContentDisposition) throws IOException {

        String fileName = null;
        String uploadFilePath = null;
        String pathFolder = "C:/Users/User/Documents/NetBeansProjects/mavenproject9/energizantesRest/src/main/java/com/helalubo/energizantesRest/img/";
        Map<String, Object> response = new HashMap<>();
        Energizante energizante = energizanteService.encontrarPorId(new Energizante(id));

        if (energizante.getNombre() != null) {

            try {

                fileName = fileFormDataContentDisposition.getFileName();
           uploadFilePath = writeToFileServer(fileInputStream,fileName,pathFolder);
        
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            //writeToFileServer esta en upload carpeta
            energizanteService.actualizarFoto(id, fileName);
            energizante = energizanteService.encontrarPorId(new Energizante(id));
            response.put("energizanteActualizado", energizante);

            response.put("success", true);
            response.put("pathActualizado", uploadFilePath);
            response.put("mensaje", "Se pudo actualizar foto");
            return Response.status(Status.OK).entity(response).build();
        } else {
            response.put("success", false);
            response.put("mensaje", "NO Se pudo agregar el energizante");
            return Response.status(Status.BAD_REQUEST).entity(response).build();
        }

    }

    public  String writeToFileServer(InputStream inputStream, String fileName, String path) throws IOException {
        
        OutputStream outputStream = null;
        String finalPath = path + fileName;
        
        try {
            
            outputStream = new FileOutputStream(new File(finalPath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != 1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally{
            outputStream.close();
        }
        return finalPath;
        
    }

    @DELETE
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("{id}") //hace referencia al path: /usuario/{id} ej. /usuario/1;
    public Response eliminarEnergizante(@PathParam("id") int id) {
        energizanteService.eliminar(new Energizante(id));
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "energizante eliminado con el id" + id);
        return Response.ok().entity(response).build();
    }

    private String writeToFileServer(InputStream fileInputStream, String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

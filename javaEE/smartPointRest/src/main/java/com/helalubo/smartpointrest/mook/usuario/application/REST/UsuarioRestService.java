/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.smartpointrest.mook.usuario.application.REST;

import com.helalubo.smartpointrest.mook.usuario.application.services.IUsuarioService;
import com.helalubo.smartpointrest.mook.usuario.domain.Usuario;
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

/**
 *
 * @author Helalubo
 */
@Stateless
@Path("/usuario")
public class UsuarioRestService {

    @Inject
    private IUsuarioService usuarioService;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<Usuario> listarPersonas() {
        List<Usuario> usuarios = usuarioService.encontrarTodosUsuarios();
        System.out.println("usuarios encontrados" + usuarios);
        return usuarios;
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("{id}") //hace referencia al path: /usuario/{id} ej. /usuario/1;
    public Usuario encontrarUsuario(@PathParam("id") int id) {
        Usuario usuario = usuarioService.encontrarUsuario(new Usuario(id));
        System.out.println("usuario encontrada " + usuario);
        return usuario;
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Usuario agregarUsuario(Usuario usuario) {
        usuarioService.insertarUsuario(usuario);

        System.out.println("Usuario agregado " + usuario);
        return usuario;
    }

    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("{id}") //hace referencia al path: /usuario/{id} ej. /usuario/1;
    public Response modificarUsuario(@PathParam("id") int id, Usuario usuarioModificado) {

        Usuario usuario = usuarioService.encontrarUsuario(new Usuario(id));

        if (usuario != null) {
            usuarioService.actualizarUsuario(usuarioModificado);

            System.out.println("Persona modificada:" + usuarioModificado);
            return Response.ok().entity(usuarioModificado).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();

        }

    }

    @DELETE
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("{id}") //hace referencia al path: /usuario/{id} ej. /usuario/1;
    public Response eliminarUsuario(@PathParam("id") int id) {
        usuarioService.eliminarUsuario(new Usuario(id));
        System.out.println("Persona eliminada con el id" + id);
        
        return Response.ok().build();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.Mook.persona.application.serviceRemote;

import com.helalubo.Mook.persona.domain.Persona;
import java.util.List;
import javax.ejb.Remote;



/**
 *
 * @author Helalubo
 */
@Remote
public interface PersonaServiceRemote {
    
    public List<Persona> ListarPersonas();
    public Persona encontrarPersonaPorId(Persona persona);
    public Persona encontrarPersonaPorEmail(Persona persona);
    public void registrarPersona(Persona persona);
    public void modificarPersona(Persona persona);
    public void eliminarPersona(Persona persona);
    
}

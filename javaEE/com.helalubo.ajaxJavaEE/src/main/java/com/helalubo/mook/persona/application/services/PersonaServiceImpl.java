package com.helalubo.mook.persona.application.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.helalubo.mook.persona.domain.Persona;
import com.helalubo.mook.persona.infrastructure.respositories.IPersonaRepository;
import javax.annotation.Resource;
import javax.ejb.SessionContext;

@Stateless
public class PersonaServiceImpl implements PersonaServiceRemote, PersonaService{

    @Inject
    private IPersonaRepository personaDao;
    
    //manejo de transacciones
    @Resource 
    private SessionContext contexto;
    
    @Override
    public List<Persona> listarPersonas() {
       return personaDao.findAllPersonas();
    }

    @Override
    public Persona encontrarPersonaPorId(Persona persona) {
        return personaDao.findPersonaById(persona);
    }

    @Override
    public Persona encontrarPersonaPorEmail(Persona persona) {
        return personaDao.findPersonaByEmail(persona);
    }

    @Override
    public void registrarPersona(Persona persona) {
        personaDao.insertPersona(persona);
    }

    @Override
    public void modificarPersona(Persona persona) {
        
        try {
        personaDao.updatePersona(persona);
            
        }catch( Throwable t){
            contexto.setRollbackOnly();
            t.printStackTrace(System.out);
            
        }
        
    }

    @Override
    public void eliminarPersona(Persona persona) {
        personaDao.deletePersona(persona);
    }
    
}

package com.helalubo.sga.mook.persona.infrastructrure.repositories;

import java.util.List;
import com.helalubo.sga.mook.persona.domain.Persona;

public interface PersonaDao {
    public List<Persona> findAllPersonas();
    
    public Persona findPersonaById(Persona persona);
    
    public Persona findPersonaByEmail(Persona persona);
    
    public void insertPersona(Persona persona);
    
    public void updatePersona(Persona persona);
    
    public void deletePersona(Persona persona);
    
}

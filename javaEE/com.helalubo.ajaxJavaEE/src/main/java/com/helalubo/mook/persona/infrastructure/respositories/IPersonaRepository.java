package com.helalubo.mook.persona.infrastructure.respositories;

import java.util.List;
import com.helalubo.mook.persona.domain.Persona;

public interface IPersonaRepository {
    public List<Persona> findAllPersonas();
    
    public Persona findPersonaById(Persona persona);
    
    public Persona findPersonaByEmail(Persona persona);
    
    public void insertPersona(Persona persona);
    
    public void updatePersona(Persona persona);
    
    public void deletePersona(Persona persona);
    
}

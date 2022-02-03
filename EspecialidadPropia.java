package pcup.domain.model;

import plataforma.domain.model.GeografiaPropia;

public class EspecialidadPropia extends GeografiaPropia {

	private String abreviatura;

	public EspecialidadPropia(long idRegistro)
	{
		super.setCdgGeografia(idRegistro);
	}
	
	public EspecialidadPropia() {
		
	}

	public void setAbreviaturaEspecialidad(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getAbreviaturaEspecialidad() {
		return abreviatura;
	}
	
}

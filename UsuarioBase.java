package com.closeup.mstr.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioBase {
	
	String usuario;
	String nombreCompleto;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}

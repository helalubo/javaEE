package com.closeup.mstr.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Respuesta {
	String errores;
	String erroresRegistrados;
	String cantidadLlamadas;
	String estado;
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getErrores() {
		return errores;
	}
	public void setErrores(String errores) {
		this.errores = errores;
	}
	public String getErroresRegistrados() {
		return erroresRegistrados;
	}
	public void setErroresRegistrados(String erroresRegistrados) {
		this.erroresRegistrados = erroresRegistrados;
	}
	public String getCantidadLlamadas() {
		return cantidadLlamadas;
	}
	public void setCantidadLlamadas(String cantidadLlamadas) {
		this.cantidadLlamadas = cantidadLlamadas;
	}
	
}

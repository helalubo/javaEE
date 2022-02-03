package com.closeup.mstr.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaisLaboratorio {
	
	String pais;
	String laboratorio;

	public PaisLaboratorio() {
		super();
	}

	public PaisLaboratorio(String pais, String laboratorio) {
		super();
		this.pais = pais;
		this.laboratorio = laboratorio;
	}

	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

}
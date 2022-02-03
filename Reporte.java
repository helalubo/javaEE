package com.closeup.mstr.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reporte {
	
	String id;
	String nombre;
	
	String dataSetId;
	String evento;

	public Reporte() {
	}

	public Reporte(String id, String nombre, String dataSetId, String evento) {
		super();
		this.id        = id;
		this.nombre    = nombre;
		this.dataSetId = dataSetId;
		this.evento    = evento;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
}
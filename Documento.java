package com.closeup.mstr.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Documento {

	String id;
	String nombre;

	String reportId;
	String evento;
	String usuarioMail;
	String usuarioId;


	public Documento() {
	}

	public Documento(String id, String nombre, String reportId, String evento,  String usuarioMail, String usuarioId) {
		super();
		this.id        = id;
		this.nombre    = nombre;
		this.reportId  = reportId;
		this.evento    = evento;
		this.usuarioMail   = usuarioMail;
		this.usuarioId   = usuarioId;
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

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getUsuarioMail() {
		return usuarioMail;
	}

	public void setUsuarioMail(String usuarioMail) {
		this.usuarioMail = usuarioMail;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}
}
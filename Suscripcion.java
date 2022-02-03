package com.closeup.mstr.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Suscripcion {
	
	String nombre;
	String grupo;
	String planificacion;
	String documento;
	
	List<Reporte>   reportes;
	List<Documento> documentos;
	
	String pais;
	String laboratorio;

	public Suscripcion() {
		super();
	}

	public Suscripcion(String pais, String laboratorio) {
		super();
		this.pais = pais;
		this.laboratorio = laboratorio;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getPlanificacion() {
		return planificacion;
	}
	public void setPlanificacion(String planificacion) {
		this.planificacion = planificacion;
	}

	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
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

	public List<Reporte> getReportes() {
		if(reportes == null) {
			reportes = new ArrayList<Reporte>();
		}

		return reportes;
	}
	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}

	public List<Documento> getDocumentos() {
		if(documentos == null) {
			documentos = new ArrayList<Documento>(); 
		}

		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
}
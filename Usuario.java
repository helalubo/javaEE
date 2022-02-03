package com.closeup.mstr.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {

	private String usuario;
	private String nombreCompleto;
	private String correo;
	private String descripcion;
	private String laboratorio;
	private String pais;
	private String genero;
	private String clave;
	private String fuerzaVenta;
	private String fuerzaVentaDemCdd;
	private String fuerzaVentaDemTd;
	private String grupo;


	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFuerzaVenta() {
		return fuerzaVenta;
	}

	public void setFuerzaVenta(String fuerzaVenta) {
		this.fuerzaVenta = fuerzaVenta;
	}

	public String getFuerzaVentaDemCdd() {
		return fuerzaVentaDemCdd;
	}

	public void setFuerzaVentaDemCdd(String fuerzaVentaDemCdd) {
		this.fuerzaVentaDemCdd = fuerzaVentaDemCdd;
	}

	public String getFuerzaVentaDemTd() {
		return fuerzaVentaDemTd;
	}

	public void setFuerzaVentaDemTd(String fuerzaVentaDemTd) {
		this.fuerzaVentaDemTd = fuerzaVentaDemTd;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;

	}
}
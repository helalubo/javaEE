package com.helalubo.mook.laboratorios.model;



public class Laboratorio {

    Integer cdg_laboratorio;
    String sicla_laboratorio;
    String des_laboratorio;
    String abrev_laboratorio;
    String pais;
    Integer nivel_lab;
    Integer cdg_jerarquia;
    String significativo;
    String cdg_tipo_lab;
    Integer importancia;
    
    
    
	public Laboratorio() {
		super();
	}
	public Laboratorio(Integer cdg_laboratorio, String sicla_laboratorio, String des_laboratorio,
			String abrev_laboratorio, String pais, Integer nivel_lab, Integer cdg_jerarquia, String significativo,
			String cdg_tipo_lab, Integer importancia) {
		super();
		this.cdg_laboratorio = cdg_laboratorio;
		this.sicla_laboratorio = sicla_laboratorio;
		this.des_laboratorio = des_laboratorio;
		this.abrev_laboratorio = abrev_laboratorio;
		this.pais = pais;
		this.nivel_lab = nivel_lab;
		this.cdg_jerarquia = cdg_jerarquia;
		this.significativo = significativo;
		this.cdg_tipo_lab = cdg_tipo_lab;
		this.importancia = importancia;
	}
	@Override
	public String toString() {
		return "Laboratorio [cdg_laboratorio=" + cdg_laboratorio + ", sicla_laboratorio=" + sicla_laboratorio
				+ ", des_laboratorio=" + des_laboratorio + ", abrev_laboratorio=" + abrev_laboratorio + ", pais=" + pais
				+ ", nivel_lab=" + nivel_lab + ", cdg_jerarquia=" + cdg_jerarquia + ", significativo=" + significativo
				+ ", cdg_tipo_lab=" + cdg_tipo_lab + ", importancia=" + importancia + "]";
	}
	public Integer getCdg_laboratorio() {
		return cdg_laboratorio;
	}
	public void setCdg_laboratorio(Integer cdg_laboratorio) {
		this.cdg_laboratorio = cdg_laboratorio;
	}
	public String getSicla_laboratorio() {
		return sicla_laboratorio;
	}
	public void setSicla_laboratorio(String sicla_laboratorio) {
		this.sicla_laboratorio = sicla_laboratorio;
	}
	public String getDes_laboratorio() {
		return des_laboratorio;
	}
	public void setDes_laboratorio(String des_laboratorio) {
		this.des_laboratorio = des_laboratorio;
	}
	public String getAbrev_laboratorio() {
		return abrev_laboratorio;
	}
	public void setAbrev_laboratorio(String abrev_laboratorio) {
		this.abrev_laboratorio = abrev_laboratorio;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Integer getNivel_lab() {
		return nivel_lab;
	}
	public void setNivel_lab(Integer nivel_lab) {
		this.nivel_lab = nivel_lab;
	}
	public Integer getCdg_jerarquia() {
		return cdg_jerarquia;
	}
	public void setCdg_jerarquia(Integer cdg_jerarquia) {
		this.cdg_jerarquia = cdg_jerarquia;
	}
	public String getSignificativo() {
		return significativo;
	}
	public void setSignificativo(String significativo) {
		this.significativo = significativo;
	}
	public String getCdg_tipo_lab() {
		return cdg_tipo_lab;
	}
	public void setCdg_tipo_lab(String cdg_tipo_lab) {
		this.cdg_tipo_lab = cdg_tipo_lab;
	}
	public Integer getImportancia() {
		return importancia;
	}
	public void setImportancia(Integer importancia) {
		this.importancia = importancia;
	}
    
    
    
    



}

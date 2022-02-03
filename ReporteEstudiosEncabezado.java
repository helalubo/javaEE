package pcup.domain.model;


public class ReporteEstudiosEncabezado{
	
	private int est_id;
	private int ord_id;
	private String linea1;
	private String linea2;
	private String linea3;
	private String tipo_pagina;
	private int columnas;
	private String nombre_hoja;
	private String tiene_descripcion2;
	
	public int getEst_id() {
		return est_id;
	}
	public void setEst_id(int est_id) {
		this.est_id = est_id;
	}
	public int getOrd_id() {
		return ord_id;
	}
	public void setOrd_id(int ord_id) {
		this.ord_id = ord_id;
	}
	public String getLinea3() {
		return linea3;
	}
	public void setLinea3(String linea3) {
		this.linea3 = linea3;
	}
	public String getLinea2() {
		return linea2;
	}
	public void setLinea2(String linea2) {
		this.linea2 = linea2;
	}
	public String getLinea1() {
		return linea1;
	}
	public void setLinea1(String linea1) {
		this.linea1 = linea1;
	}
	public String getTipo_pagina() {
		return tipo_pagina;
	}
	public void setTipo_pagina(String tipo_pagina) {
		this.tipo_pagina = tipo_pagina;
	}
	public int getColumnas() {
		return columnas;
	}
	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}
	public String getNombre_hoja() {
		return nombre_hoja;
	}
	public void setNombre_hoja(String nombre_hoja) {
		this.nombre_hoja = nombre_hoja;
	}
	public String getTiene_descripcion2() {
		return tiene_descripcion2;
	}

	public void setTiene_descripcion2(String tiene_descripcion2) {
		this.tiene_descripcion2 = tiene_descripcion2;
	}
	
}
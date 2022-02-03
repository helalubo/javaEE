package pcup.domain.model;

public class ReporteEstudiosCFG{
	
	int est_id;
	String pais;
	String esquema;
	String cdg_laboratorio;
	String fecha;
	String nombre_archivo;
	
	public int getEst_id() {
		return est_id;
	}
	public void setEst_id(int est_id) {
		this.est_id = est_id;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEsquema() {
		return esquema;
	}
	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}
	public String getCdg_laboratorio() {
		return cdg_laboratorio;
	}
	public void setCdg_laboratorio(String cdg_laboratorio) {
		this.cdg_laboratorio = cdg_laboratorio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNombre_archivo() {
		return nombre_archivo;
	}
	public void setNombre_archivo(String nombre_archivo) {
		this.nombre_archivo = nombre_archivo;
	}
}
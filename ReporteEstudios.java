package pcup.domain.model;

import java.util.LinkedList;


public class ReporteEstudios{
	
	private LinkedList<Float> valorDatos = new LinkedList<Float>();
	private int est_id;
	private int ord_id;
	private int linea;
	private int rnk;
	private String descripcion1;
	private String descripcion2;
	private String formato_linea;
	private String formato_num;
	
	public LinkedList<Float> getValorDatos() {
		return valorDatos;
	}

	public void addValorDatos(Float valorDatos) {
		this.valorDatos.add(valorDatos);
	}

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

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public int getRnk() {
		return rnk;
	}

	public void setRnk(int rnk) {
		this.rnk = rnk;
	}

	public String getDescripcion1() {
		return descripcion1;
	}

	public void setDescripcion1(String descripcion1) {
		this.descripcion1 = descripcion1;
	}

	public String getDescripcion2() {
		return descripcion2;
	}

	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}

	public String getFormato_linea() {
		return formato_linea;
	}

	public void setFormato_linea(String formato_linea) {
		this.formato_linea = formato_linea;
	}

	public String getFormato_num() {
		return formato_num;
	}

	public void setFormato_num(String formato_num) {
		this.formato_num = formato_num;
	}
	
}
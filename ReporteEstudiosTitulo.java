package pcup.domain.model;

public class ReporteEstudiosTitulo{
	
	private int est_id;
	private int ord_id;
	private int col_id;
	private int col_width;
	private String descripcion;
	private String col_align;
	
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

	public int getCol_id() {
		return col_id;
	}

	public void setCol_id(int col_id) {
		this.col_id = col_id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCol_width() {
		return col_width;
	}

	public void setCol_width(int col_width) {
		this.col_width = col_width;
	}

	public String getCol_align() {
		return col_align;
	}

	public void setCol_align(String col_align) {
		this.col_align = col_align;
	}

}
package pcup.domain.model;

import plataforma.domain.model.Metrica;




public class ReporteRepsSubTitulo{
	private String nombre = null;
	private String nombreExpo = null;
	private String nombreSQL = null;
	private String funcion = null;
	private Metrica metrica = null;
	private boolean esLabel = false;
	private String tooltip = null;
	
	
	
	/**
	 * @return the esLabel
	 */
	public boolean isEsLabel() {
		return esLabel;
	}
	/**
	 * @param esLabel the esLabel to set
	 */
	public void setEsLabel(boolean esLabel) {
		this.esLabel = esLabel;
	}
	/**
	 * @return the metrica
	 */
	public Metrica getMetrica() {
		return metrica;
	}
	/**
	 * @param metrica the metrica to set
	 */
	public void setMetrica(Metrica metrica) {
		this.metrica = metrica;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the nombreSQL
	 */
	public String getNombreSQL() {
		return nombreSQL;
	}
	/**
	 * @param nombreSQL the nombreSQL to set
	 */
	public void setNombreSQL(String nombreSQL) {
		this.nombreSQL = nombreSQL;
	}
	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}
	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}
	
	public String getFuncion()
	{
		return this.funcion;
	}
	/**
	 * @return the nombreExpo
	 */
	public String getNombreExpo() {
		return nombreExpo != null ? nombreExpo : nombre;
	}
	/**
	 * @param nombreExpo the nombreExpo to set
	 */
	public void setNombreExpo(String nombreExpo) {
		this.nombreExpo = nombreExpo;
	}
	

}
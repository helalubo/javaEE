package pcup.domain.model;

import java.util.LinkedList;
import java.util.List;

import plataforma.domain.model.FuerzaVenta;

public class ReporteRepsLinea{

	private String linea = "";
	private long level = 0L;
	private String apertura = null;
	private String inicioColumna = null;
	private String aperGeo = null;
	
	private FuerzaVenta fuerzaVenta = null;
	private List<String> datosMuestra = null;
	private List<String> datosInicio = null;
	
	public void ingresaDatoMuestra(String dato){
		if (this.datosMuestra == null){
			this.datosMuestra = new LinkedList<String>();
		}
		this.datosMuestra.add(dato);
	}
	
	public String getDatoMuestra(int indice){
		return this.datosMuestra == null || this.datosMuestra.size() < indice ? null : this.datosMuestra.get(indice);
	}
	
	/**
	 * @return the apertura
	 */
	public String getApertura() {
		return apertura;
	}
	/**
	 * @param apertura the apertura to set
	 */
	public void setApertura(String apertura) {
		this.apertura = apertura;
	}
	/**
	 * @return the datosMuestra
	 */
	public List<String> getDatosMuestra() {
		return datosMuestra;
	}
	/**
	 * @param datosMuestra the datosMuestra to set
	 */
	public void setDatosMuestra(List<String> datosMuestra) {
		this.datosMuestra = datosMuestra;
	}
	/**
	 * @return the fuerzaVenta
	 */
	public FuerzaVenta getFuerzaVenta() {
		return fuerzaVenta;
	}
	/**
	 * @param fuerzaVenta the fuerzaVenta to set
	 */
	public void setFuerzaVenta(FuerzaVenta fuerzaVenta) {
		this.fuerzaVenta = fuerzaVenta;
	}
	/**
	 * @return the level
	 */
	public long getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(long level) {
		this.level = level;
	}
	/**
	 * @return the linea
	 */
	public String getLinea() {
		return linea;
	}
	/**
	 * @param linea the linea to set
	 */
	public void setLinea(String linea) {
		this.linea = linea;
	}

	public void ingresarInicioColumna(String nombreSQL) 
	{
		if (this.datosInicio == null)
			this.datosInicio = new LinkedList<String>();
		 this.datosInicio.add(nombreSQL);
	}
	
	public String getBusquedaPerfil()
	{
		return inicioColumna;
	}

	public String getDatosInicial(int indice) {
		
		if (this.datosInicio.size() > indice)
		{
			return this.datosInicio.get(indice);
		}
		else
		{
			return "";
		}
	}

	public String getAperGeo() {
		return aperGeo;
	}

	public void setAperGeo(String aperGeo) {
		this.aperGeo = aperGeo;
	}
}
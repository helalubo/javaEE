package pcup.domain.model;

public class Solapa {

	private String cdg_solapa;
	private String id_solapa_html;
	private String nro_solapa_html;
	private String descripcion;
	private String cdg_label;
//	private String cdg_pais;
	private boolean validar_mercado;
	private int cantidad_elementos_minima;
	private String cdg_tipo_consulta;
	private String nombre_arreglo_datos;
	private String cdgRol;
	
	public String getNombreArregloDatos()
	{
		return this.nombre_arreglo_datos;
	}
	
	public void setNombreArregloDatos(String cdg_tipo)
	{
		this.nombre_arreglo_datos = cdg_tipo;
	}
	
	public int getCantidadElementosMinima()
	{
		return this.cantidad_elementos_minima;
	}
	
	public void setCantidadElementosMinima(int cantidad_elementos)
	{
		this.cantidad_elementos_minima = cantidad_elementos;
	}
	
	public String getCdgTipoConsulta()
	{
		return this.cdg_tipo_consulta;
	}
	
	public void setCdgTipoConsulta(String cdg_tipo)
	{
		this.cdg_tipo_consulta = cdg_tipo;
	}
	
	public void setCdgSolapa(String cdg_solapa) {
		this.cdg_solapa = cdg_solapa;
	}
	
	public boolean validarMercado()
	{
		return validar_mercado;
	}
	
	public void setValidarMercado(boolean validar)
	{
		validar_mercado = validar;
	}
	public String getCdgSolapa() {
		return cdg_solapa;
	}
	
	public void setIdSolapaHtml(String id_solapa_html) {
		this.id_solapa_html = id_solapa_html;
	}
	
	public String getIdSolapaHtml() {
		return id_solapa_html;
	}
	
	public void setNroSolapaHtml(String nro_solapa_html) {
		this.nro_solapa_html = nro_solapa_html;
	}
	
	public String getNroSolapaHtml() {
		return nro_solapa_html;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() 
	{
		return descripcion;
	}
	
	public void setCdgLabel(String cdg_label)
	{
		this.cdg_label = cdg_label;
	}
	
	public String getCdgLabel() {
		
		return cdg_label;
	}

	/**
	 * @return the cdg_rol
	 */
	public String getCdgRol() {
		return cdgRol;
	}

	/**
	 * @param cdg_rol the cdg_rol to set
	 */
	public void setCdgRol(String cdg_rol) {
		this.cdgRol = cdg_rol;
	}

//	public void setCdg_pais(String cdg_pais) {
//		this.cdg_pais = cdg_pais;
//	}

	
}

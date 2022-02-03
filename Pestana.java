package pcup.domain.model;

public class Pestana {

	private String prefijo;
	private String nombre;
	private String contenido;
	private boolean unoOMas = false;
	private String msgBloqueado = "";
	
	public Pestana(String prefijo, String nombre, String contenido) {
		this.prefijo = prefijo;
		this.nombre = nombre;
		this.contenido = contenido;
	}

	/**
	 * @return the contenido
	 */
	public String getContenido(){
		return contenido;
	}

	/**
	 * @return the prefijo
	 */
	public String getPrefijo() {
		return prefijo;
	}

	/**
	 * @param prefijo the prefijo to set
	 */
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
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
	 * Accion que se ejecuta despues de abrirTab
	 * @return
	 */
	public String accionAposAbrirTab() {
		return "";
	}

	public String getTipo() {
		return "generica";
	}

	/**
	 * @return the unoOMas
	 */
	public boolean isUnoOMas() {
		return unoOMas;
	}

	/**
	 * @param unoOMas the unoOMas to set
	 */
	public void setUnoOMas(boolean unoOMas) {
		this.unoOMas = unoOMas;
	}
	
	/**
	 * @return the msgBloqueado
	 */
	public String getMsgBloqueado() {
		return msgBloqueado;
	}

	/**
	 * @param msgBloqueado the msgBloqueado to set
	 */
	public void setMsgBloqueado(String msgBloqueado) {
		this.msgBloqueado = msgBloqueado;
	}
	
}

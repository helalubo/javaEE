package pcup.domain.model;

public class ReporteGerencial {

	private long linea = 0L;
	private int nivel = 0;
	private int orden = 0;
	private String tipo = null;
	private String descripcion = null;
	private String medicos = null;
	private String prescripciones = null;
	private String medicosVisitados = null;
	private String pxMedicosVisitados = null;
	private String pxMedicosNoVisitados = null;
	private String mediapx = null;
	
	public String getMediapx() {
		return mediapx;
	}
	public void setMediapx(String mediapx) {
		this.mediapx = mediapx;
	}
	private String medicosNoVisitados = null;
	private String indiceCobertura = null;
	private boolean abreGeografia = false;
	private boolean abreEspecialidad = false;
	private boolean abreProductos = false;
	private boolean abreCategorias = false;
	private boolean abreMercados = false;
	//RFC Cob Mol
	private boolean abreMolecula = false;
	
	private boolean abierto = false;
	private boolean sigue = false;
	private boolean contraer = false;
	private String cdgMercado = null;
	private String cdgGeografia = null;
	private String cdgEspecialidad = null;
	private String cdgProducto = null;
	//RFC Cob Mol
	private String cdgMolecula = null;
	private String cdgLinea = null;
	private String cdgCategoria = null;

	
	/**
	 * @return the contraer
	 */
	public boolean isContraer() {
		return contraer;
	}
	/**
	 * @param contraer the contraer to set
	 */
	public void setContraer(boolean contraer) {
		this.contraer = contraer;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}
	/**
	 * @return the sigue
	 */
	public boolean isSigue() {
		return sigue;
	}
	/**
	 * @param sigue the sigue to set
	 */
	public void setSigue(boolean sigue) {
		this.sigue = sigue;
	}
	/**
	 * @return the abierto
	 */
	public boolean isAbierto() {
		return abierto;
	}
	/**
	 * @param abierto the abierto to set
	 */
	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}
	/**
	 * @return the abreCategorias
	 */
	public boolean isAbreCategorias() {
		return abreCategorias;
	}
	
	public boolean isAbreMercados() {
		return abreMercados;
	}
	/**
	 * @param abreCategorias the abreCategorias to set
	 */
	public void setAbreCategorias(boolean abreCategorias) {
		this.abreCategorias = abreCategorias;
	}
	
	public void setAbreMercados(boolean abreMercados) {
		this.abreMercados = abreMercados;
	}
	/**
	 * @return the abreEspecialidad
	 */
	public boolean isAbreEspecialidad() {
		return abreEspecialidad;
	}
	/**
	 * @param abreEspecialidad the abreEspecialidad to set
	 */
	public void setAbreEspecialidad(boolean abreEspecialidad) {
		this.abreEspecialidad = abreEspecialidad;
	}
	/**
	 * @return the abreGeografia
	 */
	public boolean isAbreGeografia() {
		return abreGeografia;
	}
	/**
	 * @param abreGeografia the abreGeografia to set
	 */
	public void setAbreGeografia(boolean abreGeografia) {
		this.abreGeografia = abreGeografia;
	}
	/**
	 * @return the abreProductos
	 */
	public boolean isAbreProductos() {
		return abreProductos;
	}
	/**
	 * @param abreProductos the abreProductos to set
	 */
	public void setAbreProductos(boolean abreProductos) {
		this.abreProductos = abreProductos;
	}
	//RFC Cob Mol
	/**
	 * @return the abreMolecula
	 */
	public boolean isAbreMolecula() {
		return abreMolecula;
	}
	//RFC Cob Mol
	/**
	 * @param abreMolecula the abreMolecula to set
	 */
	public void setAbreMolecula(boolean abreMolecula) {
		this.abreMolecula = abreMolecula;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the indiceCobertura
	 */
	public String getIndiceCobertura() {
		return indiceCobertura;
	}
	/**
	 * @param indiceCobertura the indiceCobertura to set
	 */
	public void setIndiceCobertura(String indiceCobertura) {
		this.indiceCobertura = indiceCobertura;
	}
	/**
	 * @return the linea
	 */
	public long getLinea() {
		return linea;
	}
	/**
	 * @param linea the linea to set
	 */
	public void setLinea(long linea) {
		this.linea = linea;
	}
	/**
	 * @return the medicos
	 */
	public String getMedicos() {
		return medicos;
	}
	/**
	 * @param medicos the medicos to set
	 */
	public void setMedicos(String medicos) {
		this.medicos = medicos;
	}
	/**
	 * @return the medicosNoVisitados
	 */
	public String getMedicosNoVisitados() {
		return medicosNoVisitados;
	}
	/**
	 * @param medicosNoVisitados the medicosNoVisitados to set
	 */
	public void setMedicosNoVisitados(String medicosNoVisitados) {
		this.medicosNoVisitados = medicosNoVisitados;
	}
	/**
	 * @return the medicosVisitados
	 */
	public String getMedicosVisitados() {
		return medicosVisitados;
	}
	/**
	 * @param medicosVisitados the medicosVisitados to set
	 */
	public void setMedicosVisitados(String medicosVisitados) {
		this.medicosVisitados = medicosVisitados;
	}
	/**
	 * @return the nivel
	 */
	public int getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	/**
	 * @return the prescripciones
	 */
	public String getPrescripciones() {
		return prescripciones;
	}
	/**
	 * @param prescripciones the prescripciones to set
	 */
	public void setPrescripciones(String prescripciones) {
		this.prescripciones = prescripciones;
	}
	public String getCdgMercado() {
		return cdgMercado;
	}
	public void setCdgMercado(String cdgMercado) {
		this.cdgMercado = cdgMercado;
	}
	public String getCdgGeografia() {
		return cdgGeografia;
	}
	public void setCdgGeografia(String cdgGeografia) {
		this.cdgGeografia = cdgGeografia;
	}
	public String getCdgEspecialidad() {
		return cdgEspecialidad;
	}
	public void setCdgEspecialidad(String cdgEspecialidad) {
		this.cdgEspecialidad = cdgEspecialidad;
	}
	public String getCdgProducto() {
		return cdgProducto;
	}
	public void setCdgProducto(String cdgProducto) {
		this.cdgProducto = cdgProducto;
	}
	//RFC Cob Mol
	public String getCdgMolecula() {
		return cdgMolecula;
	}
	//RFC Cob Mol
	public void setCdgMolecula(String cdgMolecula) {
		this.cdgMolecula = cdgMolecula;
	}
	public String getCdgLinea() {
		return cdgLinea;
	}
	public void setCdgLinea(String cdgLinea) {
		this.cdgLinea = cdgLinea;
	}
	public String getCdgCategoria() {
		return cdgCategoria;
	}
	public void setCdgCategoria(String cdgCategoria) {
		this.cdgCategoria = cdgCategoria;
	}
	public String getPxMedicosVisitados() {
		return pxMedicosVisitados;
	}
	public void setPxMedicosVisitados(String pxMedicosVisitados) {
		this.pxMedicosVisitados = pxMedicosVisitados;
	}
	public String getPxMedicosNoVisitados() {
		return pxMedicosNoVisitados;
	}
	public void setPxMedicosNoVisitados(String pxMedicosNoVisitados) {
		this.pxMedicosNoVisitados = pxMedicosNoVisitados;
	}
	
}

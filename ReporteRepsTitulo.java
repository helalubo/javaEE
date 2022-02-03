package pcup.domain.model;

import java.util.LinkedList;
import java.util.List;

import plataforma.domain.model.Metrica;
import plataforma.domain.model.Periodo;
import plataforma.domain.model.Producto;
import plataforma.domain.model.Reporte;



public class ReporteRepsTitulo{

	private Periodo periodo = null;
	private Producto producto = null;
	private String nombre = null;
	private String nombreExpo = null;
	private List<ReporteRepsSubTitulo> subtitulos = null;
	private int[] indicesDatos = null;
	private String nombreSQL = null;
	private String funcion = null;
	private Metrica metrica = null;
	private Reporte reporte = null;
	private boolean esLabel = false;
	private String toolTip = null;	
	
	
	
	
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

	public boolean colocarTitulo(){
		if (this.sinSubtitulos())
			return reporte.estaLaMetrica(this.metrica);
		else
			return this.hayAlgunaMetrica();
	}
	
	private boolean hayAlgunaMetrica(){
		for (ReporteRepsSubTitulo subtitulo: this.subtitulos)
			if (reporte.estaLaMetrica(subtitulo.getMetrica()))
				return true;
			
		return false;
	}

	/**
	 * @return the reporte
	 */
	public Reporte getReporte() {
		return reporte;
	}

	/**
	 * @param reporte the reporte to set
	 */
	public void setReporte(Reporte reporte) {
		this.reporte = reporte;
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
	 * @return the subtitulos
	 */
	public List<ReporteRepsSubTitulo> getSubtitulos() {
		return subtitulos;
	}

	/**
	 * @param subtitulos the subtitulos to set
	 */
	public void setSubtitulos(List<ReporteRepsSubTitulo> subtitulos) {
		this.subtitulos = subtitulos;
	}

	public ReporteRepsTitulo(Periodo periodo) {
		this.periodo = periodo;
	}

	public boolean sinSubtitulos(){
		return subtitulos == null;
	}

	public void setFuncion(String funcion)
	{
		this.funcion = funcion;
	}

	public String getFuncion()
	{
		return this.funcion;
	}
	
	public boolean esDelPeriodo(Periodo cmp){
		return this.periodo.esElPeriodo(cmp);
	}
	
	
	
	/**
	 * @return the periodo
	 */
	public Periodo getPeriodo() {
		return periodo;
	}



	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public void addSubtitulo(String subtitulo, String campoSQL, String metrica, boolean esLabel,String orden, String tooltip, boolean esCantidadMedicos, String funcion){
//		addSubtitulo(subtitulo,null,campoSQL,metrica,esLabel,orden, null, esCantidadMedicos, funcion);
		addSubtitulo(subtitulo,null,campoSQL,metrica,esLabel,orden, tooltip, esCantidadMedicos, funcion);
	}

	public void addSubtitulo(String subtitulo, String subtituloExpo, String campoSQL, String metrica, boolean esLabel,String orden, String tooltip, boolean esCantidadMedicos,String funcion){
		if (this.subtitulos == null){
			this.subtitulos = new LinkedList<ReporteRepsSubTitulo>();
		}
		ReporteRepsSubTitulo sub = new ReporteRepsSubTitulo();
		sub.setNombre(subtitulo);
		sub.setNombreExpo(subtituloExpo);
		sub.setNombreSQL(campoSQL);
		sub.setFuncion(funcion);
		Metrica metricaDato = new Metrica(metrica);
		metricaDato.setOrdenDefaultMercado(orden);
		metricaDato.setEsCantidadMedicos(esCantidadMedicos);
		sub.setMetrica(metricaDato);
		sub.setEsLabel(esLabel);
		sub.setTooltip(tooltip);
		
		this.subtitulos.add(sub);
	}
	
	public int getSubtitulosSize(){
		if (this.sinSubtitulos())
			return (this.reporte.estaLaMetrica(this.metrica)) ? 1 : 0;
		else{
			int out = 0;
			for (ReporteRepsSubTitulo subtitulo: this.subtitulos)
				if (reporte.estaLaMetrica(subtitulo.getMetrica()))
					++out;
			return out;
		}
		
	}

	/**
	 * @return the indicesDatos
	 */
	public int[] getIndicesDatos() {
		return indicesDatos;
	}
	/**
	 * @param indicesDatos the indicesDatos to set
	 */
	public void setIndicesDatos(int[] indicesDatos) {
		this.indicesDatos = indicesDatos;
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
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre, boolean esLabel) {
		this.setNombre(nombre);
		this.setEsLabel(esLabel);
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

	public void agregarProducto(Producto producto) {
	
		this.producto = producto;
		
	}
	
	public Producto getProducto() {
		return this.producto;
	}
	
	/**
	 * @return Recupera el tool tip
	 */
	public String getToolTip() {
		return this.toolTip;
	}

	/**
	 * @param Establece el tool tip
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}	
}
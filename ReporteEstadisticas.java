package pcup.domain.model;

import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Pais;
import plataforma.domain.model.Usuario;


public class ReporteEstadisticas{
	
	private long cdgReporte = 0L;
	private long cantReportes = 0L;
	private String repConsulta = null;
	private String repGuardado = null;
	private String repSTD = null;
	private String usaGeoSTD = null;
	private Pais repPais = null;
	private Laboratorio repLaboratorio = null;
	private Usuario repUsuario = null;
	private String fechaCreacion = null;
	private String tipoConsultaUrl = null;
	private String horaEjecucion = null;
	private String cdg_tipo_consulta = "";
	
	private String origen=null;
	
	private String fechaInicioGeneracionDatos=null;
	private String horaInicioGeneracionDatos=null;
	private String fechaFinGeneracionDatos=null;
	private String horaFinGeneracionDatos=null;
	
	private String fechaInicioGeneracionArchivo=null;
	private String horaInicioGeneracionArchivo=null;
	private String fechaFinGeneracionArchivo=null;
	private String horaFinGeneracionArchivo=null;
	private String totalTiempoGeneracion=null;
	private String totalTiempoEspera=null;
	
	
	// Filtros
	private String fechaDesde = null;
	private String fechaHasta = null;
	
	private String tipoPerfil = null;
	private String perfilPublico = null;
	private String cdgFVenta = null;
	
	/**
	 * @return the fechaDesde
	 */
	public String getFechaDesde() {
		return fechaDesde;
	}
	/**
	 * @param fechaDesde the fechaDesde to set
	 */
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	/**
	 * @return the fechaHasta
	 */
	public String getFechaHasta() {
		return fechaHasta;
	}
	/**
	 * @param fechaHasta the fechaHasta to set
	 */
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	/**
	 * @return the cdgReporte
	 */
	public long getCdgReporte() {
		return cdgReporte;
	}
	/**
	 * @param cdgReporte the cdgReporte to set
	 */
	public void setCdgReporte(long cdgReporte) {
		this.cdgReporte = cdgReporte;
	}
	/**
	 * @return the repConsulta
	 */
	public String getRepConsulta() {
		return repConsulta;
	}
	/**
	 * @param repConsulta the repConsulta to set
	 */
	public void setRepConsulta(String repConsulta) {
		this.repConsulta = repConsulta;
	}
	/**
	 * @return the repGuardado
	 */
	public String getRepGuardado() {
		return repGuardado;
	}
	/**
	 * @param repGuardado the repGuardado to set
	 */
	public void setRepGuardado(String repGuardado) {
		this.repGuardado = repGuardado;
	}
	/**
	 * @return the repSTD
	 */
	public String getRepSTD() {
		return repSTD;
	}
	/**
	 * @param repSTD the repSTD to set
	 */
	public void setRepSTD(String repSTD) {
		this.repSTD = repSTD;
	}
	/**
	 * @return the usaGeoSTD
	 */
	public String getUsaGeoSTD() {
		return usaGeoSTD;
	}
	/**
	 * @param usaGeoSTD the usaGeoSTD to set
	 */
	public void setUsaGeoSTD(String usaGeoSTD) {
		this.usaGeoSTD = usaGeoSTD;
	}
	/**
	 * @return the repPais
	 */
	public Pais getRepPais() {
		return repPais;
	}
	/**
	 * @param repPais the repPais to set
	 */
	public void setRepPais(Pais repPais) {
		this.repPais = repPais;
	}
	/**
	 * @return the repLaboratorio
	 */
	public Laboratorio getRepLaboratorio() {
		return repLaboratorio;
	}
	/**
	 * @param repLaboratorio the repLaboratorio to set
	 */
	public void setRepLaboratorio(Laboratorio repLaboratorio) {
		this.repLaboratorio = repLaboratorio;
	}
	/**
	 * @return the repUsuario
	 */
	public Usuario getRepUsuario() {
		return repUsuario;
	}
	/**
	 * @param repUsuario the repUsuario to set
	 */
	public void setRepUsuario(Usuario repUsuario) {
		this.repUsuario = repUsuario;
	}
	/**
	 * @return the fechaCreacion
	 */
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the horaEjecucion
	 */
	public String getHoraEjecucion() {
		return horaEjecucion;
	}
	/**
	 * @param horaEjecucion the horaEjecucion to set
	 */
	public void setHoraEjecucion(String horaEjecucion) {
		this.horaEjecucion = horaEjecucion;
	}
	/**
	 * @param cantReportes the cantReportes to set
	 */
	public void setCantReportes(long cantReportes) {
		this.cantReportes = cantReportes;
	}
	/**
	 * @return the cantReportes
	 */
	public long getCantReportes() {
		return cantReportes;
	}
	/**
	 * @param tipoConsultaUrl the tipoConsultaUrl to set
	 */
	public void setTipoConsultaUrl(String tipoConsultaUrl) {
		this.tipoConsultaUrl = tipoConsultaUrl;
	}
	/**
	 * @return the tipoConsultaUrl
	 */
	public String getTipoConsultaUrl() {
		return tipoConsultaUrl;
	}
	
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getFechaInicioGeneracionDatos() {
		return fechaInicioGeneracionDatos;
	}
	public void setFechaInicioGeneracionDatos(String fechaInicioGeneracionDatos) {
		this.fechaInicioGeneracionDatos = fechaInicioGeneracionDatos;
	}
	public String getHoraInicioGeneracionDatos() {
		return horaInicioGeneracionDatos;
	}
	public void setHoraInicioGeneracionDatos(String horaInicioGeneracionDatos) {
		this.horaInicioGeneracionDatos = horaInicioGeneracionDatos;
	}
	public String getFechaFinGeneracionDatos() {
		return fechaFinGeneracionDatos;
	}
	public void setFechaFinGeneracionDatos(String fechaFinGeneracionDatos) {
		this.fechaFinGeneracionDatos = fechaFinGeneracionDatos;
	}
	public String getHoraFinGeneracionDatos() {
		return horaFinGeneracionDatos;
	}
	public void setHoraFinGeneracionDatos(String horaFinGeneracionDatos) {
		this.horaFinGeneracionDatos = horaFinGeneracionDatos;
	}
	public String getFechaInicioGeneracionArchivo() {
		return fechaInicioGeneracionArchivo;
	}
	public void setFechaInicioGeneracionArchivo(String fechaInicioGeneracionArchivo) {
		this.fechaInicioGeneracionArchivo = fechaInicioGeneracionArchivo;
	}
	public String getHoraInicioGeneracionArchivo() {
		return horaInicioGeneracionArchivo;
	}
	public void setHoraInicioGeneracionArchivo(String horaInicioGeneracionArchivo) {
		this.horaInicioGeneracionArchivo = horaInicioGeneracionArchivo;
	}
	public String getFechaFinGeneracionArchivo() {
		return fechaFinGeneracionArchivo;
	}
	public void setFechaFinGeneracionArchivo(String fechaFinGeneracionArchivo) {
		this.fechaFinGeneracionArchivo = fechaFinGeneracionArchivo;
	}
	public String getHoraFinGeneracionArchivo() {
		return horaFinGeneracionArchivo;
	}
	public void setHoraFinGeneracionArchivo(String horaFinGeneracionArchivo) {
		this.horaFinGeneracionArchivo = horaFinGeneracionArchivo;
	}
	public String getTotalTiempoGeneracion() {
		return totalTiempoGeneracion;
	}
	public void setTotalTiempoGeneracion(String totalTiempoGeneracion) {
		this.totalTiempoGeneracion = totalTiempoGeneracion;
	}
	public String getTotalTiempoEspera() {
		return totalTiempoEspera;
	}
	public void setTotalTiempoEspera(String totalTiempoEspera) {
		this.totalTiempoEspera = totalTiempoEspera;
	}
	public void setTipoPerfil(String tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}
	public String getTipoPerfil() {
		return tipoPerfil;
	}
	public void setPerfilPublico(String perfilPublico) {
		this.perfilPublico = perfilPublico;
	}
	public String getPerfilPublico() {
		return perfilPublico;
	}
	public void setCdgFVenta(String cdgFVenta) {
		this.cdgFVenta = cdgFVenta;
	}
	public String getCdgFVenta() {
		return cdgFVenta;
	}
	public String getCdg_tipo_consulta() {
		return cdg_tipo_consulta;
	}
	public void setCdg_tipo_consulta(String cdg_tipo_consulta) {
		this.cdg_tipo_consulta = cdg_tipo_consulta;
	}
}
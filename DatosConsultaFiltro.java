package pcup.domain.model;

import java.util.Date;

/**
 * de la base PCUP no mapea nada...???????
 * @author devel
 *
 */
public class DatosConsultaFiltro {

	
	private String nombre = null;
	private String matricula = null;
	private String[] cdg_especialidad = null;	
	private String buscarDomicilioPor = null;
	private String buscarPorVisita = null;
	private String cdg_region = null;
	private int cdg_ciudad;
	private String cdg_zonapostal = null;
	private String cdg_postal = null;
	private String cdg_postal_hasta = null;
	private String localidad = null;
	private String domicilio = null;
	private String municipio = null;
	private Date feAltaDesde = null;
	private Date feAltaHasta = null;
	private Date feBajaDesde = null;
	private Date feBajaHasta = null;
	private Date feUltModDesde = null;
	private Date feUltModHasta = null;
	private String ordenamientoPor = null;
	private String mercados = null;
	private String prodDesde = null;
	private String prodHasta = null;
	private String potDesde = null;
	private String potHasta = null;
	private String catDesde = null;
	private String catHasta = null;
	private long usr_id;
	
	public DatosConsultaFiltro(String nombre, String matricula, String[] cdg_especialidad, String buscarDomicilioPor, String buscarPorVisita, String cdg_region, int cdg_ciudad, String cdg_zonapostal, String cdg_postal,String cdg_postal_hasta, String localidad, String domicilio, String municipio, Date feAltaDesde, Date feAltaHasta, Date feBajaDesde, Date feBajaHasta, Date feUltModDesde, Date feUltModHasta, String ordenamientoPor, String mercados, String prodDesde, String prodHasta, String potDesde, String potHasta, String catDesde, String catHasta, long usr_id) {
		super();
		this.nombre = nombre;
		this.matricula = matricula;
		this.cdg_especialidad = cdg_especialidad;
		this.buscarDomicilioPor = buscarDomicilioPor;
		this.buscarPorVisita = buscarPorVisita;
		this.cdg_region = cdg_region;
		this.cdg_ciudad = cdg_ciudad;
		this.cdg_zonapostal = cdg_zonapostal;
		this.cdg_postal = cdg_postal;
		this.cdg_postal_hasta = cdg_postal_hasta;
		this.localidad = localidad;
		this.domicilio = domicilio;
		this.municipio = municipio;
		this.feAltaDesde = feAltaDesde;
		this.feAltaHasta = feAltaHasta;
		this.feBajaDesde = feBajaDesde;
		this.feBajaHasta = feBajaHasta;
		this.feUltModDesde = feUltModDesde;
		this.feUltModHasta = feUltModHasta;
		this.ordenamientoPor = ordenamientoPor;
		this.mercados = mercados;
		this.prodDesde = prodDesde;
		this.prodHasta = prodHasta;
		this.potDesde = potDesde;
		this.potHasta = potHasta;
		this.catDesde = catDesde;
		this.catHasta = catHasta;
		this.usr_id = usr_id;
	}

	public String getBuscarDomicilioPor() {
		return buscarDomicilioPor;
	}

	public void setBuscarDomicilioPor(String buscarDomicilioPor) {
		this.buscarDomicilioPor = buscarDomicilioPor;
	}

	public int getCdg_ciudad() {
		return cdg_ciudad;
	}

	public void setCdg_ciudad(int cdg_ciudad) {
		this.cdg_ciudad = cdg_ciudad;
	}

	public String[] getCdg_especialidad() {
		return cdg_especialidad;
	}

	public void setCdg_especialidad(String[] cdg_especialidad) {
		this.cdg_especialidad = cdg_especialidad;
	}

	public String getCdg_postal() {
		return cdg_postal;
	}

	public void setCdg_postal(String cdg_postal) {
		this.cdg_postal = cdg_postal;
	}

	public String getCdg_region() {
		return cdg_region;
	}

	public void setCdg_region(String cdg_region) {
		this.cdg_region = cdg_region;
	}

	public String getCdg_zonapostal() {
		return cdg_zonapostal;
	}

	public void setCdg_zonapostal(String cdg_zonapostal) {
		this.cdg_zonapostal = cdg_zonapostal;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Date getFeAltaDesde() {
		return feAltaDesde;
	}

	public void setFeAltaDesde(Date feAltaDesde) {
		this.feAltaDesde = feAltaDesde;
	}

	public Date getFeAltaHasta() {
		return feAltaHasta;
	}

	public void setFeAltaHasta(Date feAltaHasta) {
		this.feAltaHasta = feAltaHasta;
	}

	public Date getFeBajaDesde() {
		return feBajaDesde;
	}

	public void setFeBajaDesde(Date feBajaDesde) {
		this.feBajaDesde = feBajaDesde;
	}

	public Date getFeBajaHasta() {
		return feBajaHasta;
	}

	public void setFeBajaHasta(Date feBajaHasta) {
		this.feBajaHasta = feBajaHasta;
	}

	public Date getFeUltModDesde() {
		return feUltModDesde;
	}

	public void setFeUltModDesde(Date feUltModDesde) {
		this.feUltModDesde = feUltModDesde;
	}

	public Date getFeUltModHasta() {
		return feUltModHasta;
	}

	public void setFeUltModHasta(Date feUltModHasta) {
		this.feUltModHasta = feUltModHasta;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOrdenamientoPor() {
		return ordenamientoPor;
	}

	public void setOrdenamientoPor(String ordenamientoPor) {
		this.ordenamientoPor = ordenamientoPor;
	}

	public long getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}

	public String getBuscarPorVisita() {
		return buscarPorVisita;
	}

	public void setBuscarPorVisita(String buscarPorVisita) {
		this.buscarPorVisita = buscarPorVisita;
	}

	public String getCdg_postal_hasta() {
		return cdg_postal_hasta;
	}

	public void setCdg_postal_hasta(String cdg_postal_hasta) {
		this.cdg_postal_hasta = cdg_postal_hasta;
	}

	public String getMercados() {
		return mercados;
	}

	public void setMercados(String mercados) {
		this.mercados = mercados;
	}
	
	public String getCatDesde() {
		return catDesde;
	}

	public void setCatDesde(String catDesde) {
		this.catDesde = catDesde;
	}
	
	public String getCatHasta() {
		return catHasta;
	}

	public void setCatHasta(String catHasta) {
		this.catHasta = catHasta;
	}

	public String getPotDesde() {
		return potDesde;
	}

	public void setPotDesde(String potDesde) {
		this.potDesde = potDesde;
	}

	public String getPotHasta() {
		return potHasta;
	}

	public void setPotHasta(String potHasta) {
		this.potHasta = potHasta;
	}

	public String getProdDesde() {
		return prodDesde;
	}

	public void setProdDesde(String prodDesde) {
		this.prodDesde = prodDesde;
	}

	public String getProdHasta() {
		return prodHasta;
	}

	public void setProdHasta(String prodHasta) {
		this.prodHasta = prodHasta;
	}
	
	
}

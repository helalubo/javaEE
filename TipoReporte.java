package pcup.domain.model;

/**
 * 
 * @author Juan Alberto Villca <jvillca@learsoft.com.ar>
 *
 */
public class TipoReporte {

	private String cdgApertura;
	private String descApertura;
	private Long posicion;
	private String cdgLabel;
	private boolean drill = true;
	private boolean nivel1 = true;

	public boolean esElTipoReporte(String cmp){
		return this.cdgApertura != null && this.cdgApertura.equals(cmp);
	}

	/**
	 * @return the nivel1
	 */
	public boolean isNivel1() {
		return nivel1;
	}

	/**
	 * @param nivel1 the nivel1 to set
	 */
	public void setNivel1(boolean nivel1) {
		this.nivel1 = nivel1;
	}

	/**
	 * @return the drill
	 */
	public boolean isDrill() {
		return drill;
	}

	/**
	 * @param drill the drill to set
	 */
	public void setDrill(boolean drill) {
		this.drill = drill;
	}

	public TipoReporte(String cdgApertura) {
		super();
		this.cdgApertura = cdgApertura;
	}

	public TipoReporte() {
		super();
	}

	public TipoReporte(String cdgApertura, String descApertura,
			Long posicion, String cdgLabel) {
		super();
		this.cdgApertura = cdgApertura;
		this.descApertura = descApertura;
		this.posicion = posicion;
		this.cdgLabel = cdgLabel;
	}

	public String getCdgApertura() {
		return cdgApertura;
	}

	public void setCdgApertura(String cdgApertura) {
		this.cdgApertura = cdgApertura;
	}

	public String getDescApertura() {
		return descApertura;
	}

	public void setDescApertura(String descApertura) {
		this.descApertura = descApertura;
	}

	public Long getPosicion() {
		return posicion;
	}

	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}

	public String getCdgLabel() {
		return cdgLabel;
	}

	public void setCdgLabel(String cdgLabel) {
		this.cdgLabel = cdgLabel;
	}
	
	
}

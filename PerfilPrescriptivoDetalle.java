package pcup.domain.model;

public class PerfilPrescriptivoDetalle {

	private String codigo = null;
	private String descripcion = null;
	private int ranking = 0;
	private double porcentajeDetalle = 0D;
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	 * @return the porcentajeDetalle
	 */
	public double getPorcentajeDetalle() {
		return porcentajeDetalle;
	}
	/**
	 * @param porcentajeDetalle the porcentajeDetalle to set
	 */
	public void setPorcentajeDetalle(double porcentajeDetalle) {
		this.porcentajeDetalle = porcentajeDetalle;
	}
	/**
	 * @return the ranking
	 */
	public int getRanking() {
		return ranking;
	}
	/**
	 * @param ranking the ranking to set
	 */
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
	
}

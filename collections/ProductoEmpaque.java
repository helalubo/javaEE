package pcup.domain.model.collections;

/**
 * Clase para el manejo de datos de empaque/mercado por medio de un Objeto.
 * 
 * @author jangulo REQ1149 
 * @since 12/04/2019
 *
 */
public class ProductoEmpaque {
	String codigo;
	String descripcion;
	Boolean error;
	String errorDetalle = "";

	/**
	 * @param codigo
	 * @param descripcion
	 */
	public ProductoEmpaque(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		try {
			new Long(codigo);
		} catch (Exception exception) {
			errorDetalle = "Codigo debe ser numerico.";
		}
		if (codigo.length() > 12) {
			errorDetalle = "Codigo Excede el tamaño.";
		}
		this.descripcion = descripcion;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	
	/**
	 * @return the codigo
	 */
	public Long getCodigoToLong() {
		return new Long(codigo);
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the ErrorDetalle
	 */
	public String getErrorDetalle() {
		if (errorDetalle.isEmpty()) {
			errorDetalle = "OK";
		}
		return errorDetalle;
	}

	/**
	 * @param error the ErrorDetalle to set
	 */
	public void setErrorDetalle(String errorDetalle) {
		this.errorDetalle = errorDetalle;
	}

	/**
	 * @return the error
	 */
	public Boolean isError() {
		valida();
		return !errorDetalle.isEmpty();
	}

	private void valida() {
	
	}

}

package pcup.domain.model.collections;

import plataforma.domain.model.Usuario;

/**
 * Clase para definir la operaciòn AJAX a realizar, para el microstategy.
 * 
 * @author jangulo
 * @since 10/06/2019
 *
 */
public class TipoOperacionUsuario {
	Usuario usuario;
	String operacion;

	/**
	 * Constructor
	 * 
	 * @param usuario
	 * @param operacion
	 */
	public TipoOperacionUsuario(Usuario usuario, String operacion) {
		this.usuario = usuario;
		this.operacion = operacion;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}

}

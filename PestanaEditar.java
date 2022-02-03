/**
 * 
 */
package pcup.domain.model;

import pcup.util.Constantes;

import com.learsoft.web.LRSHtp;

/**
 * @author devel
 *
 */
public class PestanaEditar extends Pestana {

	private String functionJSGuardar;
	
	/**
	 * @param prefijo
	 * @param nombre
	 * @param contenido
	 */
	public PestanaEditar(String prefijo, String nombre, String functionJSGuardar) {
		super(prefijo, nombre, "");
		this.functionJSGuardar = functionJSGuardar;
	}
	
	@Override
	public String getContenido() {
		String out = "<div id='pestanaEditar' style='valign:top;'>";
		out += LRSHtp.fTableOpen(null, null, "100%", null, "class='tablaEditar'");
		
		out += LRSHtp.fTableRowOpen(null, "trEditarContenido", null, null);
		out += LRSHtp.fTableDataOpen(null, null, null, null);
		out += LRSHtp.fDivOpen("contenidoDatos", null, "id='contenidoDatos'");
		out += LRSHtp.fDivClose();
		out += LRSHtp.fTableDataClose();
		out += LRSHtp.fTableRowClose();
		
		out += guardarEditar();
		
		out += LRSHtp.fTableClose();
		out += "</div>";
		
		out += "<script>$('#botonGuardarEditadoDisabled').hide(); $('#pestanaEditar').hide()</script>";
		return out;
	}

	/**
	 * @param out
	 * @return
	 */
	protected String guardarEditar() {
		String out = "";
		out += LRSHtp.fTableRowOpen(null, "trEditarGuardar", null, null);
		out += LRSHtp.fTableDataOpen(null, null, null, "style='direction:rtl;'");
		out += LRSHtp.fImg("guardar_mercado.png", null, null, null, "id=\"botonGuardarEditado\" style=\"display:none;visibility:hidden;\" onclick=\"" + getFunctionJSGuardar() + ";\"", Constantes.PATH_IMGS_ICONOS);
		out += LRSHtp.fTableDataClose();
		out += LRSHtp.fTableRowClose();
		return out;
	}
	
	@Override
	public String accionAposAbrirTab() {
		return "cargarDatos()";
	}
	
	@Override
	public String getTipo() {
		return "editar";
	}

	/**
	 * @return the functionJSGuardar
	 */
	public String getFunctionJSGuardar() {
		return functionJSGuardar;
	}

	/**
	 * @param functionJSGuardar the functionJSGuardar to set
	 */
	public void setFunctionJSGuardar(String functionJSGuardar) {
		this.functionJSGuardar = functionJSGuardar;
	}

}

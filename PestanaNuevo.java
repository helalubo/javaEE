/**
 * 
 */
package pcup.domain.model;

import pcup.util.Constantes;
import pcup.util.HerramientasHtml;

import com.learsoft.web.LRSHtp;

/**
 * @author devel
 *
 */
public class PestanaNuevo extends Pestana {

	private String tituloTab = "";
	private String crearNuevo = "";
	private String functionJSCrearNuevo = "";
	private String cerrar = "";
	
	/**
	 *
	 * Ventanita de crear nuevo.
	 *  ---------------------------------------
	 * | TITULO                         CERRAR |
	 * |---------------------------------------|
	 * | DIV ACTUALIZABLE -> se carga con la   |
	 * | con la respuesta de la llamada ajax   |
	 * |                                       |
	 * | DIV GUARDAR -> llama a la funcion     |
	 * | retornada por getFunctionJSCrearNuevo |
	 *  ---------------------------------------
	 * 
	 * @param prefijo
	 * @param nombre
	 * @param tituloTab
	 * @param LBL_CREAR_NUEVO
	 * @param functionJSCrearNuevo
	 */
	public PestanaNuevo(String tituloTab, String LBL_CREAR_NUEVO, String functionJSCrearNuevo, String cerrar) {
		super("NUEVO", null, null);
		this.tituloTab = tituloTab;
		this.crearNuevo = LBL_CREAR_NUEVO;
		this.functionJSCrearNuevo = functionJSCrearNuevo;
		this.cerrar = cerrar;
	}

	
	
	@Override
	public String getContenido(){
		StringBuffer result = new StringBuffer("");
		// Titulo
		result.append(LRSHtp.fDivOpen("divCrearNuevo", "listContainerTitulo divFlotante", "id=\"divCrearNuevo\" style='display:none;'"));
		result.append(LRSHtp.fTableOpen("0","3","100%",null,null));
		result.append(LRSHtp.fTableRowOpen(null, "DivWindowTituloTR", null, null));
		result.append(LRSHtp.fTableData(tituloTab, "left", "DivWindowTituloTD", null, "colspan='2'"));
		result.append(LRSHtp.fTableData(HerramientasHtml.fBotonHtml(cerrar, 50, "cerrarCrearNuevo();", null), "right", "DivWindowTituloTD", null, null));
		result.append(LRSHtp.fTableRowClose());
		result.append(LRSHtp.fTableRowOpen(null,null,null,null));
		result.append(LRSHtp.fTableDataOpen(null, null, null, "colspan='3'"));	

		// div actualizable 
		result.append(LRSHtp.fDivOpen("divActualizableCrearNuevo", null, "style=\" overflow:visible; overflow-y:auto; overflow-x:hidden;\" id='divActualizableCrearNuevo'"));
		result.append(LRSHtp.fDivClose()); // div actualizable 

		// div guardar
		result.append(LRSHtp.fDivOpen(null, null, "style=\"overflow:visible; overflow-y:auto; overflow-x:hidden;\""));
		result.append(LRSHtp.fImg("guardar_mercado.png", null, null, this.crearNuevo, "id=\"botonCrearNuevo\" style=\"margin-rigth:8px;margin-top:2px;margin-bottom:2px;cursor:pointer;\" onclick=\"" + functionJSCrearNuevo + ";\"", Constantes.PATH_IMGS_ICONOS));
		result.append(LRSHtp.fImg("guardar_mercado_disabled.png", null, null,this.crearNuevo, "style=\"margin-rigth:8px;margin-top:2px;margin-bottom:2px;cursor:pointer;\" id=\"botonCrearNuevoDisabled\"", Constantes.PATH_IMGS_ICONOS));
		result.append(LRSHtp.fDivClose()); 	// div guardar
		
		result.append(LRSHtp.fTableDataClose());
		result.append(LRSHtp.fTableRowClose());
		result.append(LRSHtp.fTableClose());
		
		result.append(LRSHtp.fDivClose()); // divCrearNuevo
		
		return result.toString();
	}

}

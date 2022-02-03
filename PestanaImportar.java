package pcup.domain.model;

import com.learsoft.web.LRSHtp;
import plataforma.domain.model.Pestana;
import plataforma.util.HerramientasHtml;
import plataforma.web.lib.Styles;

/**
 * 
 * @author jangulo PXS-19, se modifica pestaña. 
 */
public class PestanaImportar extends Pestana {

	private String labelImportarGrupos = "***LBLIMPORTARGRUOPOS***";
	private String labelImportar = "***LBLIMPORTAR***";
	private String labelDescargar = "***LBLDESCARGAR***";
	private String idioma = "ESP";

	public void setLabelImportarGrupos(String label) {
		this.labelImportarGrupos = label;
	}

	public void setLabelImportar(String label) {
		this.labelImportar = label;
	}

	public void setLabelDescargar(String label) {
		this.labelDescargar = label;
	}

	public PestanaImportar(String prefijo, String nombre) {
		super(prefijo, nombre, "");
	}

	@Override
	public String getContenido() {
		String tab = getPrefijo();
		StringBuffer out = new StringBuffer();
		out.append("<div id='pestanaArboles" + tab + "' style='display:none;'>");
		out.append(LRSHtp.fFormOpen("AltMasForm", "AltaMasivaGrupoSeguimientoDatos", "post", "upload_target",
				"enctype=\"multipart/form-data\""));
		out.append(LRSHtp.fTableOpen("0", "0", "100%", null, ""));
		out.append(LRSHtp.fTableRowOpen("center", null, null, ""));
		out.append(LRSHtp.fTableDataOpen("center", null, null, ""));
		out.append(this.labelImportarGrupos);
		out.append(LRSHtp.fTableDataClose());
		out.append(LRSHtp.fTableDataOpen("center", null, null, ""));
		out.append(LRSHtp.fInputFile("parArchivo", null, null, null, null, null));
		out.append(LRSHtp.fTableDataClose());
		out.append(LRSHtp.fTableRowClose());
		out.append(LRSHtp.fTableRowOpen("center", null, null, ""));
		out.append(LRSHtp.fTableDataOpen("center", null, null, ""));
		out.append(LRSHtp.fInputButton("Procesar", this.labelImportar, Styles.COMMON_HTML_BUTTON,
				"onclick='submitArcExcel();'"));
		out.append(LRSHtp.fTableDataClose());
		out.append(LRSHtp.fTableDataOpen("center", null, null, ""));
		out.append(LRSHtp.fInputButton("Descargar", this.labelDescargar, Styles.COMMON_HTML_BUTTON,
				"onclick='descargarExcel(\"" + this.idioma + "\");'"));
		out.append(LRSHtp.fTableDataClose());
		out.append(LRSHtp.fTableRowClose());
		out.append(LRSHtp.fTableClose());
		out.append(LRSHtp.fInputHidden("accionProcesar", "procesarPlanilla", "id=\"accionProcesar\""));
		out.append(LRSHtp.fInputHidden("accion", null, "id=\"accion\""));
		out.append(LRSHtp.fInputHidden("cdgGrupo", null, "id=\"cdgGrupo\""));
		out.append(LRSHtp.fFormClose());
		out.append(LRSHtp.fDivOpen("listadoModificaciones", "listContainerTitulo divFlotante",
				"id=\"listadoModificaciones\" style='display:none;'"));
		out.append(LRSHtp.fTableOpen("0", "3", "100%", null, null));
		out.append(LRSHtp.fTableRowOpen(null, "DivWindowTituloTR", null, null));
		out.append(LRSHtp.fTableData("Resultados", "left", "DivWindowTituloTD", null, "colspan='2'"));
		out.append(LRSHtp.fTableData(HerramientasHtml.fBotonHtml("CERRAR", 50, "cerrarSeg();", null), "right",
				"DivWindowTituloTD", null, null));
		out.append(LRSHtp.fTableRowClose());
		out.append(LRSHtp.fTableRowOpen(null, null, null, null));
		out.append(LRSHtp.fTableDataOpen(null, null, null, "colspan='3'"));
		out.append(LRSHtp.fDivOpen("datosListadoMod", null,
				"style=\"font-size:9pt;aling:center;width:290px;height:260px;\" id='datosListadoMod'"));
		out.append(LRSHtp.fDivClose()); // div actualizable
		out.append(LRSHtp.fTableDataClose());
		out.append(LRSHtp.fTableRowClose());
		out.append(LRSHtp.fTableClose());
		out.append(LRSHtp.fDivClose()); // divCrearNuevo
		out.append("</div>"); // pestanaArboles
		out.append(LRSHtp.fIFrame("upload_target", "0", "0", "",
				"id='upload_target' style='width:0px;height:0px;border-width:0px;display:none;'"));
		out.append("<script>");
		out.append(getFuncionPrefijo());
		out.append("</script>");
		return out.toString();
	}

	protected int getAlto() {
		return 574;
	}

	protected void armarSeleccionados(String tab, StringBuffer out) {
		out.append("<div id='listaSeleccion" + tab
				+ "' class='linksHome' style=\"width:100%;overflow:auto;clear:both;direction: ltr;\">");
		out.append("</div>"); // listaSeleccion
	}

	protected void armarArbolesAjax(String tab, StringBuffer out) {
		out.append("<div id='arbolAjax" + tab + "Externo'> <div id='arbolAjax" + tab + "' tab='" + tab
				+ "' style=\"width:100%;overflow:auto;clear:both;direction: ltr;\"></div>");
		out.append("</div>"); // arbolAjax
	}

	/**
	 * @return la funcion para obtener el prefijo de los items del arbol
	 */
	protected String getFuncionPrefijo() {
		return "function " + getPrefijo() + "GetPrefijo(){ return '" + getPrefijoItemsArbol() + "'; }";
	}

	/**
	 * @return el prefijo de los items del arbol
	 */
	protected String getPrefijoItemsArbol() {
		return getPrefijo();
	}

	@Override
	public String accionAposAbrirTab() {
		return "$('#pestanaArboles" + getPrefijo() + "').show();";
	}

	@Override
	public String getTipo() {
		return "importar	";
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getIdioma() {
		return idioma;
	}

}

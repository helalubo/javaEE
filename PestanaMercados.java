package pcup.domain.model;

//import pcup.web.paginas.PestanaArbolesAjax;
import plataforma.web.paginas.PestanaArbolesAjax;

public class PestanaMercados extends PestanaArbolesAjax {
	
	private String labelNombre = "**nombre**";
	private String labelBuscar = "**buscar**";

	public PestanaMercados(String prefijo, String nombre, String contenido) {
		super(prefijo, nombre, contenido);
	}

	public PestanaMercados(String prefijoMercados, String label) {
		super(prefijoMercados, label, "");
	}
	
	protected void armarCombo(StringBuffer out) {
		/*out.append( "<div id='comboArbolesDiv' class='comboArbolesClass'>" );
		
		out.append( "<table class='tablaBusquedaMercados'><tr><td><span>"+labelNombre+"</span>" );
		out.append( LRSHtp.fInputText("nombreMercado", null, null, "50", null, "id=\"nombreMercado\" onkeyup='enterPressedBuscarMercado(event)'") );
		out.append( "</td>" );
		
		out.append( "<td>" );
		out.append( LRSHtp.fImg("iconoLupa2.gif", null, null,null,"onClick=\"realizarBusquedaMercados();\" Style=\"cursor:pointer\" title='"+labelBuscar+"'", Constantes.PATH_IMGS+"iconos/") );
		out.append( "</td></tr></table>" );
		
		out.append( "</div>" ); // comboArbolesDiv*/
	}
	
	@Override
	protected int getAlto() {
		return 300;
	}

	public String getLabelNombre() {
		return labelNombre;
	}

	public void setLabelNombre(String labelNombre) {
		this.labelNombre = labelNombre;
	}

	public String getLabelBuscar() {
		return labelBuscar;
	}

	public void setLabelBuscar(String labelBuscar) {
		this.labelBuscar = labelBuscar;
	}

}

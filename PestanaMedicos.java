package pcup.domain.model;

import pcup.util.Constantes;
//import pcup.web.paginas.PestanaArbolesAjax;
import plataforma.web.paginas.PestanaArbolesAjax;

import com.learsoft.web.LRSHtp;

public class PestanaMedicos extends PestanaArbolesAjax {
	
	private String labelNombre = "**nombre**";
	private String labelMatricula = "**matricula**";
	private String labelBuscar = "**buscar**";
	//REQ #1778
	private String labelRegion = "**region**";

	public PestanaMedicos(String prefijo, String nombre, String contenido) {
		super(prefijo, nombre, contenido);
	}

	public PestanaMedicos(String prefijoMedicos, String label) {
		super(prefijoMedicos, label, "");
	}
	
	protected void armarCombo(StringBuffer out) {
		out.append( "<div id='comboArbolesDiv' class='comboArbolesClass'>" );
		
		out.append( "<table class='tablaBusquedaMedicos'><tr><td><span>"+labelNombre+"</span>" );
		out.append( LRSHtp.fInputText("nombreMedico", null, null, "50", null, "id=\"nombreMedico\" onkeyup='enterPressedBuscarMedico(event)'") );
		out.append( "</td>" );
		
		out.append( "<td><span>"+labelMatricula+"</span>" );
		out.append( LRSHtp.fInputText("matriculaMedico", null, null, "50", null, "id=\"matriculaMedico\" onkeyup='enterPressedBuscarMedico(event)'") );
		out.append( "</td>" );
		
		//REQ #1778
		out.append( "<td><span>"+labelRegion+"</span>" );
		out.append( LRSHtp.fInputText("regionMedico", null, null, "50", null, "id=\"regionMedico\" onkeyup='enterPressedBuscarMedico(event)'") );
		out.append( "</td>" );
		
		out.append( "<td>" );
		out.append( LRSHtp.fImg("iconoLupa2.gif", null, null,null,"onClick=\"document.formHidden.registroDesde.value=1; realizarBusquedaMedicos();\" Style=\"cursor:pointer\" title='"+labelBuscar+"'", Constantes.PATH_IMGS+"iconos/") );
		out.append( "</td></tr></table>" );
		
		out.append( "</div>" ); // comboArbolesDiv
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

	public String getLabelMatricula() {
		return labelMatricula;
	}

	public void setLabelMatricula(String labelMatricula) {
		this.labelMatricula = labelMatricula;
	}

	//REQ #1778
	public String getLabelRegion() {
		return labelRegion;
	}

	//REQ #1778
	public void setLabelRegion(String labelRegion) {
		this.labelRegion = labelRegion;
	}

	public String getLabelBuscar() {
		return labelBuscar;
	}

	public void setLabelBuscar(String labelBuscar) {
		this.labelBuscar = labelBuscar;
	}

}

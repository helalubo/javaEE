package pcup.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Esta clase mapea la entidad "Mercados_Filtros_Cab"
 * @author jlacoste
 *
 */
public class MercadoFiltroCabecera{
	
	
	
	private Long codMercado;	
	private Integer secuencia;
	private String codTipoFiltro;
	private String descMerFiltro;
	private Integer nivelOperacion;
	private String codOperacion;
	private Integer secuenciaPadre;

	private List<MercadoFiltro> filtros;
	
	public MercadoFiltroCabecera(Long codMercado, Integer secuencia) {
		super();
		this.codMercado = codMercado;
		this.secuencia = secuencia;
	}

	public MercadoFiltroCabecera(){
		super();
		descMerFiltro = null;
		nivelOperacion = null;
		codOperacion = null;
		secuenciaPadre = null;
	}
	
	/**
	 * @return the codMercado
	 */
	public Long getCodMercado() {
		return codMercado;
	}

	/**
	 * @param codMercado the codMercado to set
	 */
	public void setCodMercado(Long codMercado) {
		this.codMercado = codMercado;
	}

	/**
	 * @return the secuencia
	 */
	public Integer getSecuencia() {
		return secuencia;
	}

	/**
	 * @param secuencia the secuencia to set
	 */
	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public MercadoFiltroCabecera(Long codMercado,
			 Integer secuencia, String descMerFiltro,
			Integer nivelOperacion, String codOperacion, Integer secuenciaPadre) {
		this.descMerFiltro = descMerFiltro;
		this.nivelOperacion = nivelOperacion;
		this.codOperacion = codOperacion;
		this.secuenciaPadre = secuenciaPadre;
	}
	
	public String getDescMerFiltro() {
		return descMerFiltro;
	}
	public void setDescMerFiltro(String descMerFiltro) {
		this.descMerFiltro = descMerFiltro;
	}
	public Integer getNivelOperacion() {
		return nivelOperacion;
	}
	public void setNivelOperacion(Integer nivelOperacion) {
		this.nivelOperacion = nivelOperacion;
	}
	public String getCodOperacion() {
		return codOperacion;
	}
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}
	public Integer getSecuenciaPadre() {
		return secuenciaPadre;
	}
	public void setSecuenciaPadre(Integer secuenciaPadre) {
		this.secuenciaPadre = secuenciaPadre;
	}

	public String getCodTipoFiltro() {
		return codTipoFiltro;
	}

	public void setCodTipoFiltro(String codTipoFiltro) {
		this.codTipoFiltro = codTipoFiltro;
	}

	public void setFiltros(List<MercadoFiltro> filtros) {
		this.filtros = filtros;
	}

	public List<MercadoFiltro> getFiltros() {
		return filtros;
	}

	/**
	 * Transforma un objeto JSON con un MercadoFiltroCabecera en un MercadoFiltroCabecera
	 * @param mercadoFiltroCabeceraJSON el objeto json
	 * @return el objeto MercadoFiltroCabecera
	 * @throws JSONException
	 */
	public static MercadoFiltroCabecera parseJSON(JSONObject mercadoFiltroCabeceraJSON) throws JSONException {
		String codigo = mercadoFiltroCabeceraJSON.getString( "codMercado" );
		if (codigo == null || "null".equals(codigo)) codigo = "0";
		MercadoFiltroCabecera mercadoFiltroCabecera = 
			new MercadoFiltroCabecera(Long.parseLong(codigo), 
									  mercadoFiltroCabeceraJSON.getInt("secuencia"));
		mercadoFiltroCabecera.setNivelOperacion(mercadoFiltroCabeceraJSON.getInt("nivelOperacion"));
		mercadoFiltroCabecera.setCodTipoFiltro( mercadoFiltroCabeceraJSON.getString("codTipoFiltro"));
		if ( mercadoFiltroCabeceraJSON.has("codOperacion") )
			mercadoFiltroCabecera.setCodOperacion( mercadoFiltroCabeceraJSON.getString("codOperacion") );
		
		mercadoFiltroCabecera.setFiltros(new ArrayList<MercadoFiltro>());
		
		JSONArray filtros = mercadoFiltroCabeceraJSON.getJSONArray("filtros");
		for ( int k = 0; k < filtros.length(); k++ ) {
			JSONObject filtroJSON = filtros.getJSONObject(k);
			String descripcion = "";
			if (filtroJSON.has("descripcion")) descripcion = filtroJSON.getString("descripcion");
			if ("".equals(descripcion) && filtroJSON.has("descFiltro")) descripcion = filtroJSON.getString("descFiltro");
			
			MercadoFiltro filtro = new MercadoFiltro();
			filtro.setSecuencia(filtroJSON.getInt("secuencia"));
			filtro.setDescripcion( descripcion );
			filtro.setCodFiltro(filtroJSON.getString("codFiltro"));
			filtro.setCodTipoFiltro(filtroJSON.getString("codTipoFiltro"));
			mercadoFiltroCabecera.getFiltros().add( filtro );
		}
		return mercadoFiltroCabecera;
	}

	
}
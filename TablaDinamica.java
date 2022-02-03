package pcup.domain.model;

import plataforma.domain.model.Cabecera;
import plataforma.domain.model.Usuario;

public class TablaDinamica implements Cabecera {

	public long getCdgEtdConsulta() {
		return cdgEtdConsulta;
	}

	public void setCdgEtdConsulta(long cdgEtdConsulta) {
		this.cdgEtdConsulta = cdgEtdConsulta;
	}

	public String getCdgTipoConsulta() {
		return cdgTipoConsulta;
	}

	public void setCdgTipoConsulta(String cdgTipoConsulta) {
		this.cdgTipoConsulta = cdgTipoConsulta;
	}

	public String getQueryDatos() {
		return queryDatos;
	}

	public void setQueryDatos(String queryDatos) {
		this.queryDatos = queryDatos;
	}

	public String getQueryResultados() {
		return queryResultados;
	}

	public void setQueryResultados(String queryResultados) {
		this.queryResultados = queryResultados;
	}

	public String getDescConsulta() {
		return descConsulta;
	}

	public void setDescConsulta(String descConsulta) {
		this.descConsulta = descConsulta;
	}

	public long getUsuario() {
		return usuario;
	}

	private long cdgEtdConsulta;
	private long usuario;
	private String cdgTipoConsulta;
	private String queryDatos;
	private String queryResultados;
	private String descConsulta;
	
	public String getDescripcion() {
		return cdgTipoConsulta + " - "+ descConsulta;
	}

	public String getCdg() {
		return Long.toString(cdgEtdConsulta);
	}

	public void setUsuario(long parUsuario) {
		
		usuario = parUsuario;
	}

	public boolean getPuedeBorrar(Usuario auth) {
		return false;
	}

	
}

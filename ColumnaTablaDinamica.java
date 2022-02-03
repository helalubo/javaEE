package pcup.domain.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import pcup.domain.service.ServicioExportacionTablaDinamica;
import plataforma.domain.model.Usuario;
import plataforma.domain.service.ServicioLabel;

import com.learsoft.exceptions.LRSException;
import com.learsoft.jxl.LRSXlsPOI;

public class ColumnaTablaDinamica {

	private long cdgColumna;
	private long cdgEtdConsulta;
	private long orden;
	private String nombreColSql;
	private String nombreColVista;
	private String queryAsociada;
	private String nombreHojaTabla;
	private String valor;
	
	public String getNombreHojaTabla() {
		return nombreHojaTabla;
	}
	public void setNombreHojaTabla(String nombreHojaTabla) {
		this.nombreHojaTabla = nombreHojaTabla;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public long getCdgColumna() {
		return cdgColumna;
	}
	public void setCdgColumna(long cdgColumna) {
		this.cdgColumna = cdgColumna;
	}
	public long getCdgEtdConsulta() {
		return cdgEtdConsulta;
	}
	public void setCdgEtdConsulta(long cdgEtdConsulta) {
		this.cdgEtdConsulta = cdgEtdConsulta;
	}
	public String getNombreColSql() {
		return nombreColSql;
	}
	public void setNombreColSql(String nombreColSql) {
		this.nombreColSql = nombreColSql;
	}
	public String getNombreColVista() {
		return nombreColVista;
	}
	public void setNombreColVista(String nombreColVista) {
		this.nombreColVista = nombreColVista;
	}
	public String getQueryAsociada() {
		return queryAsociada;
	}
	public void setQueryAsociada(String queryAsociada) {
		this.queryAsociada = queryAsociada;
	}
	public long getOrden() {
		return orden;
	}
	public void setOrden(long orden) {
		this.orden = orden;
	}
	public void cargarColumna(JSONObject objetoColumna, ServicioExportacionTablaDinamica servicio, ServicioLabel servicioLabel, Usuario auth, ResultSet rs) throws JSONException, LRSException, SQLException {
		
		cargarColumnaInterna(objetoColumna, servicio,
				servicioLabel, auth, rs);
	}
	
	protected void cargarColumnaInterna(JSONObject objetoColumna,
			ServicioExportacionTablaDinamica servicio,
			ServicioLabel servicioLabel, Usuario auth, ResultSet rs) throws JSONException,
			LRSException, SQLException {
		this.setCdgColumna(objetoColumna.getLong("id"));
		this.setOrden(objetoColumna.getLong("orden"));
		
		
		this.setNombreColSql(rs.getString("nombre_col_sql"));
		this.setNombreColVista(servicioLabel.getLabel(auth, rs.getString("nombre_col_vista")));
		this.setQueryAsociada(rs.getString("query_asociada"));
		this.setNombreHojaTabla(rs.getString("nombre_tabla"));
	}
	
	public void imprimirHeader(LRSXlsPOI xls) {
		
		xls.tableData(this.getNombreColVista(), "left", "celdaTablaDinamica", null, null);		
	}
	
	public void imprimirDato(LRSXlsPOI xls, ResultSet rs) throws SQLException {
		
		xls.tableData(rs.getString(this.getNombreColSql()), "celdaTablaDinamica", null, null, null);
	}
	
}

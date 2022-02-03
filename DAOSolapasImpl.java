package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOSolapas;
import pcup.domain.model.Solapa;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.TipoConsulta;
import plataforma.domain.model.Usuario;

public class DAOSolapasImpl extends DAOGenericoImpl<Solapa, String> implements DAOSolapas {

	public DAOSolapasImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
	}
	
	public Solapa obtenerSolapaPorCdgTipoColumna(String cdgTipoColumna) throws SQLException, LRSException
	{
		ResultSet rs = null;
		Solapa solapa = null;
		try {
			rs = lrsdb.getOracleSP("PKG_SERVICIO_SOLAPA.getSolapaPorTipoColumna('"+cdgTipoColumna+"')");
			if(rs.next()){
				solapa = new Solapa();
				solapa.setCdgSolapa(rs.getString("cdg_solapa"));
				solapa.setCdgLabel(rs.getString("cdg_label"));
				solapa.setDescripcion(rs.getString("descripcion"));
				solapa.setIdSolapaHtml(rs.getString("id_solapa_html"));
				solapa.setNroSolapaHtml(rs.getString("nro_solapa_html"));
				solapa.setValidarMercado("S".equals(rs.getString("validar_mercado")));
				solapa.setCantidadElementosMinima(rs.getInt("cantidad_minima_subtotal"));
				solapa.setCdgTipoConsulta(rs.getString("cdg_tipo_columna"));
				solapa.setNombreArregloDatos(rs.getString("nombre_arreglo_datos"));
				solapa.setCdgRol(rs.getString("cdg_rol"));
			}
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return solapa;
	}
	
	public Solapa obtenerSolapa(String cdgTipoSolapa) throws SQLException, LRSException
	{
		
		Solapa solapa = null;
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_SERVICIO_SOLAPA.getSolapaPorCodigo('"+cdgTipoSolapa+"')");
			if(rs.next()){
				solapa = new Solapa();
				solapa.setCdgSolapa(rs.getString("cdg_solapa"));
				solapa.setCdgLabel(rs.getString("cdg_label"));
				solapa.setDescripcion(rs.getString("descripcion"));
				solapa.setIdSolapaHtml(rs.getString("id_solapa_html"));
				solapa.setNroSolapaHtml(rs.getString("nro_solapa_html"));
				solapa.setValidarMercado("S".equals(rs.getString("validar_mercado")));
				solapa.setCantidadElementosMinima(rs.getInt("cantidad_minima_subtotal"));
				solapa.setCdgTipoConsulta(rs.getString("cdg_tipo_columna"));
				solapa.setNombreArregloDatos(rs.getString("nombre_arreglo_datos"));
				solapa.setCdgRol(rs.getString("cdg_rol"));
			}
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return solapa;
	}
	//TODO
	public List<Solapa> obtenerSolapas(TipoConsulta consulta,  Usuario us) throws SQLException, LRSException
	{
		
		List<Solapa> solapas = new LinkedList<Solapa>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_TIPO_CONSULTA.getSolapasTipoConsulta('"+consulta.getCdgTipoConsulta()+"','"+us.getPais()+"')");
			while(rs.next()){
				Solapa solapa = new Solapa();
				solapa.setCdgSolapa(rs.getString("cdg_solapa"));
				solapa.setCdgLabel(rs.getString("cdg_label"));
				solapa.setDescripcion(rs.getString("descripcion"));
				solapa.setIdSolapaHtml(rs.getString("id_solapa_html"));
				solapa.setNroSolapaHtml(rs.getString("nro_solapa_html"));
				solapa.setValidarMercado("S".equals(rs.getString("validar_mercado")));
				solapa.setCantidadElementosMinima(rs.getInt("cantidad_minima_subtotal"));
				solapa.setCdgTipoConsulta(rs.getString("cdg_tipo_columna"));
				solapa.setNombreArregloDatos(rs.getString("nombre_arreglo_datos"));
				solapa.setCdgRol(rs.getString("cdg_rol"));
				solapas.add(solapa);
			}
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return solapas;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Solapa get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Solapa> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	public Solapa save(Solapa object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double esMercadoActual(TipoConsulta consulta) throws LRSException
	{
		return lrsdb.getOracleDouble("PKG_ESQUEMAS.validar_servicio_actual('"+consulta.getCdgTipoConsulta()+"')");
	}
}

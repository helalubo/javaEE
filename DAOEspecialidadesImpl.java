package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pcup.domain.dao.DAOEspecialidades;
import pcup.domain.model.EspecialidadPropia;
import plataforma.domain.model.Cabecera;
import plataforma.domain.model.EspecialidadPropiaCab;
import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Pais;
import plataforma.domain.model.Usuario;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOEspecialidadesImpl implements DAOEspecialidades {


	private LRSDataBase lrsdb = null;
	
	public DAOEspecialidadesImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
	}
	

	public void actualizarPadreEspecialidad(EspecialidadPropia especialidadNueva)
			throws LRSException {
		
		String queryArealizar = "PKG_ESPECIALIDADES.actualizarPadre(";
		queryArealizar += especialidadNueva.getCdgGeografia() + ",";
		queryArealizar += especialidadNueva.getCdgGeoPropia() + ",";
		queryArealizar += especialidadNueva.getCdgGeografiaPadre() + ",null,null)";
		lrsdb.executeOracleSP(queryArealizar);
	}


	public void actualizarPadreEspecialidad(long idNuevo, long idPadre, long geografiaPropia, String id_geografia_referencia, String tipo_referencia) throws LRSException {
		
		String queryArealizar = "PKG_ESPECIALIDADES.actualizarPadre(";
		queryArealizar += idNuevo + ",";
		queryArealizar += geografiaPropia + ",";
		queryArealizar += idPadre + ",";
		queryArealizar += ((id_geografia_referencia != null)? "'"+id_geografia_referencia+"'" : "null") + ",";
		queryArealizar += ((tipo_referencia != null)? "'"+tipo_referencia+"'" : "null") + ")";
		lrsdb.executeOracleSP(queryArealizar);
	}


	public Long agregarElementoAEspecialidad(String idPadre, String idespecialidadpropia, String texto)
			throws LRSException {
		
		long padre = Long.parseLong(idPadre.substring(3));
		String queryArealizar = "PKG_ESPECIALIDADES.agregarElementoAEspecialidad(";
		queryArealizar += padre + ",'";
		queryArealizar += idespecialidadpropia + "','";
		queryArealizar += texto + "')";
		return lrsdb.getOracleNumber(queryArealizar);
	}


	public void agregarEspecialidad(EspecialidadPropia geografia)
			throws LRSException {
		
		String queryArealizar = "PKG_ESPECIALIDADES.agregarEspecialidad(";
		queryArealizar += geografia.getCdgGeografia() + ",";
		queryArealizar += geografia.getCdgGeografiaPadre() + ",";
		queryArealizar += geografia.getCdgGeoPropia() + ",";
		queryArealizar += geografia.getImportancia() + ",";
		queryArealizar += geografia.getNivelGeo() + ",'";
		queryArealizar += geografia.getCdgJerarquia() + "','";
		queryArealizar += geografia.getDescGeografia() + "','";
		queryArealizar += (geografia.getEsReal()? 'S' : 'N') + "',";
		queryArealizar += geografia.getOrden() + ",'";
		queryArealizar += geografia.getCdgPais() + "','";
		queryArealizar += geografia.getAbreviaturaEspecialidad() + "')";
		
		long id_nuevo = lrsdb.getOracleNumber(queryArealizar);
		geografia.setCdgGeografia(id_nuevo);
		
	}


	public long crearEspecialidadPropia(String especialidadBase, String nombre, String sistema,
			Usuario usuario, boolean visibilidad) throws LRSException {
		
		return lrsdb.getOracleNumber("PKG_ESPECIALIDADES_CAB.insertarEspecialidadPropia('"+especialidadBase+"','"+nombre+"','"+sistema+"','"+usuario.getLaboratorio()+"','"+usuario.getPais()+"',"+usuario.getId()+"," + (visibilidad ? "'P'" : "'V'") + ")");
	}


	public List<Cabecera> getEspecialidades(Usuario user) throws LRSException,
			SQLException {
		
		List<Cabecera> out = new LinkedList<Cabecera>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_ESPECIALIDADES_CAB.getEspecialidadesCab('"+user.getPais()+"','"+ user.getLaboratorio() +"',"+user.getId()+")");
			int stmt = lrsdb.getLastStatementId();
			
			while(rs.next()){
				
				EspecialidadPropiaCab cabecera = new EspecialidadPropiaCab(rs.getString("CDG_ESP_PROPIA"),rs.getString("DESC_ESP_PROPIA"));
	
				cabecera.setCdgSistema(rs.getString("cdg_sistema"));
				cabecera.setUsuario(rs.getLong("cdg_usuario"));
				cabecera.setVisibilidad(rs.getString("visibilidad"));
				out.add(cabecera);
			}
				
			rs.close();
			lrsdb.closeStatement(stmt);
		} catch (SQLException ex) {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			throw ex;
		}	
		
		return out;
	}
	
	public List<Cabecera> getEspecialidades(Usuario user, Pais pais, Laboratorio lab) throws LRSException,
		SQLException {
	
	List<Cabecera> out = new LinkedList<Cabecera>();
	ResultSet rs = null;
	try {
		rs = lrsdb.getOracleSP("PKG_ESPECIALIDADES_CAB.getEspecialidadesCab('"+pais.getPais()+"','"+ lab.getCdgLaboratorio() +"',"+user.getId()+")");
		int stmt = lrsdb.getLastStatementId();
		
		while(rs.next()){
			
			EspecialidadPropiaCab cabecera = new EspecialidadPropiaCab(rs.getString("CDG_ESP_PROPIA"),rs.getString("DESC_ESP_PROPIA"));
	
			cabecera.setCdgSistema(rs.getString("cdg_sistema"));
			cabecera.setUsuario(rs.getLong("cdg_usuario"));
			cabecera.setVisibilidad(rs.getString("visibilidad"));
			out.add(cabecera);
		}
			
		rs.close();
		lrsdb.closeStatement(stmt);
	} catch (SQLException ex) {
		if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		throw ex;
	}	
	
	return out;
}


	public List<EspecialidadPropia> obtenerEspecialidadesPropias(
			long especialidadBase,String sistema) throws LRSException, SQLException {
		
		List<EspecialidadPropia> lista = new ArrayList<EspecialidadPropia>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_ESPECIALIDADES.obtenerTodasEspecialidades('"+especialidadBase+"','"+sistema+"')");
			while(rs.next())
			{
				EspecialidadPropia laGeografia = new EspecialidadPropia();
				laGeografia.setCdgGeografia(rs.getLong("cdg_especialidad"));
				laGeografia.setCdgGeografiaPadre(rs.getLong("cdg_especialidad_padre"));
				laGeografia.setCdgGeoPropia(rs.getLong("cdg_esp_propia"));
				laGeografia.setDescGeografia(rs.getString("desc_especialidad"));
				laGeografia.setCdgPais(rs.getString("cdg_pais"));
				laGeografia.setImportancia(rs.getLong("importancia"));
				laGeografia.setNivelGeo(rs.getLong("nivel_esp"));
				laGeografia.setCdgJerarquia(rs.getString("cdg_jerarquia"));
				laGeografia.setAbreviaturaEspecialidad(rs.getString("abrev_especialidad"));
				if (rs.getString("es_real") != null)
				{
					laGeografia.setEsReal(rs.getString("es_real").equals("S"));
				}
				try { laGeografia.setOrden(rs.getLong("orden")); } catch (Exception ex) {/* No logueo nada, porque no necesito*/ }
				
				lista.add(laGeografia);
			}
			rs.close();
		} catch (SQLException ex) {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			throw ex;
		}	
		return lista;
	}


	public void removerNodo(String idNodo, String idGeografiaPropia)
			throws LRSException {
		
		long nodo =  Long.parseLong(idNodo.substring(3));
		String queryArealizar = "PKG_ESPECIALIDADES.removerElementoAEspecialidad(";
		queryArealizar += nodo +",";
		queryArealizar += idGeografiaPropia +")";
		lrsdb.executeOracleSP(queryArealizar);
	}

	public void renombrarNodo(String idNodo, String idGeografiaPropia,
			String texto) throws LRSException {
		
		long nodo =  Long.parseLong(idNodo.substring(3));
		String queryArealizar = "PKG_ESPECIALIDADES.renombrarElementoAEspecialidad(";
		queryArealizar += nodo + ",";
		queryArealizar += idGeografiaPropia + ",'";
		queryArealizar += texto + "')";
		lrsdb.executeOracleSP(queryArealizar);
	}
	
	public List<Cabecera> getEspecialidades(Usuario auth, String sistema) throws LRSException, SQLException {
		List<Cabecera> out = new LinkedList<Cabecera>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_ESPECIALIDADES_CAB.getEspecialidadesCabPorSistema('"+auth.getPais()+"','"+ auth.getLaboratorio() +"',"+auth.getId()+",'"+sistema+"')");
			int stmt = lrsdb.getLastStatementId();
			while(rs.next()){
				EspecialidadPropiaCab esp = new EspecialidadPropiaCab(rs.getString("CDG_ESP_PROPIA"), rs.getString("DESC_ESP_PROPIA"));
				out.add(esp);
			}
				
			rs.close();
			lrsdb.closeStatement(stmt);
		} catch (SQLException ex) {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			throw ex;
		}	
		return out;
	}


	public void eliminarGeografia(String id_geografia_propia) throws LRSException {
		String queryArealizar = "PKG_ESPECIALIDADES.eliminarEspecialidad(" + id_geografia_propia + ")";
		lrsdb.executeOracleSP(queryArealizar);
	}
	
	public void actualizarOrdenes(long idNuevo) throws LRSException {
		
		lrsdb.executeOracleSP("PKG_ESPECIALIDADES.ordenar_especialidad_propia("+idNuevo+",'TRUE')");
	}

	public void updateCabecera(Usuario auth, String id_geografia_propia,
			String id_nombre, boolean nuevaVisibilidad) throws LRSException {
		lrsdb.executeOracleSP("PKG_ESPECIALIDADES_CAB.updateCabecera("+id_geografia_propia+",'" + id_nombre + "'," + (nuevaVisibilidad ? "1" : "0") + ")");
	}
	
	public void copiarAOtroLaboratorio(Laboratorio labOrigen, Laboratorio labDestino,
			String items) throws LRSException, SQLException
	{

		lrsdb.executeOracleSP("PKG_DEF_PROPIAS.copiar_especialidades(" + labOrigen + "," + labDestino + ",'" + items + "')");

	}

	public String getNombreEspecialidad(String esp) throws LRSException{
		return lrsdb.getOracleVarchar2("pkg_especialidades.obtenerDescEsp('"+esp+"')");
	}

}

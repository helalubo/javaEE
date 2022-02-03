package pcup.domain.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOReporteGerencial;
import pcup.domain.model.ReporteGerencial;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.Linea;
import plataforma.domain.model.Mercado;
import plataforma.domain.model.Reporte;
import plataforma.domain.model.TipoApertura;

/**
 * Clase implementadora de los métodos JDBC de Reporte.
 *
 * @author Juan Alberto Villca <jvillca@learsoft.com.ar>
 * @param <T> El tipo (Perfil)
 * @param <ID> La clave primaria para el tipo (String)
 */
public class DAOReporteGerencialImpl extends DAOGenericoImpl<Reporte, String> implements DAOReporteGerencial{

	public DAOReporteGerencialImpl(LRSDataBase lrsdb){
		super(lrsdb);
	}
	
	public boolean exists(String id) {
		return false;
	}

	public Reporte get(String id) {
		return null;
	}

	public List<Reporte> getAll() {
		return null;
	}

	public void remove(String id) {
	}

	public Reporte save(Reporte object) {
		return null;
	}
	
	public void inicializarReporte(Reporte reporte, TipoApertura tipoApertura, Mercado mercado, Linea linea, boolean recarga) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.main("+reporte.getCodigoToString()+",'"+(recarga? 'S':'N')+"')");
		/*lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.chequear_inicializar("+
				(mercado != null ? mercado.getCodigoToString() : "NULL")+"," +
				(linea != null ? linea.getCodigoToString() : "NULL")+"," +
				(tipoApertura == null ? "NULL" : "'"+tipoApertura.getCodApertura()+"'") +
				")");*/
		//DNAVAS INC#2199
		//EN REALIDAD LA APERTURA INICIAL SIEMPRE DEBERIA SER MR:MERCADOS
		//SI ES REPORTE ESTANDAR LA APERTURA ES MR:MERCADOS		
		if(reporte.getCdgReporteStd()>0){
			lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.chequear_inicializar("+
				(mercado != null ? mercado.getCodigoToString() : "NULL")+"," +
				(linea != null ? linea.getCodigoToString() : "NULL")+"," +
				"'MR'" +
				")");
		}else{
			lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.chequear_inicializar("+
				(mercado != null ? mercado.getCodigoToString() : "NULL")+"," +
				(linea != null ? linea.getCodigoToString() : "NULL")+"," +
				(tipoApertura == null ? "NULL" : "'"+tipoApertura.getCodApertura()+"'") +
				")");
		}		
		
		
		/*lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.inicializar_reporte("+
				(mercado != null ? mercado.getCodigoToString() : "NULL")+"," +
				(linea != null ? linea.getCodigoToString() : "NULL")+"," +
				(tipoApertura == null ? "NULL" : "'"+tipoApertura.getCodApertura()+"'") +
				")");*/
	}
	
	public void cachearReporte(String cdgReporte) throws LRSException {
		this.lrsdb.executeOracleSP("PKG_SERVICIO_REPORTE.guardarCacheDatos("+cdgReporte+",0)");
	}
	
	public void reInicializarReporte(Reporte reporte, TipoApertura tipoApertura, Mercado mercado, Linea linea) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.inicializar_reporte("+
				(mercado != null ? mercado.getCodigoToString() : "NULL")+"," +
				(linea != null ? linea.getCodigoToString() : "NULL")+"," +
				(tipoApertura == null ? "NULL" : "'"+tipoApertura.getCodApertura()+"'") +
				")");
	}
	
	public void apertura(long linea, TipoApertura tipoApertura) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.apertura("+
				Long.toString(linea)+"," +
				(tipoApertura == null ? "NULL" : "'"+tipoApertura.getCodApertura()+"'") +
				")");
	}
	
	public void cerrar(long linea) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.cerrar("+
				Long.toString(linea)+
				")");
	}
	
	public void ultimaPagina(long linea) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.ultima_pagina("+
				Long.toString(linea)+
				")");
	}
	
	public void primeraPagina(long linea) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.primera_pagina("+
				Long.toString(linea)+
				")");
	}
	
	public void paginaSiguiente(long linea) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.pagina_siguiente("+
				Long.toString(linea)+
				")");
	}

	public void abrirTodos(long linea, TipoApertura tipoApertura) throws LRSException{
		lrsdb.executeOracleSP("PKG_DATOS_GERENCIALES.apertura("+
				Long.toString(linea)+"," +
				(tipoApertura == null ? "NULL" : "'"+tipoApertura.getCodApertura()+"'") +
				")");
	}
	
	
	public void expandir(long linea) throws LRSException{
	}
	
	public void comprir(long linea) throws LRSException{
	}
	
	public List<ReporteGerencial> obtenerDatos() throws LRSException, SQLException{
		List<ReporteGerencial> out = new LinkedList<ReporteGerencial>();
		
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_DATOS_GERENCIALES.obtener_query");
			
			while(rs.next())
				out.add(obtenerDatoGerencial(rs));
			
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return out;
	}
	
	public List<ReporteGerencial> obtenerDatosMxc() throws LRSException, SQLException{
		List<ReporteGerencial> out = new LinkedList<ReporteGerencial>();
		
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_DATOS_GERENCIALES.obtener_query_mxc");
			
			while(rs.next())
				out.add(obtenerDatoGerencialmxc(rs));
			
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return out;
	}
	
	private ReporteGerencial obtenerDatoGerencial(ResultSet rs) throws LRSException, SQLException{
		ReporteGerencial out = new ReporteGerencial();
		
		out.setLinea(rs.getLong("linea"));
		out.setNivel(rs.getInt("nivel"));
		out.setOrden(rs.getInt("orden"));
		out.setDescripcion(rs.getString("descripcion"));
		out.setMedicos(rs.getString("medicos"));
		out.setMedicosVisitados(rs.getString("visitados"));
		out.setMedicosNoVisitados(rs.getString("novisitados"));
		out.setIndiceCobertura(rs.getString("cobertura"));
		out.setTipo(rs.getString("tipo"));
		out.setPrescripciones(rs.getString("PX"));
		out.setPxMedicosVisitados(rs.getString("PxVisitados"));
		out.setPxMedicosNoVisitados(rs.getString("PxNoVisitados"));
		
		out.setAbreCategorias("S".equals(rs.getString("abre_categoria")));
		out.setAbreGeografia("S".equals(rs.getString("abre_geo")));
		out.setAbreEspecialidad("S".equals(rs.getString("abre_esp")));
		out.setAbreProductos("S".equals(rs.getString("abre_producto")));
		//RFC Cob Mol
		out.setAbreMolecula("S".equals(rs.getString("abre_molecula")));
		out.setAbierto(rs.getInt("cant_hijos") > 0);
		out.setSigue("S".equals(rs.getString("sigue")));
		out.setContraer("-".equals(out.getTipo()));
		out.setCdgMercado(rs.getString("clv_mercado"));
		out.setCdgEspecialidad(rs.getString("clv_esp"));
		out.setCdgGeografia(rs.getString("clv_geo"));
		out.setCdgCategoria(rs.getString("clv_categoria"));
		out.setCdgProducto(rs.getString("clv_producto"));
		//RFC Cob Mol
		out.setCdgMolecula(rs.getString("clv_molecula"));
		out.setCdgLinea(rs.getString("clv_linea"));
		
		return out;
	}
	

	private ReporteGerencial obtenerDatoGerencialmxc(ResultSet rs) throws LRSException, SQLException{
		ReporteGerencial out = new ReporteGerencial();
		
		out.setLinea(rs.getLong("linea"));
		out.setNivel(rs.getInt("nivel"));
		out.setOrden(rs.getInt("orden"));
		out.setDescripcion(rs.getString("descripcion"));
		out.setMedicos(rs.getString("medicos"));
		out.setMedicosVisitados(rs.getString("visitados"));
		out.setPxMedicosVisitados(rs.getString("Pxvisitados"));
		out.setMediapx(rs.getString("mediapx"));
		out.setMedicosNoVisitados(rs.getString("novisitados"));
		out.setIndiceCobertura(rs.getString("cobertura"));
		out.setTipo(rs.getString("tipo"));
		
		out.setAbreCategorias("S".equals(rs.getString("abre_categoria")));
		out.setAbreGeografia("S".equals(rs.getString("abre_geo")));
		out.setAbreEspecialidad("S".equals(rs.getString("abre_esp")));
		out.setAbreProductos("S".equals(rs.getString("abre_producto")));
		//RFC Cob Mol
		out.setAbreMolecula("S".equals(rs.getString("abre_molecula")));
		out.setAbreMercados("S".equals(rs.getString("abre_mercado")));
		out.setAbierto(rs.getInt("cant_hijos") > 0);
		out.setSigue("S".equals(rs.getString("sigue")));
		out.setContraer("-".equals(out.getTipo()));
		out.setCdgMercado(rs.getString("clv_mercado"));
		out.setCdgEspecialidad(rs.getString("clv_esp"));
		out.setCdgGeografia(rs.getString("clv_geo"));
		out.setCdgCategoria(rs.getString("clv_categoria"));
		out.setCdgProducto(rs.getString("clv_producto"));
		//RFC Cob Mol
		out.setCdgMolecula(rs.getString("clv_molecula"));
		out.setCdgLinea(rs.getString("clv_linea"));
		
		return out;
	}

	
	
	public Linea obtLineaMedicoActiva(long cdgReporte) throws LRSException{
		String lineaActiva = lrsdb.getOracleVarchar2("PKG_DATOS_GERENCIALES.obt_linea_medico_activa(" + cdgReporte + ")");
	
		if (lineaActiva != null && !"".equals(lineaActiva))
			return new Linea(Integer.parseInt(lineaActiva));
		else
			return null;
	
	}
	
	public String tieneValoresAbsolutos() throws LRSException{
		String valoresAbsolutos = lrsdb.getOracleVarchar2("PKG_DATOS_GERENCIALES.tiene_valores_absolutos");
	
		return valoresAbsolutos;
	}
	
	public List<Linea> obtLineas() throws SQLException, LRSException{
		List<Linea> out = new LinkedList<Linea>();
		
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_DATOS_GERENCIALES.obtenerLineasMedicos()");
			
			while(rs.next()){
				Linea linea = new Linea(rs.getInt("cdg_linea"));
				linea.setDescripcion(rs.getString("desc_linea"));
				out.add(linea);
			}
			
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return out;
	}
	
	public void guardarAperturas(Reporte rep) throws LRSException 
	{
		this.lrsdb.executeOracleSP("PKG_GESTION_APERTURAS.guardar_aperturas("+rep.getCodigoToString()+")");
	}
	
	public void realizarAperturas(Reporte reporte) throws LRSException {
		this.lrsdb.executeOracleSP("PKG_GESTION_APERTURAS.aperturas_reportes("+reporte.getCodigo()+")");
	}

}
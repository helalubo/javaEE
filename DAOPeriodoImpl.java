package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOPeriodo;
import plataforma.domain.model.OperPx;
import plataforma.domain.model.Periodo;
import plataforma.domain.model.TipoPeriodo;
import plataforma.domain.model.Usuario;
import plataforma.util.PlataformaLogger;

public class DAOPeriodoImpl implements DAOPeriodo {

	private LRSDataBase lrsdb;
	protected final PlataformaLogger logger = PlataformaLogger.obtenerLoggerDefault();
	
	public DAOPeriodoImpl(LRSDataBase lrsdb) {
		
		this.lrsdb = lrsdb;
	}

	public Periodo obtenerPeriodo(long cdgPeriodo, String tipoconsulta) {		
		Periodo periodo = new Periodo(cdgPeriodo);
		ResultSet rs = null;
		
		try {					
			if (tipoconsulta != null)
			{
				rs = lrsdb.getOracleSP("PKG_SERVICIO_PERIODO.obtenerPeriodoPorCodigo("+cdgPeriodo+",'"+tipoconsulta+"')");
			}
			else
			{
				rs = lrsdb.getOracleSP("PKG_SERVICIO_PERIODO.obtenerPeriodoPorCodigo("+cdgPeriodo+",null)");
			}
			if (rs.next())
			{
				periodo.setOrden(rs.getInt("orden"));
			}
			//rs.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return periodo;
	}

	public Periodo obtenerPeriodoPropio(long cdgPeriodo) {
		Periodo periodoPropio = new Periodo();
		ResultSet rs = null;
		try
		{
			rs = lrsdb.getOracleSP("PKG_SERVICIO_PERIODO.obtenerPeriodoPropio(" + cdgPeriodo + ")");
			while (rs.next())
			{
				periodoPropio = new Periodo(-1, rs.getInt("orden"), rs.getLong("cdg_periodo"), rs.getString("descripcion"), null, rs.getString("cdg_pais"), "S".equals(rs.getString("movil")) );
				periodoPropio.setTipoPeriodo( new TipoPeriodo( Long.parseLong( rs.getString("CDG_TIPOPER") ) ) );
				periodoPropio.setCdgLab(rs.getString("CDG_LABORATORIO"));
				periodoPropio.setSistema(rs.getString("sistema"));
				periodoPropio.setTipoVisibilidad(rs.getString("tipo_visibilidad"));
				periodoPropio.setCdgUsuario(rs.getLong("cdg_usuario"));
				periodoPropio.setMesesInvalidos(rs.getLong("dif_meses"));
				periodoPropio.setEdicionCreacion(rs.getString("edicion_creacion"));
				establecerPx( periodoPropio, rs );
			}
			//rs.close();
		} catch (SQLException ex) {
			logger.logException(ex);
			logger.error("SQLException: " + ex.getMessage());
		}	
		catch(Exception ex)
		{
			logger.logException(ex);
			logger.error("SQLException: " + ex.getMessage());
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}		
		
		return periodoPropio;
	}
	
	public List<Periodo> obtenerPeriodosPropios(Usuario auth) {
		ArrayList<Periodo> periodosPropios = new ArrayList<Periodo>();
		ResultSet rs = null;
		try
		{
			rs = lrsdb.getOracleSP("PKG_SERVICIO_PERIODO.obtenerPeriodosPropios(p_cdg_usuario=>" + auth.getId() + ")");
			while (rs.next())
			{
				Periodo p = new Periodo( rs.getLong("cdg_periodo") );
				p.setOrden(rs.getInt("orden"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setSistema(rs.getString("sistema"));
				p.setTipoVisibilidad(rs.getString("tipo_visibilidad"));
				p.setCdgUsuario(rs.getLong("cdg_usuario"));
				periodosPropios.add( p );
			}
			//rs.close();
		} catch (SQLException ex) {
			logger.logException(ex);
			logger.error("SQLException: " + ex.getMessage());
		} catch(Exception ex) {
			logger.logException(ex);
			logger.error("SQLException: " + ex.getMessage());
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}	
		
		return periodosPropios;
	}

	private void establecerPx(Periodo periodoPropio, ResultSet rs) {
		int i = 0;
		boolean sigue = true;
		while( sigue ) {
			String id = "" + ++i;
			if (id.length() < 2)
				id = "0" + id;
			try{
				periodoPropio.getOperPx().add( new OperPx( id, "1".equals( rs.getString("oper_px" + id) ) ) );
			}
			catch(Exception e){ sigue = false; }
		}
	}
	
	public boolean guardarPeriodoPropio( Periodo periodo, Usuario auth ) {
		StringBuffer datos = new StringBuffer();
		datos.append( periodo.getTipoPeriodo().getCodigoToString() + ",");
		datos.append( "'" + periodo.getDescripcion() + "',");
		datos.append( periodo.getOrden() + ",");
		datos.append( "'" + periodo.getCdgPais() + "',");

		
		int totalOperPx = getTotalOperPxPeriodos("" + auth.getId(), periodo.getSistema());

		for( int i = 0; i < periodo.getOperPx().size(); i++ ) {
			datos.append( (i < totalOperPx && periodo.getOperPx().get(i).isValido() ? "1" : "0") + ",");	
		}
		datos.append( periodo.getCdgLab() + ",");
		datos.append( auth.getId() + ",");
		datos.append( periodo.isMovil() ? "'S'," : "NULL," );
		datos.append( "'" + periodo.getSistema() + "',");
		datos.append( "'" + periodo.getTipoVisibilidad() + "',");
		datos.append( "'" + periodo.getEdicionCreacion() + "'");

		CallableStatement cstmt = null;
		boolean rtdo = true;
		
		try{
			String query = "BEGIN :1 := PKG_SERVICIO_PERIODO.guardarPeriodoPropio(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setString(2, datos.toString()); 		
			cstmt.execute();
			
			if((Integer)cstmt.getObject(1)==0)
				return false;
			
			periodo.setCodigo((Integer)cstmt.getObject(1));
			//cstmt.close();	
		}catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			rtdo = false;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return rtdo;
	}
	
	public boolean updatePeriodoPropio( Periodo periodo, Usuario auth ) {

		StringBuffer datos = new StringBuffer();
		datos.append( "CDG_TIPOPER = " + periodo.getTipoPeriodo().getCodigoToString() + ",");
		datos.append( "DESCRIPCION = ''" + periodo.getDescripcion() + "'',");
		datos.append( "ORDEN = " + periodo.getOrden() + ",");
		datos.append( "CDG_PAIS = ''" + periodo.getCdgPais() + "'',");
		datos.append( "SISTEMA = ''" + periodo.getSistema() + "'',");

		int totalOperPx = getTotalOperPxPeriodos("" + auth.getId(), periodo.getSistema());

		for( int i = 0; i < periodo.getOperPx().size(); i++ ) {
			OperPx operPx = periodo.getOperPx().get(i);	
			datos.append( "OPER_PX" + operPx.getId() + " = " + (i < totalOperPx && operPx.isValido() ? "1" : "0") + ",");	
		}
		
		datos.append( "CDG_LABORATORIO = " + periodo.getCdgLab() + ",");
		datos.append( "CDG_USUARIO = " + auth.getId() + ",");
		datos.append( "MOVIL = " + (periodo.isMovil() ? "''S''" : "null") + "," );
		datos.append( "TIPO_VISIBILIDAD = ''" + periodo.getTipoVisibilidad() + "''" );
		
		if (!periodo.isMovil()) {
			datos.append( ",EDICION_CREACION = nvl(EDICION_CREACION,pkg_esquemas_os.get_sesion_edicion_actual)" );
		} else {
			datos.append( ",EDICION_CREACION = NULL" );
		}
		
		CallableStatement cstmt = null;
		boolean rtdo = true;
		
		try{
			String query = "BEGIN :1 := PKG_SERVICIO_PERIODO.updatePeriodoPropio(" + periodo.getCodigoToString() + ",'" + datos.toString() + "'); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.execute();
			
			if((Integer)cstmt.getObject(1)!=1)
				return false;
			
			//cstmt.close();	
		}catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			rtdo = false;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return rtdo;
	}

	public Collection<Periodo> getPeriodos(Usuario auth, String valorBuscado) {
		ArrayList<Periodo> periodosPropios = new ArrayList<Periodo>();
		ResultSet rs = null;
		try
		{
			rs = lrsdb.getOracleSP("PKG_SERVICIO_PERIODO.obtenerPeriodosPropios(p_cdg_usuario=>" + auth.getId() + ",p_valor_buscado=>'" + valorBuscado + "',p_inactivos=>'true')");
			while (rs.next())
			{
				Periodo p = new Periodo( rs.getLong("cdg_periodo") );
				p.setOrden(rs.getInt("orden"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setSistema(rs.getString("sistema"));
				p.setCdgUsuario(rs.getLong("cdg_usuario"));
				periodosPropios.add( p );
			}
			//rs.close();
		}
		catch(Exception ex)
		{
			logger.logException(ex);
			logger.error("SQLException: " + ex.getMessage());
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}	
		
		return periodosPropios;
	}
	
	
	
	

	public Periodo obtenerPeriodoPorDescripcion(Usuario auth, String periodoDesc) throws LRSException, SQLException {
		Periodo p = null;
		ResultSet rs = null;
		
		try {
			rs = lrsdb.getOracleSP("PKG_SERVICIO_PERIODO.obtenerPeriodoPorDesc(p_cdg_usuario=>" + auth.getId() + ",p_descripcion=>'" + periodoDesc + "',p_inactivos=>'true')");
			if(rs.next()) {
				p = new Periodo( rs.getLong("cdg_periodo") );
				p.setOrden(rs.getInt("orden"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setSistema(rs.getString("sistema"));
				p.setCdgUsuario(rs.getLong("cdg_usuario"));
			}
			//rs.close();
		} finally {
			if (rs != null) { rs.close(); } 
		}
		
		return p;
	}
	
	public void remover(String id) {
		try
		{
			lrsdb.executeOracleSP("PKG_SERVICIO_PERIODO.remover('" + id + "')");
		}
		catch(Exception ex)
		{
			logger.logException(ex);
			logger.error("SQLException: " + ex.getMessage());
		}
	}

	public String obtenerDetallePeriodo(long filtroPeriodo) 
	{
		return "";
		/*try
		{
			return lrsdb.executeOracleSP("PKG_SERVICIO_PERIODO.remover('" + id + "')");
		}
		catch(Exception ex)
		{
			logger.logException(ex);
			logger.error("SQLException: " + ex.getMessage());
			return "";
		}*/
	}
	
	
	public int getTotalOperPxPeriodos(String userId, String sistema) {
		int resultado = 0; 
		CallableStatement cstmt = null;
		
		try{
			String query = "BEGIN :1 := PKG_SERVICIO_PERIODO.getTotalOperPxPeriodos(:2, :3); END;";
			if (sistema == null)
				query = "BEGIN :1 := PKG_SERVICIO_PERIODO.getTotalOperPxPeriodos(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setString(2, userId);
			if (sistema != null)
				cstmt.setString(3, sistema);
			
			cstmt.execute();
			
			resultado = (Integer)cstmt.getObject(1);
			
			//cstmt.close();	
		}catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}	
		}
		
		return resultado;
	}

	public boolean updateMesPeriodoPropio(Usuario auth, String periodo, String mes, boolean presente) {
		int resultado = 0;
		CallableStatement cstmt = null;
				
		try{
			String query = "BEGIN :1 := PKG_SERVICIO_PERIODO.updateMesPeriodoPropio(:2, :3, :4, :5); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setLong(2, auth.getId());
			cstmt.setString(3, periodo);
			cstmt.setString(4, mes);
			cstmt.setLong(5, presente ? 1 : 0);
			
			cstmt.execute();
			
			resultado = (Integer)cstmt.getObject(1);
			
			//cstmt.close();	
		}catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			return false;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}	
		}
		
		return resultado != 0;
	}

}

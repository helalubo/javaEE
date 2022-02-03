package pcup.domain.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import pcup.domain.dao.DAOExportacionMercadoCSV;
import pcup.web.exportaciones.ObjetoExportacion;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSConvertionException;
import com.learsoft.exceptions.LRSException;
import com.learsoft.util.LRSConvertionTools;

public class DAOExportacionMercadoCSVImpl implements DAOExportacionMercadoCSV{
	protected LRSDataBase lrsdb = null;
	
	public ResultSet obtenerLineasMercados(String visibilidad) throws SQLException, LRSException {
		return lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.obtener_exportacion('"+visibilidad+"')");
	}
	
	public ResultSet obtenerLineasMercadosBrasil(String visibilidad) throws SQLException, LRSException {
		return lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.obtener_exportacion_brasil('"+visibilidad+"')");
	}	
	
	public DAOExportacionMercadoCSVImpl(LRSDataBase lrsdb){
		this.lrsdb = lrsdb;
	}

	public ResultSet obtenerLineasMercadosProductos(String visibilidad) throws SQLException, LRSException {
		return lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.obtener_exportacion_productos('"+visibilidad+"')");
	}

	public ResultSet obtenerCabecera(String cabecera) throws SQLException, LRSException {
		return lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.cabeceraValidacion('" + cabecera + "')");
	}
	
	public void enviarArchivoExportacion(LinkedList<ObjetoExportacion> archivoExportacion) throws SQLException, LRSConvertionException {
		String insert = "INSERT INTO ESTADISTICAS_EXPORTADOR(CDG_ESTADISTICA, CDG_PAIS, SIGLA_LABORATORIO, USUARIO_LOGIN, TIPO_CONSULTA, MERCADO, PERIODO, ESPECIALIDAD, REGION, FECHA, HORA_INICIO, HORA_FIN) VALUES (SEQ_ESTADISTICAS_EXPORTADOR.NextVal, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";		
		PreparedStatement stmt = this.lrsdb.prepareStatement(insert);
		
		for(ObjetoExportacion obj : archivoExportacion) {
		    stmt.setString(1, obj.getCdgPais());
/*
		    stmt.setString(2, obj.getSiglaLaboratorio());
		    stmt.setString(3, obj.getUsuarioLogin());
		    stmt.setString(4, obj.getTipoConsulta());
		    stmt.setString(5, obj.getMercado());
		    stmt.setString(6, obj.getPeriodo());		    
		    stmt.setString(7, obj.getEspecialidad());
		    stmt.setString(8, obj.getRegion());
*/
		    //se limita el tamaño de los datos a insertar para evitar errores
		    String aux = obj.getSiglaLaboratorio().length() > 20 ? obj.getSiglaLaboratorio().substring(0, 20) : obj.getSiglaLaboratorio();
		    stmt.setString(2, aux);
		    
		    aux = obj.getUsuarioLogin().length() > 100 ? obj.getUsuarioLogin().substring(0, 100) : obj.getUsuarioLogin();
		    stmt.setString(3, aux);
		    
		    aux = obj.getTipoConsulta().length() > 200 ? obj.getTipoConsulta().substring(0, 200) : obj.getTipoConsulta();
		    stmt.setString(4, aux);
		    
		    aux = obj.getMercado().length() > 200 ? obj.getMercado().substring(0, 200) : obj.getMercado();
		    stmt.setString(5, aux);
		    
		    aux = obj.getPeriodo().length() > 100 ? obj.getPeriodo().substring(0, 100) : obj.getPeriodo();
		    stmt.setString(6, aux);	
		    
		    aux = obj.getEspecialidad().length() > 100 ? obj.getEspecialidad().substring(0, 100) : obj.getEspecialidad();
		    stmt.setString(7, aux);
		    
		    aux = obj.getRegion().length() > 50 ? obj.getRegion().substring(0, 50) : obj.getRegion();
		    stmt.setString(8, aux);
		    
		    stmt.setTimestamp(9, new Timestamp((LRSConvertionTools.toDate(obj.getFecha(), "yyyy-MM-dd").getTime())));
		    stmt.setString(10, obj.getHoraInicio());
		    stmt.setString(11, obj.getHoraFin());
		    stmt.addBatch();
		}
		stmt.executeBatch();
	}
	
	public ResultSet obtenerEstadisticas(String parCdgLaboratorio, String parCdgPais, String parFechaDesde, String parFechaHasta) throws SQLException, LRSException {
		return this.lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.obtener_estadisticas(" +
					"'" + parCdgLaboratorio + "', " +
					"'" + parCdgPais + "', " +
					"'" + parFechaDesde + "', " +
					"'" + parFechaHasta + "')");
	}

	public boolean comprobarUsuarioPaisLab(String pais, String laboratorio) {
		try {
			return this.lrsdb.getOracleNumber("PKG_EXPORTACION_MERCADOS.comprobar_usu_pais_lab('"+pais + "','" + laboratorio + "')") == 1;
		} catch (LRSException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet obtenerUsuariosExp() throws SQLException, LRSException {
		return lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.obtener_usuarios_exp");
	}
	
	//INC #1979
	public ResultSet obtenerGrupoSegExp() throws SQLException, LRSException {
		return lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.obtener_grupo_seg_exp");
	}
	
	//INC #1979
	public ResultSet obtenerGrupoSegDetalleExp() throws SQLException, LRSException {
		return lrsdb.getOracleSP("PKG_EXPORTACION_MERCADOS.obtener_grupo_seg_detalle_exp");
	}
}

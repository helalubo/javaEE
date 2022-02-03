package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOReporteBatch;
import pcup.domain.model.ReporteExportacion;
import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Pais;
import plataforma.domain.model.Reporte;
import plataforma.domain.model.TipoConsulta;
import plataforma.domain.model.Usuario;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;
import com.learsoft.web.LRSServletParameter;

public class DAOReporteBatchImpl implements DAOReporteBatch {

	private LRSDataBase lrsdb;
	
	public DAOReporteBatchImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
		//intentar borrar el cache en el constructor de los reportes
	}

	public ReporteExportacion getReportePendiente(int cola) throws LRSException, SQLException{
		ReporteExportacion out = new ReporteExportacion();
		ResultSet rs = null;
		try{
		/*try
		{
			long reporteID = lrsdb.getOracleNumber("PKG_REPORTES_BATCH.buscar_pendiente_exportar("+Integer.toString(cola)+")");

			out.setCodigo(reporteID);
			return out;
		}
		catch (Exception ex)
		{
			out.setCodigo(-1);
			return out;
		}*/
		
			rs = lrsdb.getOracleSP("PKG_REPORTES_BATCH.buscar_rep_pendiente_exportar("+cola+")");
			rs.next();
			out.setCodigo(rs.getLong("cdg_reporte"));
			out.setTipoConsultaBatch(new TipoConsulta(rs.getString("cdg_tipo_consulta")));
			out.setTipoExportacion(rs.getString("tipo_exportacion"));
			return out;
		}
		catch (Exception ex)
		{
			out.setCodigo(-1);
			return out;
		}
		finally
		{
			if(rs != null) rs.close();
		}
	}
	
	public void marcarReporteError(Reporte reporte) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.marcar_reporte_error("+reporte.getCodigo()+")");
	}
	
	public void setearContexto(Reporte reporte) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.setear_contexto("+reporte.getCodigo()+")");
	}
	
	
	public void marcarReporteExportando(Reporte reporte) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.marcar_reporte_exportando("+reporte.getCodigo()+")");
	}
	
	public void marcarReporteFinalizado(Reporte reporte) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.marcar_reporte_finalizado("+reporte.getCodigo()+")");
	}
	
	public void cargarReporteBatch(Reporte reporte, Usuario u) throws LRSException {
		
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.crear_reporte_batch("+reporte.getCodigo()+", "+u.getId()+")");
	}

	public void iniciarEjecucionArchivo(long codigoReporte) throws LRSException {
		
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.empezarGeneracionArchivo("+codigoReporte+")");
	}
	
	public ParametrosBaseHistorico devolverParametros(long codigoReporte) throws LRSException, SQLException
	{
		ParametrosBaseHistorico parametros = new ParametrosBaseHistorico();
		ResultSet rs = null;
		
		try {
			rs = lrsdb.getOracleSP("PKG_REPORTES_BATCH.obtenerParametros("+codigoReporte+")");
			
			while (rs.next()) {
				parametros.add(new LRSServletParameter(rs.getString("key"), rs.getString("value")));
			}
		} finally {
			if (rs != null)
				rs.close();
		}
			
		return parametros;
	}

	public void finalizarEjecucionArchivo(long codigoReporte,
			String nombreArchivo) throws LRSException {
		
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.terminarGeneracionArchivo("+codigoReporte+",'"+nombreArchivo+"')");
	}

	
	public List<Reporte> getReportesBatchGenerados(Usuario u, Laboratorio lab,
			Pais p, String nombreReporte) throws LRSException, SQLException {
		
		List<Reporte> lista=new LinkedList<Reporte>();
		ResultSet rs = null;
		
		try {
			rs= lrsdb.getOracleSP("PKG_REPORTES_BATCH.buscarReportesBatchGenerados("
											+ u.getId()+","
											+ p.getPais()+","
											+ lab.getCdgLaboratorio()
											+ (nombreReporte==null ? "": ("," + nombreReporte))
											+ ")"
											);
			while (rs.next())
			{
				String tipoExportacion = rs.getString("tipo_exportacion");
				if (tipoExportacion != null)
				{
					Reporte rep= new Reporte(rs.getLong("cdg_reporte"));
					
					rep.setEstadoHistorial(rs.getString("estado_hist"));
					rep.setEstado(rs.getString("estado"));
					rep.setDescripcion(rs.getString("value") + "." + rs.getString("tipo_exportacion").toLowerCase());
					rep.setArchivo(rs.getString("nombre_result"));
					lista.add(rep);
				}
			}
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return lista;
	}

	public Reporte getReporteBatchGenerado(String cdg_reporte)
			throws LRSException, SQLException {
		
		Reporte rep=null;
		ResultSet rs = null;
		
		try {
			rs = lrsdb.getOracleSP("PKG_REPORTES_BATCH.obtenerReporteBatchGenerado("+cdg_reporte + ")");
			System.out.println("helalubo -> PKG_REPORTES_BATCH.obtenerReporteBatchGenerado");
			if(rs.next()) {	
				rep= new Reporte(rs.getLong("cdg_reporte"));
				System.out.println("cdg_reporte->" + rs.getLong("cdg_reporte"));
				rep.setEstadoHistorial(rs.getString("estado_hist"));
				System.out.println("estado_hist->" + rs.getString("estado_hist"));
				rep.setDescripcion(rs.getString("value")+ "." + rs.getString("tipo_exportacion").toLowerCase());
				System.out.println("value->" + rs.getString("value") + "tipo_exportacion->" +rs.getString("tipo_exportacion").toLowerCase());
				rep.setArchivo(rs.getString("nombre_result"));
				System.out.println("nombre_result->" + rs.getString("nombre_result"));

			}
			System.out.println("helalubo -> PKG_REPORTES_BATCH.obtenerReporteBatchGenerado");
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return rep;
	}
		

	public String eliminarArchivoReporte(long cdgReporte) throws LRSException,
			SQLException {
		
		String resultado="";
		CallableStatement cstmt = null;
		
		try{
			String query = "BEGIN :1 := PKG_REPORTES_BATCH.eliminarReporteBatchGenerado("+ cdgReporte + "); END;";
			cstmt = this.lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
			cstmt.execute();

			resultado= (String)cstmt.getObject(1);
		} catch(Exception e){
			resultado= e.toString();
		} finally {
			if (cstmt != null)
				cstmt.close();
		}
		
		return resultado;
	}

	public void reiniciarExportacionesIncompletas() throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.reiniciar_exp_incompletas");
	}

	public void guardarVariables(Reporte reporte) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.guardar_variables("+reporte.getCodigo()+")");
	}
	
	public void procesarBatchUsuario(Usuario usuario) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.buscar_pendiente_proc_usuario("+usuario.getId()+")");
	}
	
	public String obtRptCorriendo() throws LRSException {
		String resultado="";
		CallableStatement cstmt = null;
		
		try{
			cstmt = this.lrsdb.getCallableStatement("BEGIN :1 := PKG_REPORTES_BATCH.running_reports; END;");
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.execute();

			resultado= String.valueOf((Integer)cstmt.getObject(1));
		} catch(Exception e){
			resultado= e.toString();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return resultado;
	}
}

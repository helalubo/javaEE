package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import pcup.domain.dao.DAOEstadisticas;
import pcup.domain.model.ReporteEstadisticas;
import pcup.domain.model.ReporteEstadisticasEncabezado;
import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Pais;
import plataforma.domain.model.Usuario;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;


public class DAOEstadisticasImpl implements DAOEstadisticas {
	
	private LRSDataBase lrsdb = null;
	
	public DAOEstadisticasImpl(LRSDataBase lrsdb){
		
		this.lrsdb = lrsdb;
	}

	public List<ReporteEstadisticas> obtenerReportes(ReporteEstadisticas repEst, String opcionBusqueda, Usuario usuario, String mostrarTemporales, String mostrarGuardados, String mostrarBatch, String mostrarExportaciones,String estudiosExportaciones) {
		List<ReporteEstadisticas> out = new LinkedList<ReporteEstadisticas>();
		ResultSet rs = null;
		
		try{
			rs = lrsdb.getOracleSP("PKG_ESTADISTICAS.obtenerReportes('"+repEst.getFechaDesde()+"','"+repEst.getFechaHasta()+"'," +
												"'"+repEst.getRepPais().getPais()+"',"+repEst.getRepLaboratorio().getCdgLaboratorio()+"," +
												"'"+repEst.getRepUsuario().getTipo()+"'"+
												",'"+mostrarTemporales+"'"+
												",'"+mostrarGuardados+"'"+
												",'"+mostrarBatch+"'"+
												//INC #2069
												",'"+mostrarExportaciones+";"+estudiosExportaciones+"'"+
												",'"+opcionBusqueda+"',"+usuario.getId()+")");
			
			while (rs.next()){
				ReporteEstadisticas re = new ReporteEstadisticas();
				
				re.setCdgReporte(rs.getLong("cdg_reporte"));
				re.setRepConsulta(rs.getString("reporte_consulta"));
				re.setRepGuardado(rs.getString("reporte_guardado"));
				re.setRepSTD(rs.getString("es_reporte_standard"));
				re.setUsaGeoSTD(rs.getString("usa_geo_standard"));
				re.setRepPais(new Pais(rs.getString("cdg_pais"), rs.getString("desc_pais")));
				re.setRepLaboratorio(new Laboratorio(rs.getLong("cdg_laboratorio"), rs.getString("desc_laboratorio")));
				re.setRepUsuario(new Usuario(rs.getLong("cdg_usuario"), rs.getString("usuario_nombre")));
				re.setFechaCreacion(rs.getString("fecha_creacion"));
				re.setHoraEjecucion(rs.getString("hora_ejecucion"));
				re.setCantReportes(rs.getLong("cant_reportes"));
				re.setTipoConsultaUrl(rs.getString("tipo_consulta_url"));
				re.setOrigen(rs.getString("origen"));
				re.setCdg_tipo_consulta(rs.getString("cdg_tipo_consulta"));
				
				re.setFechaInicioGeneracionDatos(rs.getString("fecha_inicio_datos"));
				re.setFechaFinGeneracionDatos(rs.getString("fecha_fin_datos"));
				re.setHoraInicioGeneracionDatos(rs.getString("hora_inicio_datos"));
				re.setHoraFinGeneracionDatos(rs.getString("hora_fin_datos"));
				
				re.setFechaInicioGeneracionArchivo(rs.getString("fecha_inicio_archivo"));
				re.setFechaFinGeneracionArchivo(rs.getString("fecha_fin_archivo"));
				re.setHoraInicioGeneracionArchivo(rs.getString("hora_inicio_archivo"));
				re.setHoraFinGeneracionArchivo(rs.getString("hora_fin_archivo"));
				
				re.setTotalTiempoGeneracion(rs.getString("tiempo_generacion"));
				re.setTotalTiempoEspera(rs.getString("tiempo_espera"));
				re.setTipoPerfil(rs.getString("tipo_perfil"));
				re.setPerfilPublico(rs.getString("perfil_publico"));
				re.setCdgFVenta(rs.getString("cdg_fventa"));
				
				out.add(re);
			}
			//rs.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}

	public ReporteEstadisticasEncabezado obtenerEncabezadoReporte(ReporteEstadisticasEncabezado repEstEnc) {
		ReporteEstadisticasEncabezado out = new ReporteEstadisticasEncabezado();
		ResultSet rs = null;
		
		try{
			rs = lrsdb.getOracleSP("PKG_ESTADISTICAS.obtenerEncabezadoReporte('"+repEstEnc.getCdgPais()+"',"+repEstEnc.getCdgLab()+"" +
					",'"+repEstEnc.getCdgTipoUsrFilt()+"','"+repEstEnc.getCdgTipoUsrConsultor()+"','"+repEstEnc.getCdgIdioma()+"')");
			
			if (rs.next()){
				
				out.setDescPais(rs.getString("desc_pais"));
				out.setDescLab(rs.getString("desc_lab"));
				out.setDescTipoUsrFilt(rs.getString("desc_tipo_usuario_filtro"));
				out.setDescTipoUsrConsultor(rs.getString("desc_tipo_usuario_consultor"));
				
			}
			//rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}
	
	/**
	 * Registra en la tabla reportes la fecha de la ultima ejecucion de un reporte
	 * 
	 * @param repEstEnc
	 * @return
	 */
	public void registrarEjecucionReporte(String cdgReporte) {

		try {
			lrsdb.executeOracleSP("PKG_SERVICIO_REPORTE.registrarEjecucionReporte('"+cdgReporte+"')");
		}
		catch (LRSException e) {

			e.printStackTrace();
		}
	}

	@Override
	public List<ReporteEstadisticas> obtenerReportes(ReporteEstadisticas repEst, String opcionBusqueda, Usuario usuario,
			String mostrarTemporales, String mostrarGuardados, String mostrarBatch, String mostrarExportaciones) {

		List<ReporteEstadisticas> out = new LinkedList<ReporteEstadisticas>();
		ResultSet rs = null;
		
		try{
			rs = lrsdb.getOracleSP("PKG_ESTADISTICAS.obtenerReportes('"+repEst.getFechaDesde()+"','"+repEst.getFechaHasta()+"'," +
												"'"+repEst.getRepPais().getPais()+"',"+repEst.getRepLaboratorio().getCdgLaboratorio()+"," +
												"'"+repEst.getRepUsuario().getTipo()+"'"+
												",'"+mostrarTemporales+"'"+
												",'"+mostrarGuardados+"'"+
												",'"+mostrarBatch+"'"+
												",'"+mostrarExportaciones+"'"+
												",'"+opcionBusqueda+"',"+usuario.getId()+")");
			
			while (rs.next()){
				ReporteEstadisticas re = new ReporteEstadisticas();
				
				re.setCdgReporte(rs.getLong("cdg_reporte"));
				re.setRepConsulta(rs.getString("reporte_consulta"));
				re.setRepGuardado(rs.getString("reporte_guardado"));
				re.setRepSTD(rs.getString("es_reporte_standard"));
				re.setUsaGeoSTD(rs.getString("usa_geo_standard"));
				re.setRepPais(new Pais(rs.getString("cdg_pais"), rs.getString("desc_pais")));
				re.setRepLaboratorio(new Laboratorio(rs.getLong("cdg_laboratorio"), rs.getString("desc_laboratorio")));
				re.setRepUsuario(new Usuario(rs.getLong("cdg_usuario"), rs.getString("usuario_nombre")));
				re.setFechaCreacion(rs.getString("fecha_creacion"));
				re.setHoraEjecucion(rs.getString("hora_ejecucion"));
				re.setCantReportes(rs.getLong("cant_reportes"));
				re.setTipoConsultaUrl(rs.getString("tipo_consulta_url"));
				re.setOrigen(rs.getString("origen"));
				re.setCdg_tipo_consulta(rs.getString("cdg_tipo_consulta"));
				
				re.setFechaInicioGeneracionDatos(rs.getString("fecha_inicio_datos"));
				re.setFechaFinGeneracionDatos(rs.getString("fecha_fin_datos"));
				re.setHoraInicioGeneracionDatos(rs.getString("hora_inicio_datos"));
				re.setHoraFinGeneracionDatos(rs.getString("hora_fin_datos"));
				
				re.setFechaInicioGeneracionArchivo(rs.getString("fecha_inicio_archivo"));
				re.setFechaFinGeneracionArchivo(rs.getString("fecha_fin_archivo"));
				re.setHoraInicioGeneracionArchivo(rs.getString("hora_inicio_archivo"));
				re.setHoraFinGeneracionArchivo(rs.getString("hora_fin_archivo"));
				
				re.setTotalTiempoGeneracion(rs.getString("tiempo_generacion"));
				re.setTotalTiempoEspera(rs.getString("tiempo_espera"));
				re.setTipoPerfil(rs.getString("tipo_perfil"));
				re.setPerfilPublico(rs.getString("perfil_publico"));
				re.setCdgFVenta(rs.getString("cdg_fventa"));
				
				out.add(re);
			}
			//rs.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}  finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return out;	
	}
	
	
}
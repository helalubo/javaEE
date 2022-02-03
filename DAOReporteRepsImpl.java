package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;
import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOMetrica;
import pcup.domain.dao.DAOReporteReps;
import pcup.domain.model.ReporteRepPNC;
import pcup.domain.model.ReporteReps;
import pcup.domain.model.ReporteRepsLinea;
import pcup.domain.model.ReporteRepsPRO;
import pcup.domain.model.ReporteRepsResumenCat;
import pcup.domain.model.ReporteRepsSubTitulo;
import pcup.domain.model.ReporteRepsTitulo;
import pcup.domain.model.collections.AnalisisProdNoVisitados;
import pcup.domain.model.collections.MedicosProductividadRepresentantes;
import pcup.domain.service.ServicioFacade;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.ClaseTerapeutica;
import plataforma.domain.model.Especialidad;
import plataforma.domain.model.ExplotacionReporte;
import plataforma.domain.model.FiltroDetalle;
import plataforma.domain.model.FuerzaVenta;
import plataforma.domain.model.Geografia;
import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Linea;
import plataforma.domain.model.Mercado;
import plataforma.domain.model.Metrica;
import plataforma.domain.model.Molecula;
import plataforma.domain.model.Periodo;
import plataforma.domain.model.Presentacion;
import plataforma.domain.model.Producto;
import plataforma.domain.model.Raiz;
import plataforma.domain.model.Reporte;
import plataforma.domain.model.Sistema;
import plataforma.domain.model.Usuario;

/**
 * Clase implementadora de los métodos JDBC de Reporte.
 *
 * @author Juan Alberto Villca <jvillca@learsoft.com.ar>
 * @param <T>
 *            El tipo (Perfil)
 * @param <ID>
 *            La clave primaria para el tipo (String)
 */
public class DAOReporteRepsImpl extends DAOGenericoImpl<Reporte, String> implements DAOReporteReps {

	public DAOReporteRepsImpl(LRSDataBase lrsdb) {
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

	public void cargarSubfiltros(Reporte object) throws LRSException {

		lrsdb.executeOracleSP("PKG_REPS.cargar_subfiltros(" + object.getCodigo() + ")");
	}

	public List<ExplotacionReporte> obtenerExplotacionReporte() throws SQLException, LRSException {
		List<ExplotacionReporte> out = new LinkedList<ExplotacionReporte>();
		ResultSet rs = null;

		try {
			rs = lrsdb.getOracleSP("PKG_REPS.obt_lista_explotacion");

			while (rs.next()) {
				ExplotacionReporte explotacion = new ExplotacionReporte();
				explotacion.setLinea(rs.getLong("linea"));
				explotacion.setDescripcion(rs.getString("desc_fventa"));
				explotacion.setSeleccionado("S".equals(rs.getString("seleccionado")));
				explotacion.setTieneApertura(!"N".equals(rs.getString("apertura")));
				explotacion.setLineaPadre(rs.getLong("linea_padre"));
				out.add(explotacion);
			}
		} finally {
			if (rs != null)
				rs.close();
		}

		return out;
	}

	public List<ExplotacionReporte> obtenerExplotacionReportePNC(String listaAperturas)
			throws SQLException, LRSException {
		List<ExplotacionReporte> out = new LinkedList<ExplotacionReporte>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("pkg_prod_ncat.obt_lista_explotacion('" + listaAperturas + "')");
			while (rs.next()) {
				ExplotacionReporte explotacion = new ExplotacionReporte();
				explotacion.setFventa(rs.getString("cdg_fventa"));
				explotacion.setDescripcion(rs.getString("desc_fventa"));
				out.add(explotacion);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return out;
	}

	/**
	 * Inicialisacion de las tablas temporales
	 */
	public void inicializar(Reporte reporte, Mercado mercado, Raiz raiz, String order, String orderType, String linea,
			Geografia geografia, Especialidad especialidad, long periodo, long c1, long c2, String sub_geo,
			String sub_esp, boolean forzar_recarga, boolean mult_contacto, int recargar, int cargaInicial,
			String filtroVisitados, String grupoSeguimiento, String navegacion) throws SQLException, LRSException {

		this.guardarVariable("NAVEGACION", navegacion);

		if ("PRN".equals(reporte.getTipoConsulta().getCdgTipoConsulta())) {
			if (order == null) {
				order = "PX_MERCADO1";
			}
		}
		lrsdb.executeOracleSP("PKG_REPS.inicializar(" + reporte.getCodigoToString() + ", " + ""
				+ (mercado != null ? mercado.getCodigoToString() : "NULL") + "," + ""
				+ (raiz != null ? raiz.getCodigoToString() : "NULL") + "," + ""
				+ (order != null ? "'" + order + "'" : "NULL") + "," + ""
				+ (orderType != null ? "'" + orderType + "'" : "NULL") + "," + ""
				+ (linea != null && !linea.isEmpty() ? "'" + linea + "'" : "NULL") + "," + ""
				+ (geografia != null ? geografia.getCodigoToString() : "NULL") + "," + ""
				+ (especialidad != null ? especialidad.getCodigoToString() : "NULL") + "," + "" + periodo + "," + c1
				+ "," + c2 + "," + ((sub_geo != null && sub_geo.length() > 0) ? "'" + sub_geo + "'" : "null") + ","
				+ ((sub_esp != null && sub_esp.length() > 0) ? "'" + sub_esp + "'" : "null") + ","
				+ (forzar_recarga ? "1" : "0") + "," + (mult_contacto ? "1" : "0") + "," + recargar + "," + cargaInicial
				+ (filtroVisitados != null ? ",'" + filtroVisitados + "'" : "")
				+ (grupoSeguimiento != null ? ",'" + grupoSeguimiento + "'" : "") + ")");
	}

	/**
	 * Apertura de una linea
	 */
	public void ordenar(Reporte reporte, String order, String orderType) throws SQLException, LRSException {
		lrsdb.executeOracleSP("PKG_REPS.ordenar('" + order + "','" + orderType + "')");
	}

	/**
	 * Apertura de una linea
	 */
	public void apertura(Reporte reporte, long linea, String aperGeo) throws SQLException, LRSException {
		if (linea == 1)
			lrsdb.executeOracleSP("PKG_REPS.abrirTodos(" + Long.toString(linea) + ")");
		else
			lrsdb.executeOracleSP("PKG_REPS.apertura(" + Long.toString(linea) + ")");
	}

	public void guardarDobleContacto(Reporte reporte, String dobleContacto) throws SQLException, LRSException {

		lrsdb.executeOracleSP("PKG_REPS.guardarDobleContacto(" + reporte.getCodigo() + ",'" + dobleContacto + "')");
	}

	/**
	 * Cierre de una linea
	 */
	public void cierre(Reporte reporte, long linea, String aperGeo) throws SQLException, LRSException {
		if (linea == 1)
			lrsdb.executeOracleSP("PKG_REPS.cerrarTodos(" + Long.toString(linea) + ")");
		else
			lrsdb.executeOracleSP("PKG_REPS.cierre(" + Long.toString(linea) + ")");
	}

	/**
	 * Inicialización de las tablas temporales y obtención de resultados.
	 */
	public ResultSet obtenerDatos(Reporte reporte) throws SQLException, LRSException {
		if ("PRJ".equals(reporte.getTipoConsulta().getCdgTipoConsulta())) {
			return lrsdb.getOracleSP("PKG_PRODUCTIVIDAD.obtenerObjetivo");
		} else {
			return lrsdb.getOracleSP("PKG_REPS2.obtenerDatos");
		}
	}
	
	/**
	 * Obtiene datos REPS solo con el codigo de reporte. 
	 * @author jangulo
	 */
	private ResultSet obtenerDatosExportacion(Reporte reporte) {
		if ("PRJ".equals(reporte.getTipoConsulta().getCdgTipoConsulta())) {
			try {
				return lrsdb.getOracleSP("PKG_GENERACION_DATOS_REPS.f_obtenerDatosRepsPRJ("+reporte.getCodigoToString() +")");
			} catch (LRSException exception) {
				logger.error(exception.getMessage());
				return null;
			}
		} else {
			try {
				return lrsdb.getOracleSP("PKG_GENERACION_DATOS_REPS.f_obtenerDatosReps("+reporte.getCodigoToString() +")");
			} catch (LRSException exception) {
				logger.error(exception.getMessage());
				return null;
			}
		}
	}

	/**
	 * Inicialización y organización de la estrutura de respuesta.
	 */
	public ReporteReps obtenerDatosReps(Reporte reporte, Mercado mercado, Raiz raiz, Usuario us, boolean directo) throws SQLException, LRSException {
		ReporteReps datos = new ReporteReps();
		datos.setReporte(reporte);
		datos.setPeriodos(reporte.getPeriodos());
		datos.setRaiz(raiz);
		ResultSet rs = null;
		boolean primeraEjecutcion = false;
		try {
			if(directo) {
				rs = obtenerDatosExportacion(reporte); 
			}else {
				rs = this.obtenerDatos(reporte);
			}
			
			while (rs.next()) {
				if (!primeraEjecutcion) {
					this.obtenerEncabezados(rs, datos, mercado, raiz, reporte, us);
					primeraEjecutcion = true;
				}
				this.obtenerLinea(rs, datos, reporte);
			}
		} finally {
			if (rs != null)
				rs.close();
		}
		if (!primeraEjecutcion) {
			datos = null;
		} else {
			datos.preparar();
		}
		return datos;
	}

	public ReporteReps obtenerDatosRepsPNC(Reporte reporte, Mercado mercado, String esp, String geo,
			String representante, String linea, String grupoSeguimiento, long catDesde, long catHasta,
			String columnaOrden, String tipoOrden, String ftvPadre, int levelPadre, boolean save)
			throws SQLException, LRSException {
		ReporteReps datos = new ReporteReps();
		datos.setReporte(reporte);
		datos.setPeriodos(reporte.getPeriodos());
		ResultSet rs = null;
		CallableStatement cstmt = null;
		boolean primeraEjecutcion = false;
		String query = "";
		try {
			if (("-1").equals(ftvPadre) || save) {// Es Apertura Total
				query = "BEGIN :1 :=  pkg_prod_ncat.obtener_atotal(:2, :3, :4, :5, :6, :7, :8, :9, :10, :11); END;";
			} else if (ftvPadre != null) {// Es una Apertura
				query = "BEGIN :1 :=  pkg_prod_ncat.obtener_fvta(:2, :3, :4, :5, :6, :7, :8, :9, :10, :11); END;";
			} else {
				query = "BEGIN :1 :=  pkg_prod_ncat.obtener_query(:2, :3, :4, :5, :6, :7, :8, :9, :10); END;";
			}
			logger.ejecuto(query + " val: " + (("").equals(esp) ? null : esp) + " " + (("").equals(geo) ? null : geo)
					+ " " + (("").equals(representante) || ("-1").equals(representante) ? null : representante) + " "
					+ (("").equals(linea) ? null : linea) + " "
					+ (((grupoSeguimiento != null) && (!("").equals(grupoSeguimiento)))
							? Integer.parseInt(grupoSeguimiento)
							: 0)
					+ " " + catDesde + " " + catHasta + " " + (("").equals(columnaOrden) ? null : columnaOrden) + " "
					+ (("").equals(tipoOrden) ? null : tipoOrden) + " " + ftvPadre + " " + levelPadre);
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, (("").equals(esp) ? null : esp));
			cstmt.setString(3, (("").equals(geo) ? null : geo));
			cstmt.setString(4, (("").equals(representante) || ("-1").equals(representante) ? null : representante));
			cstmt.setString(5, (("").equals(linea) ? null : linea));
			cstmt.setInt(6,
					(((grupoSeguimiento != null) && (!("").equals(grupoSeguimiento)))
							? Integer.parseInt(grupoSeguimiento)
							: 0));
			cstmt.setLong(7, catDesde);
			cstmt.setLong(8, catHasta);
			cstmt.setString(9, (("").equals(columnaOrden) ? null : columnaOrden));
			cstmt.setString(10, (("").equals(tipoOrden) ? null : tipoOrden));
			if (ftvPadre != null) {
				cstmt.setString(11, ("-1".equals(ftvPadre) ? null : ftvPadre));
			}
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			while (rs.next()) {
				if (!primeraEjecutcion) {
					this.obtenerEncabezados(rs, datos, mercado, null, reporte, null);
					datos.setOrder(columnaOrden);
					datos.setOrderType(tipoOrden);
					primeraEjecutcion = true;
				}
				int level = 0;
				String apertura = "";
				if (("-1").equals(ftvPadre) || save) {
					level = rs.getInt("nivel");
					apertura = ((rs.getString("cdg_fventa") != null && level < 4) ? rs.getString("apertura") : "N");
				} else if (ftvPadre != null) {
					level = levelPadre + 1;
					apertura = (level > 3 ? "N" : "S");
				} else {

					level = ("-1".equals(rs.getString("cdg_fventa")) || (rs.getString("cdg_fventa") == null) ? 1 : 2);
					apertura = (rs.getString("cdg_fventa") != null ? "S" : "N");
				}

				this.obtenerLineaPNC(rs, datos, reporte, level, apertura);
			}

			if (!primeraEjecutcion) {
				datos = null;
			} else {
				datos.preparar();
			}

		} catch (SQLException e) {
			logger.logueaException(e);
			logger.error("LRSException: " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
		}

		return datos;
	}

	public  List<ReporteRepPNC> obtenerDatosRepsPNC(Reporte reporte){
		ReporteRepPNC pnc = null;
		List<ReporteRepPNC> reportes = new ArrayList<ReporteRepPNC>();
		ResultSet resultSet = null;
		CallableStatement cstmt = null;
		try {
			try {
				resultSet = lrsdb.getOracleSP("PKG_GENERACION_DATOS_REPS.f_obtenerDatosRepsPNC("+reporte.getCodigoToString() +")");
			} catch (LRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			ArrayList<String> metricas = null;

			while (resultSet.next()) {

				ArrayList<String> metricasFiltroPx = null;
				ArrayList<String> metricasFiltroProd = null;
				ArrayList<String> metricasFiltroCm = null;

				metricasFiltroPx = new ArrayList<String>();
				metricasFiltroProd = new ArrayList<String>();
				metricasFiltroCm = new ArrayList<String>();

				metricasFiltroPx.add(null);
				metricasFiltroProd.add(null);
				metricasFiltroCm.add(null);

				pnc = new ReporteRepPNC();
				metricas = new ArrayList<String>();

				pnc.setfVenta(resultSet.getString("DESC_FVENTA"));
				pnc.setPxTotal(resultSet.getString("PXTOTAL"));
				pnc.setCantMedicos(resultSet.getString("CANTMED"));
				pnc.setNivel(resultSet.getInt("NIVEL"));
				metricas.add(resultSet.getString("PXMIX"));
				metricas.add(resultSet.getString("PRODMIX"));
				metricas.add(resultSet.getString("CMMIX"));
				metricas.add(resultSet.getString("PXENTRY"));
				metricas.add(resultSet.getString("POTENENTRY"));
				metricas.add(resultSet.getString("CMENTRY"));
				metricas.add(resultSet.getString("PX"));
				metricas.add(resultSet.getString("POTEN"));
				metricas.add(resultSet.getString("CMM"));

				for (int j = 1; j <= 10; j++) {

					if (resultSet.getString("PXF" + j) != null && !resultSet.getString("PXF" + j).isEmpty()) {
						metricasFiltroPx.add(resultSet.getString("PXF" + j));
					}
					if (resultSet.getString("PRODF" + j) != null && !resultSet.getString("PRODF" + j).isEmpty()) {
						metricasFiltroProd.add(resultSet.getString("PRODF" + j));
					}
					if (resultSet.getString("CMF" + j) != null && !resultSet.getString("CMF" + j).isEmpty()) {
						metricasFiltroCm.add(resultSet.getString("CMF" + j));
					}
				}

				pnc.setMetricas(metricas);
				pnc.setMetricasFiltroPx(metricasFiltroPx);
				pnc.setMetricasFiltroProd(metricasFiltroProd);
				pnc.setMetricasFiltroCm(metricasFiltroCm);
				reportes.add(pnc);

			}

		} catch (SQLException e) {
			logger.logueaException(e);
			logger.error("LRSException: " + e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (cstmt != null)
					cstmt.close();
			} catch (SQLException e) {
				logger.error("SQLException: " + e.getMessage());
			}
		}

		return reportes;
	}
	
	/**
	 * @author arocha M2995 
	 * 
	 * No puede cerrarse el resulset debido a que se maneja por fuera de este metodo. 
	 */
	public MedicosProductividadRepresentantes obtenerDatosRepsPRM(Reporte reporte, long merc_size, String esp,
			String geo, String representante, String filtroVisitados, String linea, String grupoSeguimiento,
			long catDesde, long catHasta, long registroDesde, long cantidadRegistros, String columnaOrden,
			String tipoOrden, boolean export) {

		MedicosProductividadRepresentantes medicos = new MedicosProductividadRepresentantes();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		try {
			logger.ejecuto((("").equals(esp) ? null : esp) + " " + (("").equals(geo) ? null : geo) + " "
					+ (("").equals(representante) || ("-1").equals(representante) ? null : representante) + " "
					+ filtroVisitados + " " + (("").equals(linea) ? null : linea) + " "
					+ (((grupoSeguimiento != null) && (!("").equals(grupoSeguimiento)))
							? Integer.parseInt(grupoSeguimiento)
							: 0)
					+ " " + catDesde + " " + catHasta + " " + registroDesde + " " + cantidadRegistros + " "
					+ (export ? "S" : "N") + " " + (("").equals(columnaOrden) ? null : columnaOrden) + " "
					+ (("").equals(tipoOrden) ? null : tipoOrden));
			String query = "BEGIN :1 := PKG_PROD_MED.obtener_query(:2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12, :13, :14, :15); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(15, OracleTypes.NUMBER);
			cstmt.setString(2, (("").equals(esp) ? null : esp));
			cstmt.setString(3, (("").equals(geo) ? null : geo));
			cstmt.setString(4, (("").equals(representante) || ("-1").equals(representante) ? null : representante));
			cstmt.setString(5, filtroVisitados);
			cstmt.setString(6, (("").equals(linea) ? null : linea));
			cstmt.setInt(7,
					(((grupoSeguimiento != null) && (!("").equals(grupoSeguimiento)))
							? Integer.parseInt(grupoSeguimiento)
							: 0));
			cstmt.setLong(8, catDesde);
			cstmt.setLong(9, catHasta);
			cstmt.setLong(10, registroDesde);
			cstmt.setLong(11, cantidadRegistros);
			cstmt.setString(12, (export ? "S" : "N"));
			cstmt.setString(13, (("").equals(columnaOrden) ? null : columnaOrden));
			cstmt.setString(14, (("").equals(tipoOrden) ? null : tipoOrden));
			cstmt.execute();

			medicos.setCantidadDeRegistros(cstmt.getLong(15));
			rs = (ResultSet) cstmt.getObject(1);
			medicos.setResultset(rs);
		} catch (SQLException e) {
			logger.logueaException(e);
			logger.error("LRSException: " + e.getMessage());
		} 
		return medicos;
	}

	/**
	 * @author jangulo
	 * 
	 */
	public MedicosProductividadRepresentantes obtenerDatosRepsPRM(Reporte reporte) {
		MedicosProductividadRepresentantes medicos = new MedicosProductividadRepresentantes();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		try {
			cstmt = lrsdb.getCallableStatement("BEGIN :1 := PKG_GENERACION_DATOS_REPS.f_obtenerDatosRepsPRM(:2, :3); END;");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(3, OracleTypes.NUMBER);
			cstmt.setLong(2, reporte.getCodigo());
			cstmt.execute();
			medicos.setCantidadDeRegistros(cstmt.getLong(3));
			rs = (ResultSet) cstmt.getObject(1);
			medicos.setResultset(rs);
		} catch (SQLException exception) {
			logger.error(exception.getMessage());
		} 
		return medicos;
	}

	
	public List<ReporteRepPNC> obtenerDatosRepsPNC(String cdg_especialidad, String cdg_geografia, String cdg_fventa,
			String cdg_linea, String cdg_grp_seg, long cat_desde, long cat_hasta, String orden_col, String tipo_orden,
			String apertura) {

		ReporteRepPNC pnc = null;
		List<ReporteRepPNC> reportes = new ArrayList<ReporteRepPNC>();
		ResultSet rs = null;
		CallableStatement cstmt = null;

		try {
			logger.ejecuto((("").equals(cdg_especialidad) ? null : cdg_especialidad) + " "
					+ (("").equals(cdg_geografia) ? null : cdg_geografia) + " "
					+ (("").equals(cdg_fventa) || ("-1").equals(cdg_fventa) ? null : cdg_fventa) + " "
					+ (("").equals(cdg_linea) ? null : cdg_linea) + " "
					+ (((cdg_grp_seg != null) && (!("").equals(cdg_grp_seg))) ? Integer.parseInt(cdg_grp_seg) : 0) + " "
					+ cat_desde + " " + cat_hasta + " " + (("").equals(orden_col) ? null : orden_col) + " "
					+ (("").equals(tipo_orden) ? null : tipo_orden) + " " + (("").equals(apertura) ? null : apertura));
			String sql = "";
			if (apertura == null || apertura.isEmpty()) {
				sql = "BEGIN :1 := PKG_PROD_NCAT.obtener_query(:2, :3, :4, :5, :6, :7, :8, :9, :10); END;";
			} else {
				sql = "BEGIN :1 := PKG_PROD_NCAT.obtener_atotal(:2, :3, :4, :5, :6, :7, :8, :9, :10, :11); END;";
			}
			cstmt = lrsdb.getCallableStatement(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, cdg_especialidad);
			cstmt.setString(3, cdg_geografia);
			cstmt.setString(4, cdg_fventa);
			cstmt.setString(5, cdg_linea);
			cstmt.setString(6, cdg_grp_seg);
			cstmt.setLong(7, cat_desde);
			cstmt.setLong(8, cat_hasta);
			cstmt.setString(9, orden_col);
			cstmt.setString(10, tipo_orden);
			if (apertura != null) {
				if (("T").equals(apertura))
					apertura = null;
				cstmt.setString(11, apertura);
			}

			cstmt.execute();

			rs = (ResultSet) cstmt.getObject(1);

			ArrayList<String> metricas = null;

			while (rs.next()) {

				ArrayList<String> metricasFiltroPx = null;
				ArrayList<String> metricasFiltroProd = null;
				ArrayList<String> metricasFiltroCm = null;

				metricasFiltroPx = new ArrayList<String>();
				metricasFiltroProd = new ArrayList<String>();
				metricasFiltroCm = new ArrayList<String>();

				metricasFiltroPx.add(null);
				metricasFiltroProd.add(null);
				metricasFiltroCm.add(null);

				pnc = new ReporteRepPNC();
				metricas = new ArrayList<String>();

				pnc.setfVenta(rs.getString("DESC_FVENTA"));
				pnc.setPxTotal(rs.getString("PXTOTAL"));
				pnc.setCantMedicos(rs.getString("CANTMED"));
				pnc.setNivel(rs.getInt("NIVEL"));
				metricas.add(rs.getString("PXMIX"));
				metricas.add(rs.getString("PRODMIX"));
				metricas.add(rs.getString("CMMIX"));
				metricas.add(rs.getString("PXENTRY"));
				metricas.add(rs.getString("POTENENTRY"));
				metricas.add(rs.getString("CMENTRY"));
				metricas.add(rs.getString("PX"));
				metricas.add(rs.getString("POTEN"));
				metricas.add(rs.getString("CMM"));

				for (int j = 1; j <= 10; j++) {

					if (rs.getString("PXF" + j) != null && !rs.getString("PXF" + j).isEmpty()) {
						metricasFiltroPx.add(rs.getString("PXF" + j));
					}
					if (rs.getString("PRODF" + j) != null && !rs.getString("PRODF" + j).isEmpty()) {
						metricasFiltroProd.add(rs.getString("PRODF" + j));
					}
					if (rs.getString("CMF" + j) != null && !rs.getString("CMF" + j).isEmpty()) {
						metricasFiltroCm.add(rs.getString("CMF" + j));
					}
				}

				pnc.setMetricas(metricas);
				pnc.setMetricasFiltroPx(metricasFiltroPx);
				pnc.setMetricasFiltroProd(metricasFiltroProd);
				pnc.setMetricasFiltroCm(metricasFiltroCm);
				reportes.add(pnc);

			}

		} catch (SQLException e) {
			logger.logueaException(e);
			logger.error("LRSException: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (cstmt != null)
					cstmt.close();
			} catch (SQLException e) {
				logger.error("SQLException: " + e.getMessage());
			}
		}

		return reportes;
	}

	public ReporteRepsPRO obtenerDatosRepsPRO(boolean directo, Reporte reporte) throws SQLException, LRSException {
		ReporteRepsPRO datos = new ReporteRepsPRO();
		ResultSet rs = null;
		try {
			if(directo) {
				rs = lrsdb.getOracleSP("PKG_GENERACION_DATOS_REPS.f_obtenerDatosRepsPRO(" + reporte.getCodigo() + ")");	
			}else {
				rs = lrsdb.getOracleSP("PKG_CONQUISTA_MERCADO.obtenerdatos");
			}
			while (rs.next()) {
				Metrica m = new Metrica(rs.getString("cdg_metrica"));
				Periodo per = new Periodo(rs.getLong("cdg_periodo"));
				per.setDescripcion(rs.getString("desc_periodo"));
				if (rs.getLong("cdg_producto") != -1) {
					Producto prod = new Producto();
					prod.setCodigo(rs.getLong("cdg_producto"));
					prod.setDescripcion(rs.getString("desc_producto"));
					datos.put(m, prod, per, rs.getString("valor"), rs.getString("total"));
				} else {
					Mercado mer = new Mercado();
					mer.setCodigo(rs.getLong("cdg_producto"));
					mer.setDescripcion(rs.getString("desc_producto"));
					datos.put(m, mer, per, rs.getString("valor"), rs.getString("total"));
				}
			}
		} finally {
			if (rs != null)
				rs.close();
		}
		return datos;
	}

	public List<Linea> obtLineas() throws SQLException, LRSException {
		List<Linea> out = new LinkedList<Linea>();

		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_REPS.obtenerLineasMedicos()");
			while (rs.next()) {
				Linea linea = new Linea(rs.getInt("cdg_linea"));
				linea.setDescripcion(rs.getString("desc_linea"));
				out.add(linea);
			}
		} catch (SQLException ex) {
			logger.error("SQLException : obtLineas : " + ex.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return out;
	}

	private void obtenerLinea(ResultSet rs, ReporteReps datos, Reporte reporte) throws SQLException {
		ReporteRepsLinea linea = new ReporteRepsLinea();
		linea.setLinea(rs.getString("linea"));
		linea.setLevel(rs.getInt("level"));
		linea.setApertura(rs.getString("apertura"));
		FuerzaVenta fuerzaVenta = new FuerzaVenta();
		fuerzaVenta.setNombre(rs.getString("desc_fventa"));
		fuerzaVenta.setFventa(rs.getString("cdg_fventa"));
		linea.setFuerzaVenta(fuerzaVenta);
		for (ReporteRepsTitulo titulo : datos.getTitulo()) {
			if (titulo.getSubtitulos() != null) {
				for (ReporteRepsSubTitulo subtitulo : titulo.getSubtitulos()) {
					if (reporte.estaLaMetrica(subtitulo.getMetrica())) {
						linea.ingresaDatoMuestra(rs.getString(subtitulo.getNombreSQL()));
						if (subtitulo.getMetrica().getEsCantidadMedicos()) {
							linea.ingresarInicioColumna(subtitulo.getFuncion());
						} else {
							linea.ingresarInicioColumna("");
						}
					}
				}
			} else {

				if (reporte.estaLaMetrica(titulo.getMetrica())) {
					linea.ingresaDatoMuestra(rs.getString(titulo.getNombreSQL()));
					if (titulo.getMetrica().getEsCantidadMedicos()) {
						linea.ingresarInicioColumna(titulo.getFuncion());
					} else {
						linea.ingresarInicioColumna("");
					}
				}
			}
		}
		datos.agregarLinea(linea);
	}

	private void obtenerLineaPNC(ResultSet rs, ReporteReps datos, Reporte reporte, int level, String apertura)
			throws SQLException {
		ReporteRepsLinea linea = new ReporteRepsLinea();
		linea.setLinea(rs.getString("cdg_fventa"));
		linea.setLevel(level);
		linea.setApertura(apertura);

		FuerzaVenta fuerzaVenta = new FuerzaVenta();
		fuerzaVenta.setNombre(rs.getString("desc_fventa"));
		fuerzaVenta.setFventa(rs.getString("cdg_fventa"));

		linea.setFuerzaVenta(fuerzaVenta);
		int cantTitulos = 0;
		for (ReporteRepsTitulo titulo : datos.getTitulo()) {
			if (titulo.getSubtitulos() != null) {

				for (ReporteRepsSubTitulo subtitulo : titulo.getSubtitulos()) {

					if (reporte.estaLaMetrica(subtitulo.getMetrica())) {
						cantTitulos++;
						linea.ingresaDatoMuestra(rs.getString(subtitulo.getNombreSQL()));
						if (subtitulo.getMetrica().getEsCantidadMedicos()) {

							linea.ingresarInicioColumna(subtitulo.getFuncion());

						} else {
							linea.ingresarInicioColumna("");
						}
					}
				}
			} else {
				if (reporte.estaLaMetrica(titulo.getMetrica())) {
					cantTitulos++;
					linea.ingresaDatoMuestra(rs.getString(titulo.getNombreSQL()));
					if (titulo.getMetrica().getEsCantidadMedicos()) {
						linea.ingresarInicioColumna(titulo.getFuncion());

					} else {
						linea.ingresarInicioColumna("");
					}
				} else if ("IR".equals(titulo.getMetrica().getCodMetrica())) {
					cantTitulos++;
					linea.ingresaDatoMuestra(rs.getString(titulo.getNombreSQL()));
					linea.ingresarInicioColumna(titulo.getFuncion());
				}
			}
		}
		datos.setCantTitulos(cantTitulos);
		datos.agregarLinea(linea);
	}

	public void borrarCacheDatos(Reporte reporte) throws LRSException {
		lrsdb.executeOracleSP("PKG_SERVICIO_REPORTE.borrarCacheDatos(" + reporte.getCodigo() + ")");
	}

	private void obtenerProductos(ResultSet rs, ReporteReps datos, int cantProductos) throws SQLException {
		List<Producto> productos = null;
		for (int i = 1; i <= 25; ++i) {
			long cdgProducto = rs.getLong("prod_" + i);
			if (cdgProducto > 0) {
				Producto producto = new Producto();
				producto.setCodigo(cdgProducto);
				producto.setDescripcion(rs.getString("desc_prod_" + i));
				producto.setPropio("S".equals(rs.getString("prod_propio_" + i)));
				if (productos == null)
					productos = new LinkedList<Producto>();
				productos.add(producto);
			}
		}
		datos.setProductos(productos);
	}

	private void obtenerTitulosPRJ(ResultSet rs, ReporteReps datos, Mercado mercado, Periodo periodo, int indice,
			Reporte reporte, Usuario us) throws SQLException, LRSException {
		List<ReporteRepsTitulo> titulos = datos.getTitulo();

		DAOMetrica daoMetrica = new DAOMetricaImpl(lrsdb);
		String ordenMetricaMD = daoMetrica.obtenerOrdenDefaultSQL("MD");
		String ordenMetricaPX = daoMetrica.obtenerOrdenDefaultSQL("PX");

		// estructura mercados
		Periodo periodo1 = new Periodo();
		periodo1.setCodigo(1);
		periodo1.setDescripcion("G_MERCADO");

		String filtro = lrsdb.getOracleVarchar2("PKG_PRODUCTIVIDAD.obtenerNombreFiltro(" + reporte.getCodigo() + ")");

		ReporteRepsTitulo titMercados = new ReporteRepsTitulo(periodo1);
		titMercados.setNombre("MET_MD", true);
		titMercados.addSubtitulo(mercado.getNombre(), "cant_med_mer", "MD", false, ordenMetricaMD, "TT_MED_MERC", true,
				"med_mer");
		titMercados.addSubtitulo(filtro, "cant_med_lab", "MD", false, ordenMetricaMD, "TT_MED_LAB", true, "med_lab");
		titulos.add(titMercados);

		ReporteRepsTitulo titPrescrip = new ReporteRepsTitulo(periodo1);
		titPrescrip.setNombre("MET_PX", true);
		titPrescrip.addSubtitulo(mercado.getNombre(), "PX_MER", "PX", false, ordenMetricaPX, null, false, null);
		titPrescrip.addSubtitulo(filtro, "PX_LAB", "PX", false, ordenMetricaPX, null, false, null);
		titulos.add(titPrescrip);

		ReporteRepsTitulo titSharePX = new ReporteRepsTitulo(periodo1);
		titSharePX.setNombre("MET_SH_PX", true);
		titSharePX.setNombreSQL("SH_MER");
		Metrica metrica = new Metrica("PC");
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("PC"));
		titSharePX.setMetrica(metrica);
		titulos.add(titSharePX);

		ReporteRepsTitulo titPotencial = new ReporteRepsTitulo(periodo1);
		titPotencial.setNombre("MET_MS_GANAR", true);
		titPotencial.setNombreSQL("sh_ganar");
		titPotencial.setFuncion("med_falt");
		metrica = new Metrica("MD");
		metrica.setEsCantidadMedicos(true);
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("MD"));
		titPotencial.setToolTip("MET_MS_GANAR");
		titPotencial.setMetrica(metrica);
		titulos.add(titPotencial);

		ReporteRepsTitulo titObjetivo = new ReporteRepsTitulo(periodo1);
		titObjetivo.setNombre("MET_SH_OB", true);
		titObjetivo.setNombreSQL("SH_OBJ");
		metrica = new Metrica("PC");
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("PC"));
		titObjetivo.setMetrica(metrica);
		titulos.add(titObjetivo);

		// estructura visitados
		Periodo periodo2 = new Periodo();
		periodo2.setCodigo(2);
		periodo2.setDescripcion("TV_VIS");

		ReporteRepsTitulo titCantMedVisit = new ReporteRepsTitulo(periodo2);
		titCantMedVisit.setNombre("MET_MD", true);
		titCantMedVisit.addSubtitulo(mercado.getNombre(), "cant_med_mer_vis", "MD", false, ordenMetricaMD,
				"TT_MED_MERC", true, "med_mer_vis");
		titCantMedVisit.addSubtitulo("No " + filtro, "cant_med_no_lab_vis", "MD", false, ordenMetricaMD,
				"TT_MED_N_MERC", true, "med_no_lab_vis");
		titCantMedVisit.addSubtitulo(filtro, "cant_med_lab_vis", "MD", false, ordenMetricaMD, "TT_MED_LAB", true,
				"med_lab_vis");
		titulos.add(titCantMedVisit);

		ReporteRepsTitulo titPrescripVis = new ReporteRepsTitulo(periodo2);
		titPrescripVis.setNombre("MET_PX", true);
		titPrescripVis.addSubtitulo(mercado.getNombre(), "PX_MER_VIS", "PX", false, "TT_MED_MERC", null, false, null);
		titPrescripVis.addSubtitulo(filtro, "PX_LAB_VIS", "PX", false, "TT_MED_LAB", null, false, null);
		titulos.add(titPrescripVis);

		ReporteRepsTitulo titProductividadActual = new ReporteRepsTitulo(periodo2);
		titProductividadActual.setNombre("MET_SH_PX", true);
		titProductividadActual.setNombreSQL("SH_MER_VIS");
		metrica = new Metrica("PC");
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("PC"));
		titProductividadActual.setMetrica(metrica);
		titulos.add(titProductividadActual);

		ReporteRepsTitulo titPotencialVis = new ReporteRepsTitulo(periodo2);
		titPotencialVis.setNombre("MET_MS_GANAR", true);
		titPotencialVis.setNombreSQL("SH_OBJETIVO_NEW_VIS");
		titPotencialVis.setFuncion("med_falt_vis");
		metrica = new Metrica("MD");
		metrica.setEsCantidadMedicos(true);
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("MD"));
		titPotencialVis.setMetrica(metrica);
		titPotencialVis.setToolTip("MET_MS_GANAR");
		titulos.add(titPotencialVis);

		ReporteRepsTitulo titObjetivoVis = new ReporteRepsTitulo(periodo2);
		titObjetivoVis.setNombre("MET_SH_OB", true);
		titObjetivoVis.setNombreSQL("SH_OBJ_VIS");
		metrica = new Metrica("PC");
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("PC"));
		titObjetivoVis.setMetrica(metrica);
		titulos.add(titObjetivoVis);

		Periodo periodo3 = new Periodo();
		periodo3.setCodigo(3);
		periodo3.setDescripcion("TV_NOVIS");

		ReporteRepsTitulo titCantMedNoVisit = new ReporteRepsTitulo(periodo3);
		titCantMedNoVisit.setNombre("MET_MD", true);
		titCantMedNoVisit.addSubtitulo(mercado.getNombre(), "cant_med_mer_NOVIS", "MD", false, ordenMetricaMD,
				"TT_MED_MERC", true, "med_mer_Nvis");
		titCantMedNoVisit.addSubtitulo(filtro, "cant_med_lab_novis", "MD", false, ordenMetricaMD, "TT_MED_LAB", true,
				"med_lab_Nvis");
		titulos.add(titCantMedNoVisit);

		ReporteRepsTitulo titPrescripNoVis = new ReporteRepsTitulo(periodo3);
		titPrescripNoVis.setNombre("MET_PX", true);
		titPrescripNoVis.addSubtitulo(mercado.getNombre(), "PX_MER_NOVIS", "PX", false, ordenMetricaPX, null, false,
				null);
		titPrescripNoVis.addSubtitulo(filtro, "PX_LAB_NOVIS", "PX", false, ordenMetricaPX, null, false, null);
		titulos.add(titPrescripNoVis);

		ReporteRepsTitulo titProductividadNoActual = new ReporteRepsTitulo(periodo3);
		titProductividadNoActual.setNombre("MET_SH_PX", true);
		titProductividadNoActual.setNombreSQL("SH_MER_NOVIS");
		metrica = new Metrica("PC");
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("PC"));
		titProductividadNoActual.setMetrica(metrica);
		titulos.add(titProductividadNoActual);

		ReporteRepsTitulo titPotencialNoVis = new ReporteRepsTitulo(periodo3);
		titPotencialNoVis.setNombre("MET_MS_GANAR", true);
		titPotencialNoVis.setNombreSQL("SH_OBJETIVO_NEW_NOVIS");
		titPotencialNoVis.setFuncion("med_falt_Nvis");
		metrica = new Metrica("MD");
		metrica.setEsCantidadMedicos(true);
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("MD"));
		titPotencialNoVis.setMetrica(metrica);
		titPotencialNoVis.setToolTip("MET_MS_GANAR");
		titulos.add(titPotencialNoVis);

		ReporteRepsTitulo titObjetivoNoVis = new ReporteRepsTitulo(periodo3);
		titObjetivoNoVis.setNombre("MET_SH_OB", true);
		titObjetivoNoVis.setNombreSQL("SH_OBJ_NOVIS");
		metrica = new Metrica("PC");
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("PC"));
		titObjetivoNoVis.setMetrica(metrica);
		titulos.add(titObjetivoNoVis);
	}

	private void obtenerTitulosPRN(ResultSet rs, ReporteReps datos, Mercado mercado, Periodo periodo, int indice,
			Reporte reporte) throws SQLException {

		List<ReporteRepsTitulo> titulos = datos.getTitulo();
		String postFijoPeriodo = Integer.toString(indice + 1);

		DAOMetrica daoMetrica = new DAOMetricaImpl(lrsdb);
		String ordenMetricaPX = daoMetrica.obtenerOrdenDefaultSQL("PX");
		String ordenMetricaPC = daoMetrica.obtenerOrdenDefaultSQL("PC");
		String ordenMetricaMD = daoMetrica.obtenerOrdenDefaultSQL("MD");
		// String ordenMetricaIP = daoMetrica.obtenerOrdenDefaultSQL("IP");
		String subtitulo = obtenerPx(reporte.getTipoReporte().getCdgApertura());

		ReporteRepsTitulo titMercado = new ReporteRepsTitulo(periodo);
		titMercado.setNombre(mercado.getDescripcion());
		titMercado.addSubtitulo("T_PRD_PX", "px_mercado" + postFijoPeriodo, "PX", true, ordenMetricaPX, null, false,
				"px_mercado");
		titMercado.addSubtitulo(subtitulo, "px_lab" + postFijoPeriodo, "PX", true, ordenMetricaPX, null, false,
				"px_lab");

		titMercado.addSubtitulo("T_PRD_SHARE", "shr_mercado" + postFijoPeriodo, "PC", true, ordenMetricaPC, null, false,
				"shr_mercado");
		titMercado.addSubtitulo("T_PRD_CTMED", null, "cant_med" + postFijoPeriodo, "MD", true, ordenMetricaMD,
				"TT_MED_MERC", true, "cant_med");
		titMercado.addSubtitulo("T_PRD_CTMEDL", null, "cant_medlab" + postFijoPeriodo, "MD", true, ordenMetricaMD,
				"TT_MED_MERC", true, "cant_medlab");
		titMercado.addSubtitulo("T_MS_MED", null, "shr_medico" + postFijoPeriodo, "MD", true, ordenMetricaMD,
				"TT_MED_MERC", true, "shr_medico");

		titulos.add(titMercado);
		ReporteRepsTitulo titMedVisit = new ReporteRepsTitulo(periodo);
		titMedVisit.setNombre("T_MED_VIS", true);
		titMedVisit.addSubtitulo("T_PRD_PXM", "px_mer_visitados" + postFijoPeriodo, "PX", true, ordenMetricaPX, null,
				false, "px_mer_visitados");
		titMedVisit.addSubtitulo(subtitulo, "px_visitados" + postFijoPeriodo, "PX", true, ordenMetricaPX, null, false,
				"px_visitados");
		titMedVisit.addSubtitulo("T_PRD_SHARE", "T_PRD_PRD_EXPO", "calc_share_visitados" + postFijoPeriodo, "PC", true,
				ordenMetricaPC, null, false, "calc_share_visitados");
		// titMedVisit.addSubtitulo("T_PRD_CTMED",null,"med_visitados"+postFijoPeriodo,"MD",true,ordenMetricaMD,"TT_MED_VIS",true,"med_visitados");
		titMedVisit.addSubtitulo("T_PRD_CTMEDLAB", null, "med_visitados" + postFijoPeriodo, "MD", true, ordenMetricaMD,
				"TT_MED_VIS", true, "med_visitados");
		titulos.add(titMedVisit);

		ReporteRepsTitulo titMedNoVisit = new ReporteRepsTitulo(periodo);
		titMedNoVisit.setNombre("T_MED_NO_VIS", true);
		titMedNoVisit.addSubtitulo("T_PRD_PXM", "px_mer_novisitados" + postFijoPeriodo, "PX", true, ordenMetricaPX,
				null, false, "px_mer_novisitados");
		titMedNoVisit.addSubtitulo(subtitulo, "px_novisitados" + postFijoPeriodo, "PX", true, ordenMetricaPX, null,
				false, "px_novisitados");
		titMedNoVisit.addSubtitulo("T_PRD_SHARE", "calc_share_no_visitados" + postFijoPeriodo, "PC", true,
				ordenMetricaPC, null, false, "calc_share_no_visitados");
		// titMedNoVisit.addSubtitulo("T_PRD_CTMED",null,"med_novisitados"+postFijoPeriodo,"MD",true,ordenMetricaMD,"TT_MED_NO_VIS",true,"med_novisitados");
		titMedNoVisit.addSubtitulo("T_PRD_CTMEDLAB", null, "med_novisitados" + postFijoPeriodo, "MD", true,
				ordenMetricaMD, "TT_MED_NO_VIS", true, "med_novisitados");
		titulos.add(titMedNoVisit);

		ReporteRepsTitulo indiceProductividad = new ReporteRepsTitulo(periodo);
		indiceProductividad.setNombre("T_PRD_IND_PRO", true);
		indiceProductividad.setNombreExpo("T_PRD_IND_PRO_EXPO");
		indiceProductividad.setNombreSQL("INDICE_PRODUCTIVIDAD" + postFijoPeriodo);
		Metrica metrica = new Metrica("IR");

		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("IR"));
		indiceProductividad.setMetrica(metrica);
		titulos.add(indiceProductividad);

		/*
		 * ReporteRepsTitulo indicePotencialidad = new ReporteRepsTitulo(periodo);
		 * indicePotencialidad.setNombre("T_PRD_IND_POT",true); indicePotencialidad
		 * .addSubtitulo("T_PRD_MD_VIS","INDICE_POTENCIALIDAD_VIS" +postFijoPeriodo
		 * ,"IP",true,ordenMetricaIP,false,"INDICE_POTENCIALIDAD_VIS");
		 * indicePotencialidad
		 * .addSubtitulo("T_PRD_MD_NOVIS","INDICE_POTENCIALIDAD_NOVIS" +postFijoPeriodo
		 * ,"IP",true,ordenMetricaIP,false,"INDICE_POTENCIALIDAD_NOVIS");
		 * indicePotencialidad
		 * .addSubtitulo("T_PRD_MD_TOTAL","INDICE_POTENCIALIDAD_TOTAL" +postFijoPeriodo
		 * ,"IP",true,ordenMetricaIP,false,"INDICE_POTENCIALIDAD_TOTAL");
		 * titulos.add(indicePotencialidad);
		 */

		datos.setOrder(rs.getString("campo_order"));
		datos.setOrderType(rs.getString("order_type"));

	}

	public List<FiltroDetalle> obtenerFiltrosPNC(Reporte reporte) {

		List<FiltroDetalle> listaFiltros = new ArrayList<FiltroDetalle>();
		String tipoFiltro = null;
		try {
			tipoFiltro = this.obtenerParametro("TFILTRO");
		} catch (LRSException e) {
			e.printStackTrace();
		}

		if ("RAI".equals(tipoFiltro)) {
			for (Raiz r : reporte.getRaices()) {
				FiltroDetalle fl = new FiltroDetalle();
				fl.setCodigo(r.getCodigoToString());
				fl.setDescripcion(r.getDescripcion());
				listaFiltros.add(fl);
			}
		} else if ("LAB".equals(tipoFiltro)) {
			for (Laboratorio l : reporte.getLaboratorios()) {
				FiltroDetalle fl = new FiltroDetalle();
				fl.setCodigo(l.getCodigoToString());
				fl.setDescripcion(l.getDescripcion());
				listaFiltros.add(fl);
			}
		} else if ("CTE".equals(tipoFiltro)) {
			for (ClaseTerapeutica c : reporte.getClasesTerapeuticas()) {
				FiltroDetalle fl = new FiltroDetalle();
				fl.setCodigo(c.getCodigoToString());
				fl.setDescripcion(c.getDescripcion());
				listaFiltros.add(fl);
			}
		} else if ("DRG".equals(tipoFiltro)) {
			for (Molecula m : reporte.getMoleculas()) {
				FiltroDetalle fl = new FiltroDetalle();
				fl.setCodigo(m.getCodigoToString());
				fl.setDescripcion(m.getDescripcion());
				listaFiltros.add(fl);
			}
		} else if ("PRE".equals(tipoFiltro)) {
			for (Presentacion p : reporte.getPresentaciones()) {
				FiltroDetalle fl = new FiltroDetalle();
				fl.setCodigo(p.getCodigoToString());
				fl.setDescripcion(p.getDescripcion());
				listaFiltros.add(fl);
			}
		}
		return listaFiltros;
	}

	public String obtenerParametro(String tipo) throws LRSException {
		return lrsdb.getOracleVarchar2("libpcup_os.get_variable('" + tipo + "')");
	}

	/**
	 * Obtiene el codigo del label que va a mostrar. Depende del filtro que se
	 * selecciono antes de ejecutar el reporte Tipo Reporte L: Laboratorios D:
	 * Molecula R: Productos C: Clase Terapeutica G: Genericos P: Presentaciones
	 * 
	 * @param filtro
	 *            codigo de filtro que se selecciono
	 * @return el codigo del label en la base que contiene el titulo.
	 */
	private String obtenerPx(String filtro) {

		String valor = "";

		if (filtro.equals("L")) {
			valor = "T_PRD_PLX1";
		} else if (filtro.equals("D")) {
			valor = "T_PRD_PLX2";
		} else if (filtro.equals("R")) {
			valor = "T_PRD_PLX3";
		} else if (filtro.equals("C")) {
			valor = "T_PRD_PLX4";
		} else if (filtro.equals("G")) {
			valor = "T_PRD_PLX5";
		} else if (filtro.equals("P")) {
			valor = "T_PRD_PLX6";
		}
		return valor;
	}

	public List<FuerzaVenta> obtenerFuerzaVentaPorPadre(String padre) throws SQLException {
		return obtenerFuerzaVentaPorPadre(null, padre);
	}

	private void obtenerTitulos(ResultSet rs, ReporteReps datos, Mercado mercado, Periodo periodo, int indice)
			throws SQLException {

		List<ReporteRepsTitulo> titulos = datos.getTitulo();
		String postFijoPeriodo = Integer.toString(indice + 1);
		DAOMetrica daoMetrica = new DAOMetricaImpl(lrsdb);
		String ordenMetricaPX = daoMetrica.obtenerOrdenDefaultSQL("PX");
		String ordenMetricaPC = daoMetrica.obtenerOrdenDefaultSQL("PC");
		String ordenMetricaMD = daoMetrica.obtenerOrdenDefaultSQL("MD");
		String ordenMetricaIP = daoMetrica.obtenerOrdenDefaultSQL("IP");
		ReporteRepsTitulo titMercado = new ReporteRepsTitulo(periodo);

		titMercado.setNombre(mercado.getDescripcion());

		titMercado.addSubtitulo("T_PRD_PX", "px_mercado" + postFijoPeriodo, "PX", true, ordenMetricaPX, null, false,
				"px_mercado");
		titMercado.addSubtitulo("T_PRD_SHARE", "shr_mercado" + postFijoPeriodo, "PC", true, ordenMetricaPC, null, false,
				"shr_mercado");
		titMercado.addSubtitulo("T_PRD_CTMED", null, "cant_med" + postFijoPeriodo, "MD", true, ordenMetricaMD,
				"TT_MED_MERC", true, "cant_med");
		titulos.add(titMercado);
		if (datos.getProductos() != null) {
			int indiceProd = 0;
			for (Producto producto : datos.getProductos()) {
				String postFijoProducto = Integer.toString(++indiceProd);
				ReporteRepsTitulo titProd = new ReporteRepsTitulo(periodo);
				titProd.setNombre(producto.getDescripcion(), false);
				titProd.addSubtitulo("T_PRD_PX", "px_producto" + postFijoPeriodo + "_" + postFijoProducto, "PX", true,
						ordenMetricaPX, null, false, "px_producto");
				titProd.addSubtitulo("T_PRD_SHARE", "shr_producto" + postFijoPeriodo + "_" + postFijoProducto, "PC",
						true, ordenMetricaPC, null, false, "shr_producto");
				titProd.addSubtitulo("T_PRD_CTMED", "md_producto" + postFijoPeriodo + "_" + postFijoProducto, "MD",
						true, ordenMetricaMD, null, true, "cant_medlab");
				titulos.add(titProd);
			}
		}
		ReporteRepsTitulo titMedVisit = new ReporteRepsTitulo(periodo);
		titMedVisit.setNombre("T_MED_VIS", true);
		titMedVisit.addSubtitulo("T_PRD_PX", "px_visitados" + postFijoPeriodo, "PX", true, ordenMetricaPX, null, false,
				"px_visitados");
		titMedVisit.addSubtitulo("T_PRD_PRD", "T_PRD_PRD_EXPO", "calc_share_visitados" + postFijoPeriodo, "PC", true,
				ordenMetricaPC, null, false, "calc_share_visitados");
		titMedVisit.addSubtitulo("T_PRD_CTMED", null, "med_visitados" + postFijoPeriodo, "MD", true, ordenMetricaMD,
				"TT_MED_VIS", true, "med_visitados");
		titulos.add(titMedVisit);
		ReporteRepsTitulo titMedNoVisit = new ReporteRepsTitulo(periodo);
		titMedNoVisit.setNombre("T_MED_NO_VIS", true);
		titMedNoVisit.addSubtitulo("T_PRD_PX", "px_novisitados" + postFijoPeriodo, "PX", true, ordenMetricaPX, null,
				false, "px_novisitados");
		titMedNoVisit.addSubtitulo("T_PRD_SHARE", "calc_share_no_visitados" + postFijoPeriodo, "PC", true,
				ordenMetricaPC, null, false, "calc_share_no_visitados");
		titMedNoVisit.addSubtitulo("T_PRD_CTMED", null, "med_novisitados" + postFijoPeriodo, "MD", true, ordenMetricaMD,
				"TT_MED_NO_VIS", true, "med_novisitados");
		titulos.add(titMedNoVisit);
		ReporteRepsTitulo indiceProductividad = new ReporteRepsTitulo(periodo);
		indiceProductividad.setNombre("T_PRD_IND_PRO", true);
		indiceProductividad.setNombreExpo("T_PRD_IND_PRO_EXPO");
		indiceProductividad.setNombreSQL("INDICE_PRODUCTIVIDAD" + postFijoPeriodo);
		Metrica metrica = new Metrica("IR");
		metrica.setOrdenDefaultMercado(daoMetrica.obtenerOrdenDefaultSQL("IR"));
		indiceProductividad.setMetrica(metrica);
		titulos.add(indiceProductividad);
		ReporteRepsTitulo indicePotencialidad = new ReporteRepsTitulo(periodo);
		indicePotencialidad.setNombre("T_PRD_IND_POT", true);
		indicePotencialidad.addSubtitulo("T_PRD_MD_VIS", "INDICE_POTENCIALIDAD_VIS" + postFijoPeriodo, "IP", true,
				ordenMetricaIP, null, false, "INDICE_POTENCIALIDAD_VIS");
		indicePotencialidad.addSubtitulo("T_PRD_MD_NOVIS", "INDICE_POTENCIALIDAD_NOVIS" + postFijoPeriodo, "IP", true,
				ordenMetricaIP, null, false, "INDICE_POTENCIALIDAD_NOVIS");
		indicePotencialidad.addSubtitulo("T_PRD_MD_TOTAL", "INDICE_POTENCIALIDAD_TOTAL" + postFijoPeriodo, "IP", true,
				ordenMetricaIP, null, false, "INDICE_POTENCIALIDAD_TOTAL");
		titulos.add(indicePotencialidad);
		datos.setOrder(rs.getString("campo_order"));
		datos.setOrderType(rs.getString("order_type"));
	}

	private void obtenerTitulosPRC(ResultSet rs, ReporteReps datos, Mercado mercado, Periodo periodo, int indice)
			throws SQLException {

		List<ReporteRepsTitulo> titulos = datos.getTitulo();
		String postFijoPeriodo = Integer.toString(indice + 1);

		DAOMetrica daoMetrica = new DAOMetricaImpl(lrsdb);

		String ordenMetricaVX = daoMetrica.obtenerOrdenDefaultSQL("VX");
		String ordenMetricaVS = daoMetrica.obtenerOrdenDefaultSQL("VS");
		String ordenMetricaVD = daoMetrica.obtenerOrdenDefaultSQL("VD");
		String ordenMetricaMD = daoMetrica.obtenerOrdenDefaultSQL("MD");

		if (datos.getProductos() != null) {
			int indiceProd = 0;
			for (Producto producto : datos.getProductos()) {

				String postFijoProducto = Integer.toString(++indiceProd);
				ReporteRepsTitulo titProd = new ReporteRepsTitulo(periodo);
				titProd.setNombre(producto.getDescripcion(), false);

				titProd.agregarProducto(producto);

				titProd.addSubtitulo("T_PRD_CTMED", null, "md_producto" + postFijoPeriodo + "_" + postFijoProducto,
						"VD", true, ordenMetricaVD, "TT_MED_PROD", true, "md_producto");

				titProd.addSubtitulo("T_PRD_CTMED",
						"med_visitados" + postFijoPeriodo + (indiceProd == 1 ? "" : "_" + postFijoProducto), "MD", true,
						ordenMetricaMD, "TT_MED_VIS", true, "med_visitados");

				titProd.addSubtitulo("T_PRD_SHARE", "T_PRD_SHARE",
						"shr_producto" + postFijoPeriodo + "_" + postFijoProducto, "VS", true, ordenMetricaVS, null,
						false, "shr_producto");

				titProd.addSubtitulo("T_PRD_CTMED",
						"med_novisitados" + postFijoPeriodo + (indiceProd == 1 ? "" : "_" + postFijoProducto), "MD",
						true, ordenMetricaMD, "TT_MED_NO_VIS", true, "med_novisitados");

				titProd.addSubtitulo("T_PRD_SHARE", "T_PRD_SHARE",
						"px_prod_novis" + postFijoPeriodo + "_" + postFijoProducto, "VS", true, ordenMetricaVS, null,
						false, "px_prod_novis");

				titProd.addSubtitulo("T_PRD_PX", "px_producto" + postFijoPeriodo + "_" + postFijoProducto, "VX", true,
						ordenMetricaVX, null, false, "px_producto");
				titulos.add(titProd);
			}
		}
		datos.setOrder(rs.getString("campo_order"));
		datos.setOrderType(rs.getString("order_type"));

	}

	private void obtenerEncabezados(ResultSet rs, ReporteReps datos, Mercado mercado, Raiz raiz, Reporte reporte,
			Usuario us) throws SQLException, LRSException {

		datos.setTitulo(new LinkedList<ReporteRepsTitulo>());
		if (reporte.getTipoConsulta().esElTipoConsulta("PRJ")) {
			obtenerTitulosPRJ(rs, datos, mercado, null, 0, reporte, us);
		} else if (reporte.getTipoConsulta().esElTipoConsulta("PNC")) {
			obtenerTitulosPNC(rs, datos, mercado, null, 0, reporte);
		} else {
			this.obtenerProductos(rs, datos, reporte.getRaices().size());
			for (int i = 0; i < reporte.getPeriodos().size(); ++i) {
				if (reporte.getTipoConsulta().esElTipoConsulta("PRC"))
					obtenerTitulosPRC(rs, datos, mercado, reporte.getPeriodos().get(i), i);
				else if (reporte.getTipoConsulta().esElTipoConsulta("PRN"))
					obtenerTitulosPRN(rs, datos, mercado, reporte.getPeriodos().get(i), i, reporte);
				else
					obtenerTitulos(rs, datos, mercado, reporte.getPeriodos().get(i), i);
			}
		}

	}

	public void guardarVariable(String key, String valor) throws LRSException {
		lrsdb.executeOracleSP("libpcup_os.guardar_variable('" + key + "','" + valor + "')");
	}

	public void cachear(String cdgReporte) throws LRSException {
		this.lrsdb.executeOracleSP("PKG_SERVICIO_REPORTE.guardarCacheDatos(" + cdgReporte + ",0)");
	}

	public boolean existenDatos(String cdgReporte) throws LRSException {
		long a = this.lrsdb.getOracleNumber("PKG_PROD_MED.existen_datos(" + cdgReporte + ",0)");
		return a > 0;
	}

	public void realizarAperturas(Reporte reporte) throws LRSException {
		this.lrsdb.executeOracleSP("PKG_GESTION_APERTURAS.aperturas_reportes(" + reporte.getCodigo() + ")");
	}

	public List<FuerzaVenta> obtenerFuerzaVentaPorPadre(Reporte reporte, String padre) throws SQLException {

		String resultado = ((padre == null) ? "-1" : padre);
		List<FuerzaVenta> listadoFzaVta = new ArrayList<FuerzaVenta>();
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			String query = "BEGIN :1 := PKG_CONSULTA_MAESTROS.getFzaVtaPorPadre(:2, :3); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, resultado);
			cstmt.setString(3, reporte == null ? "0" : "1");
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				FuerzaVenta fzavta = new FuerzaVenta();
				fzavta.setCdg_fventa_hijo(rs.getString("cdg_fventa"));
				fzavta.setCdg_fventa_padre(rs.getString("cdg_fventa_padre"));
				fzavta.setNombre(rs.getString("nombre"));
				listadoFzaVta.add(fzavta);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
		}

		return listadoFzaVta;
	}

	public List<FuerzaVenta> obtenerFuerzaVentaPorPadreYLinea(Reporte reporte, String linea) throws SQLException {

		List<FuerzaVenta> listadoFzaVta = new ArrayList<FuerzaVenta>();
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			String query = "BEGIN :1 := PKG_CONSULTA_MAESTROS.getFzaVtaPorPadreYLinea(:2, :3); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, linea);
			cstmt.setString(3, reporte == null ? "0" : "1");
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				FuerzaVenta fzavta = new FuerzaVenta();
				fzavta.setCdg_fventa_hijo(rs.getString("cdg_fventa"));
				fzavta.setCdg_fventa_padre(rs.getString("cdg_fventa_padre"));
				fzavta.setNombre(rs.getString("nombre"));
				listadoFzaVta.add(fzavta);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
		}

		return listadoFzaVta;
	}

	public void cargarGtmpFventasReporte(long cdgReporte) throws LRSException {
		// INC #1829
		long cdgUsuario = this.lrsdb.getOracleNumber("PKG_ESQUEMAS_OS.get_sesion_usuario_actual");
		boolean estaSegPorGeo = this.lrsdb
				.getOracleVarchar2("PKG_SERVICIO_USUARIO.estaSegmentadoPorGeografia(" + cdgUsuario + ")").equals("S");

		if (estaSegPorGeo) {
			this.lrsdb.executeOracleSP("PKG_SEGMENTACION.cargarGeografiaUsuario(" + cdgUsuario + ")");
		}
		this.lrsdb.executeOracleSP("PKG_TARGET.incializar_geografias(" + cdgReporte + ")");
		this.lrsdb.executeOracleSP("PKG_PERFIL.cargarGtmpFventasReporte");
	}

	@Override
	public AnalisisProdNoVisitados obtenerMedicosNoVisitado(long catDesde, long catHasta, String tipoOrden,
			String columnaOrden) {
		AnalisisProdNoVisitados medicos = new AnalisisProdNoVisitados();
		ResultSet rs = null;
		CallableStatement cstmt = null;

		try {
			String query = "BEGIN :1 := pkg_prod_ncat.query_noVisitados(:2, :3, :4, :5, :6 ,:7); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, null);
			cstmt.setString(3, null);
			cstmt.setLong(4, catDesde);
			cstmt.setLong(5, catHasta);
			cstmt.setString(6, columnaOrden);
			cstmt.setString(7, tipoOrden);

			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			medicos.setResultset(rs);
			// rs.close();

		} catch (SQLException e) {
			logger.logueaException(e);
			logger.error("LRSException: " + e.getMessage());
		} /*
			 * finally { if (cstmt != null) { try { cstmt.close(); } catch (SQLException e)
			 * { e.printStackTrace(); } } }
			 */

		return medicos;
	}


	@Override
	public AnalisisProdNoVisitados obtenerMedicosNoVisitado(Reporte reporte) {
		AnalisisProdNoVisitados medicos = new AnalisisProdNoVisitados();
		try {
			ResultSet rs = lrsdb.getOracleSP("PKG_DESARROLLO_ANALYZER.obtenerDatos2RepsPNC("+reporte.getCodigoToString() +")");
			medicos.setResultset(rs);
		} catch (LRSException exception) {
			logger.error(exception.getMessage());
		}		
		return medicos;	
	}

	
	public String obtenerAperturasPNC() throws SQLException, LRSException {

		return lrsdb.getOracleVarchar2("PKG_PROD_NCAT.obtener_apertura()");
	}

	private void obtenerTitulosPNC(ResultSet rs, ReporteReps datos, Mercado mercado, Periodo periodo, int indice,
			Reporte reporte) throws SQLException {

		List<ReporteRepsTitulo> titulos = datos.getTitulo();

		DAOMetrica daoMetrica = new DAOMetricaImpl(lrsdb);

		String ordenMetricaML = daoMetrica.obtenerOrdenDefaultSQL("ML");
		String ordenMetricaMA = daoMetrica.obtenerOrdenDefaultSQL("MA");
		String ordenMetricaPS = daoMetrica.obtenerOrdenDefaultSQL("PS");
		String ordenMetricaMD = daoMetrica.obtenerOrdenDefaultSQL("MD");

		String metricaSeleccionada = "";

		// estructura mercados
		Periodo periodo1 = new Periodo();
		periodo1.setCodigo(1);
		periodo1.setDescripcion("G_MERCADO");

		periodo = periodo1;

		ReporteRepsTitulo pxTotalMed = new ReporteRepsTitulo(periodo);
		pxTotalMed.setNombre("LBL_PXTM", true);
		// pxTotalMed.setNombreExpo("T_PRD_IND_PRO_EXPO");
		pxTotalMed.setNombreSQL("pxTotal");
		pxTotalMed.setFuncion("px_totalMed");
		Metrica metrica = new Metrica("IR");

		metrica.setOrdenDefaultMercado("DESC");
		pxTotalMed.setMetrica(metrica);
		titulos.add(pxTotalMed);

		ReporteRepsTitulo cantMed = new ReporteRepsTitulo(periodo);
		cantMed.setNombre("LBL_CM", true);
		cantMed.setNombreSQL("cantMed");
		cantMed.setFuncion("cantMed");

		Metrica metrica1 = new Metrica("MD");

		metrica1.setOrdenDefaultMercado("DESC");
		metrica1.setEsCantidadMedicos(true);
		cantMed.setMetrica(metrica1);
		titulos.add(cantMed);

		if (!reporte.estaLaMetrica(new Metrica("MD")))
			metricaSeleccionada = "MD";

		// INC #1982
		ServicioFacade servicioFacade = new ServicioFacade(lrsdb);
		boolean muestraPX = false;

		// INC #1982
		try {
			muestraPX = "SI".equals(servicioFacade.getServicioParametros().ObtenerParametroEdicion("PX_ABS_TARGET",
					Sistema.SISTEMA_TARGET, null)) && get_met("PX", reporte);
		} catch (LRSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ReporteRepsTitulo titMercMix = new ReporteRepsTitulo(periodo);
		titMercMix.setNombre("T_MER_MIX", true);
		// INC #1982
		if (muestraPX) {
			titMercMix.addSubtitulo("T_PRD_PX", "pxMix", "ML", true, ordenMetricaML, null, false, "px_mer_mix");
		}
		titMercMix.addSubtitulo("LBL_PROD", "prodMix", "ML", true, null, null, false, "prod_mix");
		titMercMix.addSubtitulo("LBL_CANT_MED", "cmMix", (metricaSeleccionada != "" ? metricaSeleccionada : "ML"), true,
				ordenMetricaMD, null, false, "cm_mix");
		titulos.add(titMercMix);

		ReporteRepsTitulo titMercLab = new ReporteRepsTitulo(periodo);
		titMercLab.setNombre("T_MERC_LAB", true);
		// INC #1982
		if (muestraPX) {
			titMercLab.addSubtitulo("T_PRD_PX", "pxEntry", "MA", true, ordenMetricaMA, null, false, "px_mer_lab");
		}
		titMercLab.addSubtitulo("LBL_POTEN", "potenEntry", "MA", true, null, null, false, "poten_lab");
		titMercLab.addSubtitulo("LBL_CANT_MED", "cmEntry", (metricaSeleccionada != "" ? metricaSeleccionada : "MA"),
				true, ordenMetricaMD, null, false, "cm_lab");
		titulos.add(titMercLab);

		ReporteRepsTitulo titMercado = new ReporteRepsTitulo(periodo);
		titMercado.setNombre(mercado.getDescripcion());
		// INC #1982
		if (muestraPX) {
			titMercado.addSubtitulo("T_PRD_PX", "px", "PS", true, ordenMetricaPS, null, false, "px_mercado");
		}
		titMercado.addSubtitulo("LBL_PCT", "poten", "PS", true, null, null, false, "prod_merc");
		titMercado.addSubtitulo("LBL_CANT_MED", "cmm", (metricaSeleccionada != "" ? metricaSeleccionada : "PS"), true,
				ordenMetricaMD, null, false, "cm_m");
		titulos.add(titMercado);

		List<FiltroDetalle> listaFiltros = obtenerFiltrosPNC(reporte);
		int i = 1;
		for (FiltroDetalle fd : listaFiltros) {
			ReporteRepsTitulo titFiltro = new ReporteRepsTitulo(periodo);
			titFiltro.setNombre(fd.getDescripcion());
			// INC #1982
			if (muestraPX) {
				titFiltro.addSubtitulo("T_PRD_PX", "pxf" + i, "PS", true, "DESC", null, false, "px_filtro");
			}
			titFiltro.addSubtitulo("LBL_PCT", "prodf" + i, "PS", true, null, null, false, "prod_filtro");
			titFiltro.addSubtitulo("LBL_CANT_MED", "cmf" + i, (metricaSeleccionada != "" ? metricaSeleccionada : "PS"),
					true, null, null, false, "cm_f");

			titulos.add(titFiltro);
			i++;
		}
		// Hacer el ciclo para que recorra los filtros y arme los titulos, los voy a
		// obtener del objeto reporte, algo similar al del PRM
		// tengo que agregar una llamada a la varible que devuelve el tipo de filtro.
	}

	public boolean get_met(String tipo, Reporte reporte) {
		for (Metrica met_list : reporte.getMetricas()) {
			if (met_list.getCodMetrica().equals(tipo)) {
				return true;
			}
		}
		return false;
	}

	public void prepararNvgMesoPais(String cdgReporte, String cdgGeografia) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPS.preparar_nvg_mesoPais(" + cdgReporte + ", " + cdgGeografia + ")");
	}

	/**
	 * Se utiliza en Reporte PRJ, cuando es necesario obtener la recategorización.
	 * 
	 * @return ReporteRepsResumenCat contenedor de registros resultantes.
	 * @throws LRSException
	 * @author jangulo FTN1118
	 */
	public ReporteRepsResumenCat cargarResumenCategorizacion() throws LRSException {
		ResultSet rs = lrsdb.getOracleSP("PKG_PRODUCTIVIDAD.OBTENER_COMPARACION");
		ReporteRepsResumenCat reporterepsresumencat = new ReporteRepsResumenCat();
		if (rs != null) {
			try {
				while (rs.next()) {
					reporterepsresumencat.addDataList(rs.getInt("CATEGORIA"), rs.getInt("TOTAL"), rs.getInt("ALTAS"),
							rs.getInt("BAJAS"), rs.getString("TIPO"));
				}
			} catch (SQLException e) {
				logger.error("DAOReporteRepsImpl : cargarResumenCategorizacion : " + e);
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("DAOReporteRepsImpl : cargarResumenCategorizacion : " + e);
				}
			}
		}
		return reporterepsresumencat;
	}

	/**
	 * Para cargar las temporales necesarias para visualizar reporte Perfil
	 * Prescriptivo.
	 * 
	 * @param laboratorio
	 *            en el cual se trabaja.
	 * @param categoria
	 *            del grupo.
	 * @param visitado
	 * @param faltante
	 * @param periodo
	 *            En el que se maneja la información.
	 * @throws LRSException
	 * 
	 */
	public void cargarGtmpMedicos(int laboratorio, long categoria, String visitado, boolean faltante, int periodo)
			throws LRSException {
		lrsdb.executeOracleSP("PKG_PRODUCTIVIDAD.REPS_CARGAR_TMP_MEDICOS_PRJ(" + laboratorio + "," + categoria + ",'"
				+ visitado + "','" + ((faltante) ? "S" : "N") + "'," + periodo + ")");
	}

	/**
	 * Carga temporales desde la tabla resumen del reporte PRJ.
	 * 
	 * @param categoria
	 *            a buscar.
	 * @param tipo
	 *            (Mercado, Visitado o No)
	 * @param altaBaja
	 *            si es alta de categoria o lo contrario.
	 * @throws LRSException
	 * @author jangulo FTN1118
	 */
	public void cargarMedicosTablaResumen(long categoria, String tipo, long altaBaja) throws LRSException {
		lrsdb.executeOracleSP(
				"PKG_PRODUCTIVIDAD.CARGAR_MEDICOS_TABLA_RESUMEN(" + categoria + ",'" + tipo + "'," + altaBaja + ")");

	}

}

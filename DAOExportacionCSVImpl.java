package pcup.domain.dao.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import pcup.domain.dao.DAOExportacionCSV;
import pcup.domain.service.ServicioFacade;
import plataforma.domain.model.DatosExportacionHorizontalColum;
import plataforma.domain.model.ErroresFiltros;
import plataforma.domain.model.Metrica;
import plataforma.domain.model.Periodo;
import plataforma.domain.model.Reporte;
import plataforma.util.PlataformaLogger;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOExportacionCSVImpl implements DAOExportacionCSV {

	protected final PlataformaLogger logger = PlataformaLogger.obtenerLoggerDefault();
	protected LRSDataBase lrsdb = null;
	private String caracterSeparacion = ";";
	private String caracterDecimal = ",";
	private String caracterSeparadorMil = ".";
	private ServicioFacade servicioFacade = null;

	private int numeroMaximoDomiciliosEnExportacion = 0;

	public int getNumeroMaximoDomicilios() {
		return numeroMaximoDomiciliosEnExportacion;
	}

	public DAOExportacionCSVImpl(LRSDataBase lrsdb) {
		this.lrsdb = lrsdb;
		servicioFacade = new ServicioFacade(lrsdb);
	}

	private String convertString2double(String dato) {

		Double doble = Double.valueOf(dato.trim());
		return doble.toString().replace(",", caracterSeparadorMil).replace(".", caracterDecimal);
	}

	private boolean isNumerico(String dato) {
		if (NumberUtils.isDigits(dato.replace(",", "").replace(".", "").trim())) {
			return StringUtils.countMatches(dato, ".") < 2 && StringUtils.countMatches(dato, ",") < 2;
		}
		return false;
	}

	private void guardarVariable(BufferedWriter csv, String valor) throws IOException {

		if (valor != null) {
			try {
				if (isNumerico(valor)) {
					csv.append(convertString2double(valor).toString() + caracterSeparacion);
				} else {
					csv.append("\"" + valor + "\"" + caracterSeparacion);
				}
			} catch (Exception e) {
				csv.append("\"" + valor + "\"" + caracterSeparacion);
			}
		} else {
			csv.append(caracterSeparacion);
		}
	}

	private void guardarVariable(BufferedWriter csv, String valor, boolean esNumerico) throws IOException {

		if (valor != null) {
			if (esNumerico) {
				csv.append(convertString2double(valor).toString() + caracterSeparacion);
			} else {
				csv.append("\"" + valor + "\"" + caracterSeparacion);
			}
		} else {
			csv.append(caracterSeparacion);
		}
	}

	/**
	 * @author jangulo PXS-240
	 * 
	 *         1703: si viene de reps y no debe mostrar px hay q restar 1
	 */
	public List<Exception> getPerfilExportacion(Reporte reporte, long registroDesde, long registroHasta,
			boolean muestraDir, String aperturas, String orden, BufferedWriter csv, boolean consultaSndRep,
			String encabezadoListado, String erroresFiltros, int[] numeroLinea, int cantPeriodosFinal,
			boolean esRepsSinPX, boolean isExpCdgLider, boolean emailAllowed) throws IOException {
		List<Exception> exceptions = new ArrayList<Exception>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String qry = "";
		try {
			int cantPeriodos = reporte.getPeriodos().size();
			cantPeriodos = cantPeriodosFinal; // 1703: esta linea debe ir aca y estaba mas abajo
			qry = "BEGIN :1 := pkg_exportacion_perfil.exportar_perfil(" + reporte.getCodigo() + ", '" + aperturas
					+ "', " + cantPeriodos + ", '" + orden + "', " + registroDesde + "," + registroHasta + ", '"
					+ (muestraDir ? "SI" : "NO") + "', '" + (consultaSndRep ? "SI" : "NO") + "',6); END;";
			cstmt = this.lrsdb.getCallableStatement(qry);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			boolean tienePondFinal = false;
			for (Metrica metrica : reporte.getMetricas()) {
				if (metrica.getCodMetrica().contains("PO")) {
					tienePondFinal = true;
				}
			}
			final int COLUMNAS_INICIALES_IGNORADAS_POR_METRICAS = 9;
			final int COLUMNAS_INICIALES_IGNORADAS_POR_APERTURAS = 11;
			int cantidadMetricasExtra = (tienePondFinal ? 1 : 0);
			int cantidadMetricas = esRepsSinPX ? reporte.getMetricas().size() - 1 : reporte.getMetricas().size();
			int maximaColumnaMetrica = COLUMNAS_INICIALES_IGNORADAS_POR_METRICAS
					+ cantPeriodos * (cantidadMetricas + cantidadMetricasExtra);
			int inicioColumnaAperturas = COLUMNAS_INICIALES_IGNORADAS_POR_APERTURAS
					+ cantPeriodos * (cantidadMetricas + cantidadMetricasExtra);
			int totalColumnas = rs.getMetaData().getColumnCount();
			if (muestraDir) {
				totalColumnas = totalColumnas - 3;
			}
			boolean[] sonFloat = new boolean[totalColumnas];
			for (int contador = 1; contador <= totalColumnas; contador++) {
				if (rs.getMetaData().getColumnName(contador).contains("PM")
						|| rs.getMetaData().getColumnName(contador).contains("PL")
						|| rs.getMetaData().getColumnName(contador).contains("PO")
						|| rs.getMetaData().getColumnName(contador).contains("PF")
						|| rs.getMetaData().getColumnName(contador).contains("PCT")) {
					sonFloat[contador - 1] = true;
				} else {
					sonFloat[contador - 1] = false;
				}
			}
			if (registroDesde == 1) {
				if (rs.next()) {
					if (reporte.isTieneErroresFiltros()) {
						erroresFiltros = erroresFiltros(reporte);
						csv.append(erroresFiltros);
						numeroLinea[0] += erroresFiltros.split("\n").length;
					}
					csv.append(encabezadoListado);
					try {
						imprimirPerfil(reporte, csv, rs, maximaColumnaMetrica, inicioColumnaAperturas, totalColumnas,
								sonFloat, muestraDir, isExpCdgLider, emailAllowed);
						csv.newLine();
					} catch (LRSException lrs) {
						exceptions.add(lrs);
					}
				} else {
					csv.append(servicioFacade.getServicioLabel().getLabelNoHtml(reporte.getUsuario(), "MSG_ERR_FILTROS")
							+ "\n");
					numeroLinea[0]++;
					csv.append(encabezadoListado);
				}
			}
			while (rs.next()) {
				try {
					imprimirPerfil(reporte, csv, rs, maximaColumnaMetrica, inicioColumnaAperturas, totalColumnas,
							sonFloat, muestraDir, isExpCdgLider, emailAllowed);
					csv.newLine();
				} catch (LRSException lrs) {
					exceptions.add(lrs);
				}
			}
		} catch (SQLException exception) {
			logger.logueaException(exception);
			logger.error(exception.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException exception) {
					logger.logueaException(exception);
					logger.error(exception.getMessage());
				}
			}
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException exception) {
					logger.logueaException(exception);
					logger.error(exception.getMessage());
				}
			}
		}
		return exceptions;
	}

	/**
	 * @author jangulo PXS-240
	 */
	public void getPerfilExportacionColum(Reporte reporte, long registroDesde, long registroHasta, boolean muestraDir,
			String aperturas, String orden, BufferedWriter csv, boolean consultaSndRep, String encabezadoListado,
			String erroresFiltros, int[] numeroLinea, int cantPeriodosFinal, int columSegApert, int columnaDomicilio,
			boolean tienePM, boolean tienePX, boolean isExpCdgLider, boolean emailAllowed)
			throws IOException, LRSException {
		
		System.out.println("HELALUBO-> DaoExportacionCsvImpl.getPerfilExportacionColum");
		LinkedList<DatosExportacionHorizontalColum> out = new LinkedList<DatosExportacionHorizontalColum>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String qry = "";
		boolean[] sonFloat = null;
		String[] campoSonFloat = null;
		try {
			int cantPeriodos = reporte.getPeriodos().size();
			qry = "BEGIN :1 := pkg_exportacion_perfil.exportar_perfil(" + reporte.getCodigo() + ", '" + aperturas
					+ "', " + cantPeriodos + ", '" + orden + "', " + registroDesde + "," + registroHasta + ", '"
					+ (muestraDir ? "SI" : "NO") + "', '" + (consultaSndRep ? "SI" : "NO") + "',6); END;";
			cstmt = this.lrsdb.getCallableStatement(qry);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			boolean tienePondFinal = false;
			for (Metrica metrica : reporte.getMetricas()) {
				if (metrica.getCodMetrica().contains("PO")) {
					tienePondFinal = true;
				}
			}
			if (!tienePX) {
				reporte.getMetricas().remove(new Metrica("PX"));
			}
			cantPeriodos = cantPeriodosFinal;
			final int COLUMNAS_INICIALES_IGNORADAS_POR_METRICAS = 9;
			final int COLUMNAS_INICIALES_IGNORADAS_POR_APERTURAS = 11;
			int cantidadMetricasExtra = (tienePondFinal ? 1 : 0);
			int cantidadMetricas = reporte.getMetricas().size();
			int maximaColumnaMetrica = COLUMNAS_INICIALES_IGNORADAS_POR_METRICAS
					+ cantPeriodos * (cantidadMetricas + cantidadMetricasExtra);
			int inicioColumnaAperturas = COLUMNAS_INICIALES_IGNORADAS_POR_APERTURAS
					+ cantPeriodos * (cantidadMetricas + cantidadMetricasExtra);
			DatosExportacionHorizontalColum datosExportacionHorizontalColum = new DatosExportacionHorizontalColum(
					reporte.getPeriodos());
			String posicion = "-1";
			while (rs.next()) {
				int totalColumnas = rs.getMetaData().getColumnCount();
				if (muestraDir) {
					totalColumnas = totalColumnas - 3;
				}
				sonFloat = new boolean[totalColumnas];
				campoSonFloat = new String[totalColumnas];
				for (int contador = 1; contador <= totalColumnas; contador++) {
					if (rs.getMetaData().getColumnName(contador).contains("PM")
							|| rs.getMetaData().getColumnName(contador).contains("PL")
							|| rs.getMetaData().getColumnName(contador).contains("PO")
							|| rs.getMetaData().getColumnName(contador).contains("PF")
							|| rs.getMetaData().getColumnName(contador).contains("PCT")) {
						sonFloat[contador - 1] = true;
					} else {
						sonFloat[contador - 1] = false;
					}
					campoSonFloat[contador - 1] = rs.getMetaData().getColumnName(contador);
				}
				if (!rs.getString("posicion").equals(posicion)
						|| !rs.getString("mercado").equals(datosExportacionHorizontalColum.getMercado())) {
					posicion = rs.getString("posicion");
					if (datosExportacionHorizontalColum.getMedico() != null) {
						out.addLast(datosExportacionHorizontalColum);
						datosExportacionHorizontalColum = new DatosExportacionHorizontalColum(reporte.getPeriodos());
					}
					datosExportacionHorizontalColum.setMercado(rs.getString("mercado"));
					datosExportacionHorizontalColum.setDescripcion(rs.getString("descripcion"));
					datosExportacionHorizontalColum.setMatricula(rs.getString("matricula"));
					datosExportacionHorizontalColum.setDesc_esp1(rs.getString("desc_esp1"));
					datosExportacionHorizontalColum.setDesc_esp2(rs.getString("desc_esp2"));
					datosExportacionHorizontalColum.setFvta(rs.getString("fvta"));
					datosExportacionHorizontalColum.setDesc_mercado(rs.getString("desc_mercado"));
					datosExportacionHorizontalColum.setMedico(rs.getString("cdg_medico"));
					datosExportacionHorizontalColum.setCdgLider(rs.getString("cdg_lider"));
					for (int contador = COLUMNAS_INICIALES_IGNORADAS_POR_METRICAS; contador < maximaColumnaMetrica; contador++) {
						String valor = rs.getString(contador);
						datosExportacionHorizontalColum.cargarMetrica(valor);
					}
					if (muestraDir) {
						if (emailAllowed) {
							datosExportacionHorizontalColum.setEmail1(rs.getString("email1"));
							datosExportacionHorizontalColum.setEmail2(rs.getString("email2"));
						}
						obtenerDomiciliosColum(rs.getString(totalColumnas + 1), datosExportacionHorizontalColum);
					}
				}
				int a = (totalColumnas - inicioColumnaAperturas) / 2;
				int cantMetricas = 1;
				cantMetricas++;
				if (tienePX) {
					cantMetricas++;
				}
				int cantApertProd = reporte.getPeriodos().size() * cantMetricas + 1;
				String descripcion = null;
				for (int contador = inicioColumnaAperturas; contador <= totalColumnas; contador++) {
					if ((aperturas.indexOf(",") > -1) && (contador > (inicioColumnaAperturas + a))) {
						if ((contador - inicioColumnaAperturas) % cantApertProd == 0) {
							descripcion = rs.getString(contador);
						}
						for (Periodo periodo : reporte.getPeriodos()) {
							contador++;
							String valor1 = rs.getString(contador);
							String valor2 = null;
							if (cantMetricas > 1) {
								contador++;
								valor2 = rs.getString(contador);
							}
							String valor3 = null;
							if (cantMetricas > 2) {
								contador++;
								valor3 = rs.getString(contador);
							}
							if (valor1 != null || valor2 != null || valor3 != null) {
								datosExportacionHorizontalColum.cargarSegundaApertura(descripcion, periodo);
								datosExportacionHorizontalColum.cargarSegundaApertura(valor1, periodo);
								if (cantMetricas > 1)
									datosExportacionHorizontalColum.cargarSegundaApertura(valor2, periodo);
								if (cantMetricas > 2)
									datosExportacionHorizontalColum.cargarSegundaApertura(valor3, periodo);
							}
						}
					} else {
						if ((contador - inicioColumnaAperturas) % cantApertProd == 0) {
							descripcion = rs.getString(contador);
						}
						for (Periodo periodo : reporte.getPeriodos()) {
							contador++;
							String valor1 = rs.getString(contador);
							String valor2 = null;
							if (cantMetricas > 1) {
								contador++;
								valor2 = rs.getString(contador);
							}
							String valor3 = null;
							if (cantMetricas > 2) {
								contador++;
								valor3 = rs.getString(contador);
							}
							if (valor1 != null || valor2 != null || valor3 != null) {
								datosExportacionHorizontalColum.cargarPrimeraApertura(descripcion, periodo);
								datosExportacionHorizontalColum.cargarPrimeraApertura(valor1, periodo);
								if (cantMetricas > 1)
									datosExportacionHorizontalColum.cargarPrimeraApertura(valor2, periodo);
								if (cantMetricas > 2)
									datosExportacionHorizontalColum.cargarPrimeraApertura(valor3, periodo);
							}
						}
					}
				}
			}
			out.addLast(datosExportacionHorizontalColum);
			imprimirPerfilColumn(reporte, csv, out, erroresFiltros, numeroLinea, encabezadoListado, columSegApert,
					columnaDomicilio, tienePondFinal, isExpCdgLider, sonFloat, emailAllowed);
		} catch (SQLException exception) {
			logger.logueaException(exception);
			logger.error(exception.getMessage());
		} finally {
			if (cstmt != null) {
				try {
					rs.close();
					cstmt.close();
				} catch (SQLException exception) {
					logger.logueaException(exception);
					logger.error(exception.getMessage());
				}
			}
		}

	}

	private void imprimirPerfilColumn(Reporte reporte, BufferedWriter csv,
			LinkedList<DatosExportacionHorizontalColum> listDatosExportacionHorizontalColum, String erroresFiltros,
			int[] numeroLinea, String encabezadoListado, int columSegApert, int columnaDomicilio,
			boolean muestraPonderados, boolean isExpCdgLider, boolean[] sonFloat, boolean emailAllowed)
			throws IOException {
		
		
		System.out.println("Entra a DAOEXPORTACION CSV IMPL ->imprimirPerfilColumn ");
		if (reporte.isTieneErroresFiltros()) {
			erroresFiltros = erroresFiltros(reporte);
			csv.append(erroresFiltros);
			numeroLinea[0] += erroresFiltros.split("\n").length;
		}
		csv.append(encabezadoListado);
		for (DatosExportacionHorizontalColum medico : listDatosExportacionHorizontalColum) {
			int posicionIni = 0;
			for (Periodo periodo : reporte.getPeriodos()) {
				guardarVariable(csv, medico.getDescripcion());
				guardarVariable(csv, medico.getMatricula(), false);
				guardarVariable(csv, medico.getDesc_esp1());
				guardarVariable(csv, medico.getDesc_esp2());
				guardarVariable(csv, medico.getFvta(), false);
				guardarVariable(csv, medico.getDesc_mercado());
				int contador = 9;
				for (String valor : medico.devolverMetricas(posicionIni, reporte.getPeriodos().size(),
						muestraPonderados)) {
					if (sonFloat[contador - 1]) {
						if (valor != null) {
							
							System.out.println("Helalubo ponderados -> " +valor );
							
							guardarVariable(csv, valor);
						} else {
							csv.append(caracterSeparacion);
						}
					} else {
						if (valor != null) {
							csv.append("\"" + valor + "\"" + caracterSeparacion);
						} else {
							csv.append(caracterSeparacion);
						}
					}
					contador++;
				}
				guardarVariable(csv, medico.getMedico());
				guardarVariable(csv, periodo.getDescripcion());
				int cantAper = 0;
				int cantAperTotal = 0;
				for (String valor : medico.devolverPrimeraApertura(periodo)) {
					if (cantAper < columSegApert) {
						if (valor != null) {
							if (valor.contains(",") || valor.contains(".")) {
								guardarVariable(csv, valor);
							} else {
								csv.append("\"" + valor + "\"" + caracterSeparacion);
							}
						} else {
							csv.append(caracterSeparacion);
						}
					}
					cantAper++;
				}
				cantAperTotal = cantAper;
				for (int i = cantAperTotal; i < columSegApert; i++) {
					guardarVariable(csv, null);
					cantAper++;
				}
				cantAperTotal = cantAper;
				cantAper = 0;
				if (medico.devolverSegundaApertura(periodo) != null) {
					for (String valor : medico.devolverSegundaApertura(periodo)) {
						if (cantAper < columSegApert) {
							if (valor != null) {
								if (valor.contains(",") || valor.contains(".")) {
									guardarVariable(csv, valor);
								} else {
									csv.append("\"" + valor + "\"" + caracterSeparacion);
								}
							} else {
								csv.append(caracterSeparacion);
							}
						}
						cantAper++;
					}
				}
				cantAperTotal = cantAper;
				for (int i = cantAperTotal; i < columnaDomicilio - columSegApert; i++) {
					guardarVariable(csv, null);
				}
				if (isExpCdgLider) {
					guardarVariable(csv, medico.getCdgLider(), false);
				}
				if (medico.devolverDomicilio() != null) {
					if (emailAllowed) {
						csv.append(medico.getEmail1() + caracterSeparacion);
						csv.append(medico.getEmail2() + caracterSeparacion);
					}
					List<String> v = medico.devolverDomicilio();
					for (int i = 0; i < 4; i++) {
						guardarVariable(csv, v.get(i));
					}
				}
				posicionIni++;
				csv.newLine();
			}
		}
	}

	private void obtenerDomiciliosColum(String dom, DatosExportacionHorizontalColum datosExportacionHorizontalColum) {
		if (dom != null) {
			int numeroDomicilios = 0;
			String[] medicoDom = dom.trim().split(";");
			for (int j = 0; j < medicoDom.length; j++) {
				numeroDomicilios++;
				obtenerDomicilioColum(medicoDom[j], datosExportacionHorizontalColum);
			}
			if (numeroDomicilios > numeroMaximoDomiciliosEnExportacion) {
				numeroMaximoDomiciliosEnExportacion = numeroDomicilios;
			}
		}
	}

	private void obtenerDomicilioColum(String dom, DatosExportacionHorizontalColum Deh) {
		String[] medicoDomDatos = null;

		medicoDomDatos = dom.split("___");

		String[] localidadCalle = medicoDomDatos[1].trim().split("-");
		String domicilio = "";
		String localidad = null;
		if (localidadCalle.length > 1) {
			localidad = localidadCalle[0];
			for (int i = 1; i < localidadCalle.length; i++) {
				domicilio += localidadCalle[i] + " - ";
			}
			domicilio = domicilio.substring(0, domicilio.length() - 3);
		} else {
			if (localidadCalle.length > 0) {
				domicilio = localidadCalle[0];
			} else {
				domicilio = " ";
			}
			localidad = " ";
		}
		Deh.cargarDomicilio(domicilio);
		Deh.cargarDomicilio(medicoDomDatos[2].trim());
		Deh.cargarDomicilio(localidad);
		Deh.cargarDomicilio(medicoDomDatos[4].trim());
	}

	/**
	 * @author jangulo PXS-240
	 * 
	 *         Aqui imprime
	 * 
	 *         En reps hay determinados mercados que no tienen px entonces muestran
	 *         mercados donde deberian mostrar los productos
	 * 
	 * @param reporte
	 * @param csv
	 * @param rs
	 * @param maximoValor
	 * @param actual
	 * @param totalColumnas
	 * @param sonFloat
	 * @param muestraDir
	 * @param isExpCdgLider
	 * @throws LRSException
	 */
	private void imprimirPerfil(Reporte reporte, BufferedWriter csv, ResultSet rs, int maximaColumnaMetrica,
			int inicioColumnaAperturas, int totalColumnas, boolean[] sonFloat, boolean muestraDir,
			boolean isExpCdgLider, boolean emailAllowed) throws LRSException {
		
		System.out.println("HELALUBO -> IMPRIMIR PERFIL");
		try {
			guardarVariable(csv, rs.getString("descripcion"));
			guardarVariable(csv, rs.getString("matricula"), false);
			guardarVariable(csv, rs.getString("desc_esp1"));
			guardarVariable(csv, rs.getString("desc_esp2"));
			guardarVariable(csv, rs.getString("fvta"));
			guardarVariable(csv, rs.getString("desc_mercado"));
			for (int contador = 9; contador < maximaColumnaMetrica; contador++) {
				if (sonFloat[contador - 1]) {
					guardarVariable(csv, rs.getString(contador));
				} else {
					String valor = "";
					if (contador == 11 && rs.getString("desc_mercado").equals(rs.getString(11))) {
						valor = rs.getString(13);
					} else {
						valor = rs.getString(contador);
					}
					if (valor != null) {
						csv.append("\"" + valor + "\"" + caracterSeparacion);
					} else {
						csv.append(caracterSeparacion);
					}
				}
			}
			for (int contador = inicioColumnaAperturas; contador <= (totalColumnas); contador++) {
				if (sonFloat[contador - 1]) {
					guardarVariable(csv, rs.getString(contador));
				} else {
					String valor = rs.getString(contador);
					if (valor != null) {
						csv.append("\"" + valor + "\"" + caracterSeparacion);
					} else {
						csv.append(caracterSeparacion);
					}
				}
			}
			guardarVariable(csv, rs.getString("cdg_medico"), false);
			if (isExpCdgLider) {
				guardarVariable(csv, rs.getString("cdg_lider"), false);
			}
			if (muestraDir) {
				if (emailAllowed) {
					guardarVariable(csv, rs.getString("email1"), false);
					guardarVariable(csv, rs.getString("email2"), false);
				}
				obtenerDomicilios(rs.getString(totalColumnas + 1), csv);
			}
		} catch (Exception exception) {
			logger.logueaException(exception);
			logger.error(exception.getMessage() + " reporte:" + reporte.getCodigo());
		}
	}

	public String erroresFiltros(Reporte reporte) {
		String errores = "";

		ErroresFiltros erroresFiltros = reporte.getErroresFiltros();

		errores += errorFiltro(reporte, erroresFiltros.getGeografias(), "GEO");
		errores += errorFiltro(reporte, erroresFiltros.getEspecialidades(), "ESP");
		errores += errorFiltro(reporte, erroresFiltros.getLineas(), "LIN");
		errores += errorFiltro(reporte, erroresFiltros.getProductos(), "PRO");

		return errores;
	}

	@SuppressWarnings("rawtypes")
	private String errorFiltro(Reporte reporte, List lista, String tipo) {
		if (lista != null && !lista.isEmpty()) {
			return servicioFacade.getServicioLabel().getLabelNoHtml(reporte.getUsuario(), "ERR_F_" + tipo) + "\n";
		}
		return "";
	}

	private void obtenerDomicilios(String dom, BufferedWriter csv) throws IOException {
		int numeroDomicilios = 0;
		String[] medicoDom = dom.trim().split(";");
		for (int j = 0; j < medicoDom.length; j++) {
			numeroDomicilios++;
			obtenerDomicilio(medicoDom[j], csv);
		}
		if (numeroDomicilios > numeroMaximoDomiciliosEnExportacion) {
			numeroMaximoDomiciliosEnExportacion = numeroDomicilios;
		}
	}

	public void setearValoresExportacion(String caracterSeparacion, String caracterDecimal,
			String caracterSeparadorMil) {
		this.caracterSeparacion = caracterSeparacion;
		this.caracterDecimal = caracterDecimal;
		this.caracterSeparadorMil = caracterSeparadorMil;
	}

	/**
	 * M5681 @author jangulo issue about use split by "-"
	 * @param dom
	 * @param csv
	 * @throws IOException
	 */
	private void obtenerDomicilio(String dom, BufferedWriter csv) throws IOException {
		String[] medicoDomDatos = null;
		medicoDomDatos = dom.split("___");

		for (int i = 0; i < medicoDomDatos.length; i++)
			if ((medicoDomDatos[i] != null) && medicoDomDatos[i].toLowerCase().equals("null"))
				medicoDomDatos[i] = "";

		String[] localidadCalle = medicoDomDatos[1].trim().split("-");
		String domicilio = " ";
		String localidad = null;
		if (localidadCalle.length > 2) {
			localidad = localidadCalle[0];
			for (int counter = 1; counter < localidadCalle.length; counter++) {
				domicilio += localidadCalle[counter] + " ";
			}
		} else if (localidadCalle.length > 1) {
			domicilio = localidadCalle[1];
			localidad = localidadCalle[0];
		} else {
			if (localidadCalle.length > 0) {
				domicilio = localidadCalle[0];
			} else {
				domicilio = " ";
			}
			localidad = " ";
		}

		csv.append("\"" + domicilio + "\"" + caracterSeparacion);
		csv.append("\"" + medicoDomDatos[2] + "\"" + caracterSeparacion);
		csv.append("\"" + localidad + "\"" + caracterSeparacion);
		csv.append("\"" + medicoDomDatos[4] + "\"" + caracterSeparacion);
	}

}

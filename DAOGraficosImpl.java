package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import oracle.jdbc.OracleTypes;
import pcup.domain.charts.GraficoFactory;
import pcup.domain.charts.LRSChart;
import pcup.domain.charts.LRSChartCategories;
import pcup.domain.charts.LRSChartCategory;
import pcup.domain.charts.LRSChartDataSet;
import pcup.domain.charts.LRSChartSet;
import pcup.domain.charts.LRSChartTrendLine;
import pcup.domain.charts.LRSChartVTrendLine;
import pcup.domain.charts.PCupChartLine;
import pcup.domain.dao.DAOGraficos;
import pcup.domain.model.TipoConsultaGrafico;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.Reporte;
import plataforma.domain.model.Usuario;

public class DAOGraficosImpl extends DAOGenericoImpl<LRSChart, String> implements DAOGraficos{

	public DAOGraficosImpl(LRSDataBase lrsdb){
		this.lrsdb = lrsdb;
	}

	public LRSChart obtenerGrafico(Usuario auth, long cdgGrafico) throws LRSException, SQLException {
		return obtenerGrafico(auth, cdgGrafico,null);
	}
	
	public LRSChart obtenerGrafico(Usuario auth, long cdgGrafico, Reporte reporte) throws LRSException, SQLException {
	
		LRSChart out = null;
		lrsdb.executeOracleSP("PKG_SERVICIO_GRAFICOS.generarDatosGraficos("+Long.toString(auth.getId())+"," +
				""+Long.toString(cdgGrafico)+"," +
				""+(reporte == null ? "NULL" : reporte.getCodigoToString())+")");
		
		ResultSet rs = null;
		CallableStatement cstmnt = null;
		try {
			cstmnt = lrsdb.getCallableStatement("BEGIN :1 := PKG_SERVICIO_GRAFICOS.obtenerGrafico("+Long.toString(cdgGrafico)+"); END;");
			cstmnt.registerOutParameter(1,OracleTypes.CURSOR);
			cstmnt.execute();
			rs = (ResultSet)cstmnt.getObject(1);
			
			if(rs.next()){
				
				out = (LRSChart) GraficoFactory.getInstance(rs.getString("java_class"), reporte);
				
				out.setCodigo(rs.getLong("cdg_grafico"));
				out.setCaption(rs.getString("descripcion"));
				out.setXAxisName(rs.getString("descripcion_eje_x"));
				out.setYAxisName(rs.getString("descripcion_eje_y"));
	
	
				if (rs.getString("ver_nombres") != null){
					out.setShowLabels(rs.getInt("ver_nombres"));
					out.setShowNames(rs.getInt("ver_nombres"));
				}
	
				if (rs.getString("animacion") != null)
					out.setAnimation(rs.getInt("animacion"));
				
				if (rs.getString("minimo_valor_x") != null)
					out.setXAxisMinValue(rs.getDouble("minimo_valor_x"));
				if (rs.getString("maximo_valor_x") != null)
					out.setXAxisMaxValue(rs.getDouble("maximo_valor_x"));
				
				if (rs.getString("minimo_valor_y") != null)
					out.setYAxisMinValue(rs.getDouble("minimo_valor_y"));
				if (rs.getString("maximo_valor_y") != null)
					out.setYAxisMaxValue(rs.getDouble("maximo_valor_y"));
				
				if (rs.getString("escala_x") != null)
					out.setEscalaAutomaticaX(rs.getDouble("escala_x"));
											
				out.setLimitaXSegunDatos(rs.getInt("limita_x_datos") == 1);
				out.setLimitaYSegunDatos(rs.getInt("limita_y_datos") == 1);
				out.setMultiSeriesChart(rs.getInt("multi_series") == 1);			
			}
			//rs.close();
			//cstmnt.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
			if (cstmnt != null) { cstmnt.close(); }
		}
		
		obtenerSeries(out);
		obtenerTrendlines(out);
		
		return out;
	}
	
	private void obtenerTrendlines(LRSChart chart) throws SQLException, LRSException{
		ResultSet cuadrantes = null;
		CallableStatement cstmnt = null;
		
		try {
			cstmnt = lrsdb.getCallableStatement("BEGIN :1 := PKG_SERVICIO_GRAFICOS.obtenerGraficoCuadrantes("+Long.toString(chart.getCodigo())+"); END;");
			cstmnt.registerOutParameter(1,OracleTypes.CURSOR);
			cstmnt.execute();
			cuadrantes = (ResultSet)cstmnt.getObject(1);
			
			LRSChartTrendLine trendlines = new LRSChartTrendLine();
			LRSChartVTrendLine vTrendlines = new LRSChartVTrendLine();
			while(cuadrantes.next()){
				PCupChartLine line = new PCupChartLine();
				line.setDisplayValue(cuadrantes.getString("descripcion"));
				line.setColor(cuadrantes.getString("color"));
				
				if (cuadrantes.getString("valor_inicio") != null)
					line.setStartValue(cuadrantes.getDouble("valor_inicio"));
				if (cuadrantes.getString("valor_fin") != null)
					line.setEndValue(cuadrantes.getDouble("valor_fin"));
				if (cuadrantes.getString("zona") != null)
					line.setIsTrendZone(cuadrantes.getInt("zona"));
				if (cuadrantes.getString("alpha") != null)
					line.setAlpha(cuadrantes.getInt("alpha"));
				
				line.setEsLinea(cuadrantes.getInt("es_linea") == 1);
				if ("V".equals(cuadrantes.getString("tipo_cuadrante"))){
					line.setEsVertical(true);
					vTrendlines.addLine(line);
				}
				else if ("H".equals(cuadrantes.getString("tipo_cuadrante")))
					trendlines.addLine(line);				
			}
			//cuadrantes.close();
			//cstmnt.close();
			
			//Asigno los cuadrantes si es que exiten
			if (trendlines.size() > 0)
				chart.setTrendlines(trendlines);
			if (vTrendlines.size() > 0)
				chart.setVTrendlines(vTrendlines);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (cuadrantes != null) { cuadrantes.close(); }
			if (cstmnt != null) { cstmnt.close(); }
		}		
	}
	
	public List<LRSChartDataSet> obtenerSoloSeries(int cdgGrafico, String tipoVisita) throws SQLException, LRSException{
		ResultSet series = null;
		CallableStatement cstmnt = null;
		
		try {
			cstmnt = lrsdb.getCallableStatement("BEGIN :1 := PKG_SERVICIO_GRAFICOS.obtenerSoloSeries("+ cdgGrafico +",'" + tipoVisita + "'); END;");
			cstmnt.registerOutParameter(1,OracleTypes.CURSOR);
			cstmnt.execute();
			series = (ResultSet)cstmnt.getObject(1);
			List<LRSChartDataSet> dataSets = new LinkedList<LRSChartDataSet>();
			while(series.next()){
				LRSChartDataSet dataset = new LRSChartDataSet();
				dataset.setCdgGrafico(series.getLong("cdg_grafico"));
				dataset.setCdgSerie(series.getLong("cdg_serie"));
				dataset.setSeriesName(series.getString("descripcion"));
				dataset.setColor(series.getString("color"));

				dataSets.add(dataset);
			}		
			//series.close();
			//cstmnt.close();

			return dataSets;			
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (series != null) { series.close(); }
			if (cstmnt != null) { cstmnt.close(); }
		}
	}
	
	private void obtenerSeries(LRSChart chart) throws SQLException, LRSException{
		ResultSet series = null;
		CallableStatement cstmnt = null;
		
		try {
			cstmnt = lrsdb.getCallableStatement("BEGIN :1 := PKG_SERVICIO_GRAFICOS.obtenerGraficoSeries("+Long.toString(chart.getCodigo())+"); END;");
			cstmnt.registerOutParameter(1,OracleTypes.CURSOR);
			cstmnt.execute();
			series = (ResultSet)cstmnt.getObject(1);
			List<LRSChartDataSet> dataSets = new LinkedList<LRSChartDataSet>();
			LRSChartCategories categories = new LRSChartCategories();
			int nroSerie = 0;
			while(series.next()){
				LRSChartDataSet dataset = new LRSChartDataSet();
				dataset.setCdgGrafico(series.getLong("cdg_grafico"));
				dataset.setCdgSerie(series.getLong("cdg_serie"));
				dataset.setCantValores(series.getInt("cantidad_valores"));
				dataset.setSeriesName(series.getString("descripcion"));
				dataset.setColor(series.getString("color"));
				if (series.getString("incluir_nombre") != null)
					dataset.setIncludeInLegend(series.getInt("incluir_nombre"));
				
				/*if (chart.getCodigo() == 20)
					dataset.setShowValues(0);*/
				obtenerDatos(chart, dataset,nroSerie == 0 ? categories : null);
				dataSets.add(dataset);
				++nroSerie;
			}
			
			//series.close();
			//cstmnt.close();
			if (nroSerie > 1 || categories.isMuestraCategory() || chart.isMultiSeriesChart())
				chart.setCategories(categories);
			
			chart.setDataSets(dataSets);		
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (series != null) { series.close(); }
			if (cstmnt != null) { cstmnt.close(); }
		}
	}
	
	private void obtenerDatos(LRSChart chart, LRSChartDataSet dataset, LRSChartCategories categories) throws LRSException, SQLException{
		ResultSet datos = null;
		CallableStatement cstmnt = null;
		
		try {
			cstmnt = lrsdb.getCallableStatement("BEGIN :1 := PKG_SERVICIO_GRAFICOS.obtenerGraficoDatos("+Long.toString(dataset.getCdgGrafico())+","+Long.toString(dataset.getCdgSerie())+"); END;");
			cstmnt.registerOutParameter(1,OracleTypes.CURSOR);
			cstmnt.execute();
			datos = (ResultSet)cstmnt.getObject(1);
			int cnt = 0;
			
			while(datos.next()){			
				if (datos.getInt("category") == 1){
					if (categories != null){
						LRSChartCategory category = new LRSChartCategory();
						category.setLabel(datos.getString("descripcion"));
						category.setX(datos.getDouble("valor1"));
						category.setShowVerticalLine(1);
						categories.addCategory(category);
					}
				}
				else{
					
					LRSChartSet set = null;
					if (dataset.getCantValores() == 1){
						set = new LRSChartSet(datos.getString("descripcion"), datos.getDouble("valor1"));
						//set.setLabel(null);
						//dataset.setColor(LRSChartColor.getDefaultColor((int)dataset.getCdgSerie()));
					}
					else if (dataset.getCantValores() == 3){
						set = new LRSChartSet(datos.getString("descripcion"), datos.getDouble("valor1"), datos.getDouble("valor2"), datos.getDouble("valor3"));
						if (categories != null)
							categories.setMuestraCategory(true);
						//set.setColor(LRSChartColor.getDefaultColor(datos.getInt("item")));
					}
					
					set.setToolText(datos.getString("tooltext"));
					if (datos.getString("sliced") != null)
						set.setIsSliced(datos.getInt("sliced"));
					
					if (categories != null && dataset.getCantValores() == 1){
						LRSChartCategory category = new LRSChartCategory();
						category.setLabel(datos.getString("descripcion"));
						categories.addCategory(category);
						categories.setMuestraCategory(true);//MT:978-GIT:3838
					}
					
					if (chart.isLimitaYSegunDatos()){
						double datoCompara = dataset.getCantValores() == 1 ? set.getValue() : set.getY();
						double deltaZ = 0;//dataset.getCantValores() == 1 ? 0 : set.getZ();
						if (cnt == 0){
							chart.setYAxisMinValue(datoCompara);
							chart.setYAxisMaxValue(datoCompara);
						}
						else{
							chart.setYAxisMinValue(datoCompara-deltaZ < chart.getYAxisMinValue() ? datoCompara-deltaZ : chart.getYAxisMinValue());
							chart.setYAxisMaxValue(datoCompara+deltaZ > chart.getYAxisMaxValue() ? datoCompara+deltaZ : chart.getYAxisMaxValue());
						}
					}
					set.setLink(datos.getString("link"));
					if (datos.getString("color") != null)
						set.setColor(datos.getString("color"));
					dataset.addSet(set);				
				}
				++cnt;
			}
		
			//datos.close();
			//cstmnt.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (datos != null) { datos.close(); }
			if (cstmnt != null) { cstmnt.close(); }
		}	
	}

	public boolean exists(String id) {
		return false;
	}

	public LRSChart get(String id) {
		return null;
	}

	public List<LRSChart> getAll() {
		return null;
	}

	public void remove(String id) {
	}

	public LRSChart save(LRSChart object) {
		return null;
	}

	public List<TipoConsultaGrafico> obtenerTipoConsultasGraficos(Usuario auth,
			String cdgTipoConsulta) throws LRSException, SQLException {
		
		List<TipoConsultaGrafico> consultasGraficos = new LinkedList<TipoConsultaGrafico>();
		
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_SERVICIO_GRAFICOS.obtenerGraficoTipoConsulta('"+cdgTipoConsulta+"')");		
			
			while(rs.next()){
				
				TipoConsultaGrafico tipoGrafico = new TipoConsultaGrafico();
				tipoGrafico.setCdgGrafico(rs.getString("cdg_grafico"));
				tipoGrafico.setCdgTipoConsulta(cdgTipoConsulta);
				tipoGrafico.setJavaClass(rs.getString("java_class"));
				tipoGrafico.setEsDefault(rs.getString("defecto").equals("S"));
				tipoGrafico.setNombreGrafico(rs.getString("nombre_grafico"));
				consultasGraficos.add(tipoGrafico);
				
			}
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return consultasGraficos;
	}
									
}
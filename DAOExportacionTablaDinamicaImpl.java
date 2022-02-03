package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import pcup.domain.dao.DAOExportacionTablaDinamica;
import pcup.domain.model.ColumnaTablaDinamica;
import pcup.domain.model.TablaDinamica;
import plataforma.domain.dao.DAOLabel;
import plataforma.domain.dao.impl.DAOLabelImpl;
import plataforma.domain.model.Cabecera;
import plataforma.domain.model.Usuario;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOExportacionTablaDinamicaImpl implements DAOExportacionTablaDinamica {

	private LRSDataBase lrsdb;
	private DAOLabel daoLabel = null;
	
	public DAOExportacionTablaDinamicaImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
		daoLabel = new DAOLabelImpl(lrsdb);
	}
	
	public List<Cabecera> getCabeceras(Usuario auth) throws LRSException, SQLException
	{
		ResultSet rs = null;
		List<Cabecera> listadoCabecera = new LinkedList<Cabecera>();
		
		try {
			rs = lrsdb.getOracleSP("PKG_TABLA_DINAMICA.obtenerConsultas");
			
			while (rs.next())
			{
				TablaDinamica cabecera = cargarTablaDinamica(auth, rs);
				
				listadoCabecera.add(cabecera);
			}
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return listadoCabecera;
		
	}

	protected TablaDinamica cargarTablaDinamica(Usuario auth, ResultSet rs)
			throws SQLException {
		TablaDinamica cabecera = new TablaDinamica();
		cabecera.setCdgEtdConsulta(rs.getLong("cdg_etd_consulta"));
		cabecera.setCdgTipoConsulta(rs.getString("cdg_tipo_consulta"));
		cabecera.setDescConsulta(daoLabel.getLabel(auth, rs.getString("lbl_desc_consulta")));
		cabecera.setQueryDatos(rs.getString("query_datos"));
		cabecera.setQueryResultados(rs.getString("query_resultado"));
		return cabecera;
	}
	
	public List<ColumnaTablaDinamica> getColumnasDeTabla(String codigoTabla,Usuario auth) throws LRSException, SQLException
	{
		ResultSet rs = null;
		List<ColumnaTablaDinamica> listadoColumnas = new LinkedList<ColumnaTablaDinamica>();
		
		try {
			rs = lrsdb.getOracleSP("PKG_TABLA_DINAMICA.obtenerColumnasDeTabla('"+codigoTabla+"',"+auth.getId()+")");
			
			while (rs.next()) {
				ColumnaTablaDinamica columna = new ColumnaTablaDinamica();
				columna.setCdgEtdConsulta(rs.getLong("cdg_etd_consulta"));
				columna.setCdgColumna(rs.getLong("cdg_columna"));
				columna.setNombreColVista(daoLabel.getLabel(auth, rs.getString("nombre_col_vista")));
				columna.setNombreColSql(rs.getString("nombre_col_sql"));
				columna.setQueryAsociada(rs.getString("query_asociada"));
				columna.setNombreHojaTabla(rs.getString("nombre_tabla"));
				
				listadoColumnas.add(columna);
			}
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return listadoColumnas;
	}
	
	public List<Cabecera> obtenerQueries(Usuario auth, String parametroReporte) throws LRSException, SQLException
	{
		ResultSet rs = null;
		List<Cabecera> listadoCabecera = new LinkedList<Cabecera>();
		
		try {
			rs = lrsdb.getOracleSP("PKG_TABLA_DINAMICA.obtenerConsPorTipo('"+parametroReporte+"')");
			
			while (rs.next()) {
				TablaDinamica cabecera = cargarTablaDinamica(auth, rs);
				
				listadoCabecera.add(cabecera);
			}
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return listadoCabecera;
	}
	
	public ResultSet guardarColumna(long cdgColumna, long orden, long cdgReporte,long codigoCabeceraExportacion) throws LRSException, SQLException
	{
		return lrsdb.getOracleSP("PKG_TABLA_DINAMICA.guardarColumnasDeTabla("+cdgColumna+","+orden+","+cdgReporte+","+codigoCabeceraExportacion+")");
	}
	
	public void vaciarColumnasTemporales() throws LRSException
	{
		lrsdb.executeOracleSP("PKG_TABLA_DINAMICA.vaciarColumnasTemporales()");
	}
	
	public ResultSet ejecutarConsulta(String consulta, long codigoCabeceraExportacion, String cdgReporte) throws LRSException, SQLException
	{
		ResultSet rs = lrsdb.getOracleSP("PKG_TABLA_DINAMICA.ejecutarConsulta('"+consulta+"',"+codigoCabeceraExportacion+","+cdgReporte+")");
		//devuelvo la tabla de datos
		return rs;
		
	}
	
	public ResultSet ejecutarConsultaColumna(long cdgColumna) throws LRSException, SQLException
	{
		ResultSet rs = lrsdb.getOracleSP("PKG_TABLA_DINAMICA.ejecutarConsultaColumna('"+cdgColumna+"')");
		//devuelvo la tabla de datos
		return rs;
	}

	public long generarCabeceraExportacion(long cdgReporte, String consulta,
			boolean tablasAnexas, Usuario auth) throws LRSException,
			SQLException {
		
		long valorRespuesta = lrsdb.getOracleNumber("PKG_TABLA_DINAMICA.guardarCabeceraConsulta("+cdgReporte+", "+auth.getId()+", "+consulta+", '"+(tablasAnexas ? '1' : '0')+"')");
		return valorRespuesta;
	}

}

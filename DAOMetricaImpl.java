package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOMetrica;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.Metrica;
import plataforma.domain.model.Reporte;

public class DAOMetricaImpl extends DAOGenericoImpl<String, String>  implements DAOMetrica {

	protected LRSDataBase lrsdb = null;
	
	public DAOMetricaImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
	}
	
	public String obtenerOrdenDefaultSQL(String cdgMetrica) {
		String orden = obtenerOrdenDefault(cdgMetrica);
		return orden.equals("D")? "DESC" : "ASC";
	}

	public String obtenerOrdenDefault(String cdgMetrica) {
		
		try {
			return lrsdb.getOracleVarchar2("PKG_SERVICIO_METRICA.obtenerOrdenMetrica('"+cdgMetrica+"')");
		} catch (LRSException e) {
			super.logger.logException(e);
			return "D";
		}
	}

	public List<Metrica> obtenerMetricas(Reporte reporte) throws LRSException, SQLException
	{
		List<Metrica> metricas = new LinkedList<Metrica>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_SERVICIO_METRICA.obtenerMetricasReporte('"+reporte.getCodigo()+"')");
			while (rs.next())
			{
				Metrica metrica = new Metrica(rs.getString("cdg_metrica"));
				metrica.setPrefijo(rs.getString("prefijo"));
				metrica.setPosicion(rs.getInt("posicion"));
				metrica.setDescripcion(rs.getString("desc_metrica"));
				metrica.setMuestraGrowth(rs.getString("MUESTRA_GROWTH"));
				metrica.setCdgLabel(rs.getString("CDG_LABEL"));

				metricas.add(metrica);
			}
//			rs.close();
		} catch (SQLException ex) {
			
		} finally {
			//se estaba superando número máximo de cursores x quedar abiertos
			if (rs != null)
				rs.close(); 
		}
		
		return metricas; 
	}

	
	public boolean exists(String id) {
		return false;
	}

	public String get(String id) {
		return null;
	}

	public List<String> getAll() {
		return null;
	}

	public void remove(String id) {

	}

	public String save(String object) {
		return null;
	}

}

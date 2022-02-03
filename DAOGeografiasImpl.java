package pcup.domain.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;

import pcup.domain.dao.DAOGeografias;
import plataforma.domain.model.Geografia;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOGeografiasImpl extends plataforma.domain.dao.impl.DAOGeografiasImpl implements DAOGeografias {

	
	public DAOGeografiasImpl(LRSDataBase lrsdb){
		super(lrsdb);
	}
	
	public List<Geografia> geografiasHijasSeleccionadas(String tipoConsulta, long cdgGeografiaPropia,
			String listadoGeografiasSeleccionadas, String sistema, boolean sacarDeGtmp) throws LRSException, SQLException {
		
		List<Geografia> out = new LinkedList<Geografia>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("pkg_geografias.getGeoHijasSeleccionadas('"+tipoConsulta+"','"+ cdgGeografiaPropia+"','"+listadoGeografiasSeleccionadas+"',"+((sistema != null)? "'"+sistema+"'" : "null" ) +","+(sacarDeGtmp? "1" : "0")+")");
			int stmt = lrsdb.getLastStatementId();
			while(rs.next()){
				Geografia geo = new Geografia();
				geo.setCodigo(rs.getLong("CDG_GEOGRAFIA"));
				out.add(geo);
			}
			rs.close();
			lrsdb.closeStatement(stmt);
		} catch (SQLException ex) {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			throw ex;
		}	
		
		return out;
	}
	public Geografia pasarGeografia(String tipoConsulta, long cdgGeografiaPropia, String nombre) {
		Geografia ans = new Geografia();
		String[] arrAns;
		try {
			
			 String ngeo = lrsdb.getOracleVarchar2("PKG_COPIAR_GEO.PASAR_GEOGRAFIA("+cdgGeografiaPropia+",'"+ tipoConsulta+"', '"+nombre+"')");
			 if (ngeo!=null){
				 arrAns = ngeo.split(";");
				 ans.setDescripcion(arrAns[0]);
				 ans.setCodigo(arrAns[1]);
			 }
		} catch (LRSException e) {
			e.printStackTrace();
		}

		return ans;
	}
	public void  setGeoMod(long cdgGeografiaPropia, String stat) throws LRSException{
		lrsdb.executeOracleSP("PKG_COPIAR_GEO.SET_GEO_MOD("+cdgGeografiaPropia+",'"+stat+"')");
	}
	public String  getGeoMod(long cdgGeografiaPropia) throws LRSException{
		return lrsdb.getOracleVarchar2("PKG_COPIAR_GEO.GET_GEO_MOD("+cdgGeografiaPropia+")");
	}
	

}

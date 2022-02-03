package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import pcup.domain.dao.DAOTipoGeografias;
import plataforma.domain.model.TipoGeografia;
import plataforma.domain.model.Usuario;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOTipoGeografiasImpl implements DAOTipoGeografias {

	private LRSDataBase lrsdb;
//	private DAOSistema daosistema;
	
	public DAOTipoGeografiasImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
//		daosistema = new DAOSistemaImpl(lrsdb);
	}
	
	public List<TipoGeografia> getTipoGeografias (Usuario user) throws LRSException, SQLException
	{
		List<TipoGeografia> out = new LinkedList<TipoGeografia>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("pkg_tipo_geografia.getTipoGeografias('"+user.getPais()+"','"+ user.getLaboratorio() +"',"+user.getId()+")");
			int stmt = lrsdb.getLastStatementId();
			while(rs.next()){
				TipoGeografia geo = new TipoGeografia(rs.getLong("CDG_GEO_PROPIA"));
				geo.setDescripcion(rs.getString("DESC_GEO_PROPIA"));
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
}

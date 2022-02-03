package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOPais;
import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Pais;


public class DAOPaisImpl implements DAOPais{
	
	LRSDataBase lrsdb=null;
	
	public DAOPaisImpl(LRSDataBase lrsdb){
		this.lrsdb=lrsdb;
	}
	
	public List<Laboratorio> obtenerListaLaboratorios(Pais pais)
			throws LRSException, SQLException {
		
		List<Laboratorio> out = new LinkedList<Laboratorio>();
		Laboratorio lab;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_SERVICIO_PAIS.obtenerListaLabs(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, pais.getPais());
			
			cstmt.execute();
			
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				lab = crearLaboratorioDeResultSet(rs);
				out.add(lab);
			}

			/*rs.close();
			cstmt.close();*/
		} catch(SQLException e) {
				throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();		
		}
		
		return out;
	}
	
	private Laboratorio crearLaboratorioDeResultSet(ResultSet rs) throws SQLException {
		return new Laboratorio(rs.getLong("cdg_laboratorio"), rs.getString("desc_laboratorio"));
	}
	

}

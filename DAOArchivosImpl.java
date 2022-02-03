/**
 * 
 */
package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOArchivos;

/**
 * @author devel
 *
 */
public class DAOArchivosImpl implements DAOArchivos {



	private LRSDataBase lrsdb;
	
	public DAOArchivosImpl(LRSDataBase lrsdb){
		this.lrsdb = lrsdb;
	}
	
	public List<String> exportacionesCaducas() throws SQLException {
		List<String> nombreArchivos = new ArrayList<String>();
		ResultSet rs = null;
		CallableStatement cstmnt = null;
		try {
			cstmnt = lrsdb.getCallableStatement("BEGIN :1 := PKG_REPORTES_BATCH.getArchivosABorrar(); END;");
			cstmnt.registerOutParameter(1,OracleTypes.CURSOR);
			cstmnt.execute();
			rs = (ResultSet)cstmnt.getObject(1);

			while(rs.next()){
				nombreArchivos.add(rs.getString("nombre_result"));
			}
			//rs.close();
			//cstmnt.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
			if (cstmnt != null) { cstmnt.close(); }
		}
		
		return nombreArchivos;
	}

	public void marcarComoBorrados(List<String> nombres) throws SQLException, LRSException {
		if ( nombres != null && nombres.size() > 0 ){
			StringBuffer sb = new StringBuffer();
			for( String nombre : nombres ) {
				sb.append(nombre + ";");
			}
			CallableStatement cstmnt = null;
			try {
				cstmnt = lrsdb.getCallableStatement("BEGIN :1 := pkg_reportes_batch.borrarExportacionesSinArchivo('" + sb.toString() + "'); END;");
				cstmnt.registerOutParameter(1,OracleTypes.VARCHAR);
				cstmnt.execute();
				//cstmnt.close();
			} catch (SQLException ex) {
				throw ex;
			} finally {
				if (cstmnt != null) { cstmnt.close(); }
			}
			
		}
	}
	
}

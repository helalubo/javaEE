package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAODefPropias;
import plataforma.domain.model.LogDefPropias;
import plataforma.domain.model.collections.LogsDefPropias;

public class DAODefPropiasImpl implements DAODefPropias{
	
	private LRSDataBase lrsdb;

	public DAODefPropiasImpl(LRSDataBase lrsdb) {
		this.lrsdb = lrsdb;
	}

	public LogsDefPropias getLog() {
		LogsDefPropias logsdp = new LogsDefPropias();
		ResultSet rs = null;
		try
		{
			rs = this.lrsdb.getOracleSP("PKG_DEF_PROPIAS.get_log_def_propias");
		
			while(rs.next()){
				
				LogDefPropias ldp = new LogDefPropias();
				
				ldp.setCdgDef(rs.getString("cdg_def"));
				ldp.setDescDef(rs.getString("desc_def"));
				ldp.setCdgDefCopia(rs.getString("cdg_def_copia"));
				ldp.setDescDefCopia(rs.getString("desc_def_copia"));
				ldp.setEstado(rs.getString("estado"));
				ldp.setMensaje(rs.getString("mensaje"));
				
				logsdp.add(ldp);
				
				if(logsdp.getTipo() == null){
					
					logsdp.setCdgLabDestino(rs.getString("cdg_lab_destino"));
					logsdp.setCdgLabOrigen(rs.getString("cdg_lab_origen"));
					logsdp.setTipo(rs.getString("tipo"));
					
				}
				
			}
	
			//rs.close();
		} catch (SQLException ex) {
			
		} catch (LRSException e) {
			
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return logsdp;
	}
	
}

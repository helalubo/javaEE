package pcup.domain.dao.impl;

import java.sql.SQLException;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOSesion;

public class DAOSesionImpl implements DAOSesion {

	
	private LRSDataBase lrsdb;
	
	public DAOSesionImpl(LRSDataBase lrsdb){
		this.lrsdb = lrsdb;
	}
	
	public String getSesionesCaducas() throws LRSException, SQLException
	{
		return lrsdb.getOracleVarchar2("PKG_SESION.obtener_sessiones_caducas");
	}
	
	public void limpiarEjecucionReportes() throws LRSException, SQLException
	{
		lrsdb.executeOracleSP("PKG_SESION.limpiar_ejecucion_reportes");
	}
}

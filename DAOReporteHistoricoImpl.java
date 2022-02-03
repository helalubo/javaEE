package pcup.domain.dao.impl;

import pcup.domain.dao.DAOReporteHistorico;
import plataforma.domain.model.Reporte;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOReporteHistoricoImpl implements DAOReporteHistorico {

	private LRSDataBase lrsdb = null;
	
	public DAOReporteHistoricoImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
	}
	
	public void guardarParametroReporte(Reporte rep, String codigo, String value) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.guardarParametro("+rep.getCodigo()+",'"+codigo+"','"+value+"')");
	}

	public String obtenerParametroReporte(Reporte rep, String codigo) throws LRSException {
		return lrsdb.getOracleVarchar2("PKG_REPORTES_BATCH.obtenerParametros("+rep.getCodigo()+",'"+codigo+"')");
	}


	public void borrarParametroReporte(Reporte reporte, String codigo) throws LRSException 
	{
		lrsdb.executeOracleSP("PKG_REPORTES_BATCH.borrarParametro("+reporte.getCodigo()+",'"+codigo+"')");
	}

	public void renombrarReportes(String lista) throws LRSException {
		lrsdb.executeOracleSP("PKG_REPORTE_HISTORICO.renombrar_reportes('"+lista+"')");
	}
}

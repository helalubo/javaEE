package pcup.domain.dao.impl;

import plataforma.domain.model.Usuario;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOServicioSegmentacionImpl implements
		pcup.domain.dao.DAOServicioSegmentacion {

	private LRSDataBase lrsdb;
	
	public DAOServicioSegmentacionImpl(LRSDataBase lrsdb)
	{
		this.lrsdb = lrsdb;
	}
	
	public void cargarGeografiasUsuario(Usuario auth) throws LRSException {
	
		lrsdb.executeOracleSP("PKG_SEGMENTACION.cargarGeografiaUsuario("+auth.getId()+")");
	}

}

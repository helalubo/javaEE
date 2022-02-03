package pcup.domain.dao.impl;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOErrores;

public class DAOErroresImpl implements DAOErrores {

	private LRSDataBase lrsdb;

	public DAOErroresImpl(LRSDataBase lrsdb) {
		this.lrsdb = lrsdb;
	}

	public void logErrorJava(Long cdgReporte, String tipoErrorJava,
			String message, String stack, String cause, int codigo) throws LRSException {
		lrsdb.executeOracleSP("PKG_ERRORES_AUDIT.log_error_java("+ (cdgReporte != null ? cdgReporte : "NULL") + ", '" +
                         tipoErrorJava + "', '" +
                         message + "', '" +
                         stack + "', '" +
                         cause + "', '" +
                         codigo + "')");
	}
	
}

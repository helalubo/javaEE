package pcup.domain.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

public class DAOParametrosImpl {
	
	private LRSDataBase lrsdb;
	
	public DAOParametrosImpl(LRSDataBase lrsdb)	{
		this.lrsdb = lrsdb;
	}
	
	public Map<String, String> getGlobalParametrosList(List<String> listaSolicitada) throws LRSException {
		Map<String, String> parametros = new HashMap<String, String>();
		for (String parametroSolicitado : listaSolicitada) {
			parametros.put(parametroSolicitado,
					lrsdb.getOracleVarchar2("LIBPCUP_os.obt_parametro_global('" + parametroSolicitado + "')"));
		}
		return parametros;
	}
}

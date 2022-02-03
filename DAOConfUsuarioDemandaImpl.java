package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.learsoft.database.LRSDataBase;
import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOConfUsuarioDemanda;
import pcup.domain.model.collections.ProductoEmpaque;
import plataforma.util.PlataformaLogger;

public class DAOConfUsuarioDemandaImpl implements DAOConfUsuarioDemanda {

	private LRSDataBase lrsDataBase = null;
	private PlataformaLogger logger;

	public DAOConfUsuarioDemandaImpl(LRSDataBase lrsDataBase) {
		this.lrsDataBase = lrsDataBase;
		logger = PlataformaLogger.obtenerLoggerDefault();
	}

	@Override
	public Boolean saveMercadoEmpaques(List<ProductoEmpaque> listaProductos, String mercado, String esquema) {
		CallableStatement callableStatement = null;
		try {
			callableStatement = lrsDataBase
					.getCallableStatement("BEGIN PKG_CONF_OFFLINE.deleteMercadoEmpaques(:1,:2); END;");
			callableStatement.setLong(1, Long.valueOf(mercado));
			callableStatement.setInt(2, Integer.valueOf(esquema));
			callableStatement.execute();
			callableStatement.close();
			callableStatement = lrsDataBase
					.getCallableStatement("BEGIN PKG_CONF_OFFLINE.saveMercadoEmpaques(:1,:2,:3); END;");
			for (ProductoEmpaque producto : listaProductos) {
				callableStatement.setLong(1, producto.getCodigoToLong());
				callableStatement.setLong(2, Long.valueOf(mercado));
				callableStatement.setInt(3, Integer.valueOf(esquema));
				callableStatement.execute();
			}			callableStatement.close();
			return true;
		} catch (SQLException exception) {
			logger.error(exception.getMessage());
			return false;
		}
	}

	@Override
	public Boolean findMercadoEmpaques(String mercado, String esquema) {
		CallableStatement callableStatement = null;
		Integer resultado;
		try {
			callableStatement = lrsDataBase
					.getCallableStatement("BEGIN :1 := PKG_CONF_OFFLINE.findMercadoEmpaques(:2,:3); END;");
			callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
			callableStatement.setLong(2, Long.valueOf(mercado));
			callableStatement.setInt(3, Integer.valueOf(esquema));
			callableStatement.execute();
			resultado = (Integer) callableStatement.getObject(1);
			callableStatement.close();
			return resultado == 1;
		} catch (SQLException exception) {
			logger.error(exception.getMessage());
			return false;
		}
	}

	@Override
	public List<String> getMercadoEmpaques() {
		CallableStatement callableStatement = null;
		List<String> listado = new ArrayList<String>();
		try {
			callableStatement = lrsDataBase
					.getCallableStatement("BEGIN :1 := PKG_CONF_OFFLINE.getMercadoEmpaques; END;");
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.execute();
			ResultSet resultSet = (ResultSet)callableStatement.getObject(1);
			while (resultSet.next()) {
				listado.add(resultSet.getInt(1) + resultSet.getString(2));
			}
			resultSet.close();
			callableStatement.close();
		} catch (SQLException exception) {
			logger.error(exception.getMessage());
		}
		return listado;
	}

}

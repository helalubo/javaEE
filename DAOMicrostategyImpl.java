package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import com.learsoft.database.LRSDataBase;
import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOMicrostategy;
import plataforma.util.PlataformaLogger;

/**
 * Clase para conectar con Microstategy
 * 
 * @author jangulo
 *
 */
public class DAOMicrostategyImpl implements DAOMicrostategy {

	private LRSDataBase lrsDataBase = null;
	private PlataformaLogger logger;

	/**
	 * Asigna conexión
	 * 
	 * @param lrsDataBase
	 */
	public DAOMicrostategyImpl(LRSDataBase lrsDataBase) {
		this.lrsDataBase = lrsDataBase;
		logger = PlataformaLogger.obtenerLoggerDefault();
	}

	/**
	 * Consulta si un laboratorio posee el recurso Microstategy
	 */
	@Override
	public Boolean isLaboratoryMicrostategy(String pais, Integer laboratorio) {
		CallableStatement callableStatement = null;
		Integer respuesta = 0;
		try {
			callableStatement = lrsDataBase
					.getCallableStatement("BEGIN :1 := pkg_offline_mstr.is_microstrategy_lab(:2,:3); END;");
			callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
			callableStatement.setString(2, pais);
			callableStatement.setInt(3, laboratorio);
			callableStatement.execute();
			respuesta = callableStatement.getInt(1);
			callableStatement.close();
			return respuesta == 1;
		} catch (SQLException exception) {
			logger.error(exception.getMessage());
			return false;
		}
	}

	@Override
	public Integer exeUserMicrostategy(String loginUser, String accion) {
		CallableStatement callableStatement = null;
		Integer respuesta = 0;
		try {
			callableStatement = lrsDataBase
					.getCallableStatement("BEGIN :1 := pkg_offline_mstr.exe_microstrategy_user(:2, :3); END;");
			callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
			callableStatement.setString(2, loginUser);
			callableStatement.setString(3, accion);
			callableStatement.execute();
			respuesta = callableStatement.getInt(1);
			callableStatement.close();
			return respuesta;
		} catch (SQLException exception) {
			logger.error(exception.getMessage());
			return 2;
		}
	}

	@Override
	public Integer exeSyncMicrostategy(Integer laboratorio, String pais, String loginUser) {
		CallableStatement callableStatement = null;
		Integer respuesta = 0;
		try {
			logger.ejecuto("exeSyncMicrostategy :: laboratorio = "+ laboratorio + " , pais = " + pais + "User = " + loginUser);
			callableStatement = lrsDataBase
					.getCallableStatement("BEGIN :1 := pkg_offline_mstr.sp_actualiza_usuarios_mstr(:2, :3, :4); END;");
			callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
			callableStatement.setInt(2, laboratorio);
			callableStatement.setString(3, pais);
			callableStatement.setString(4, loginUser);
			callableStatement.execute();
			respuesta = callableStatement.getInt(1);
			callableStatement.close();
		} catch (SQLException exception) {
			logger.error(exception.getMessage());
			respuesta = 9;
		}
		return respuesta;
	}
}

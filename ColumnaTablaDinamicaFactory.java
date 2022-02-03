package pcup.domain.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import pcup.domain.service.ServicioExportacionTablaDinamica;
import plataforma.domain.model.Usuario;
import plataforma.domain.service.ServicioLabel;

import com.learsoft.exceptions.LRSException;

public class ColumnaTablaDinamicaFactory {

	private static ColumnaTablaDinamicaFactory instance = new ColumnaTablaDinamicaFactory();
	
	private ColumnaTablaDinamicaFactory()
	{
		
	}
	
	public static ColumnaTablaDinamicaFactory getInstance()
	{
		return instance;
	}
	
	public ColumnaTablaDinamica crearColumnaTablaDinamica(JSONObject objetoColumna, long cdgReporte, ServicioExportacionTablaDinamica servicio, ServicioLabel servicioLabel, Usuario auth, long codigoCabeceraExportacion) throws JSONException, LRSException, SQLException
	{
		ResultSet rs = servicio.guardarColumna(objetoColumna.getLong("id"),objetoColumna.getLong("orden"),cdgReporte,codigoCabeceraExportacion);
		rs.next();
		ColumnaTablaDinamica columna = null;
		
		if ("S".equals(rs.getString("tipo_columna")))
		{
			columna =  new ColumnaTablaDinamicaConjunto();
		}
		else
		{
			columna =  new ColumnaTablaDinamica();
		}
		
		columna.cargarColumna(objetoColumna, servicio, servicioLabel, auth, rs);
		rs.close();
		return columna;
		
	}
}

package pcup.domain.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import pcup.domain.service.ServicioExportacionTablaDinamica;
import plataforma.domain.model.Usuario;
import plataforma.domain.service.ServicioLabel;

import com.learsoft.exceptions.LRSException;
import com.learsoft.jxl.LRSXlsPOI;

public class ColumnaTablaDinamicaConjunto extends ColumnaTablaDinamica {

	private int cantidadElementosMaximo;
	
	public void cargarColumna(JSONObject objetoColumna, ServicioExportacionTablaDinamica servicio, ServicioLabel servicioLabel, Usuario auth, ResultSet rs) throws JSONException, LRSException, SQLException {
		
		super.cargarColumnaInterna(objetoColumna, servicio, servicioLabel, auth, rs);
		cantidadElementosMaximo = rs.getInt("cantidad_elementos_maximo");
		rs.close();
	}
	
	public void imprimirHeader(LRSXlsPOI xls) {
		for (int columna = 1; columna <= cantidadElementosMaximo; columna++)
		xls.tableData(this.getNombreColVista() + " " + columna, "left", "celdaTablaDinamica", null, null);		
	}
	
	public void imprimirDato(LRSXlsPOI xls, ResultSet rs) throws SQLException {
		
		String dato = rs.getString(this.getNombreColSql());
		
		int cantidadElementos = 0;
		if (dato != null)
		{
			String[] unidades = dato.split(";");
			
			cantidadElementos = unidades.length;
			for (int columna = 0; columna < cantidadElementos; columna++)
			{
				xls.tableData(unidades[columna], "celdaTablaDinamica", null, null, null);
			}
		}
		for (int columna = cantidadElementos; columna < cantidadElementosMaximo; columna++)
		{
			xls.tableData("", "celdaTablaDinamica", null, null, null);
		}
	}
}

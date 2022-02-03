package pcup.domain.model;

import plataforma.domain.model.Reporte;

public class ReporteExportacion extends Reporte{
	
	private String tipoExportacion = null;

	public void setTipoExportacion(String tipoExportacion) {
		this.tipoExportacion = tipoExportacion;
	}

	public String getTipoExportacion() {
		return tipoExportacion;
	}
	

}

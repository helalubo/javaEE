package pcup.domain.model;

public class TipoConsultaGrafico {

	private String cdgGrafico=null;
	private String cdgTipoConsulta;
	private String javaClass;
	private String esDefault;
	private String nombreGrafico;
	
	/***
	 * Constructor que carga el codigo del tipo de consulta
	 * @param cdgTipoConsulta
	 */
	public TipoConsultaGrafico(String cdgTipoConsulta) {
		super();
		this.setCdgTipoConsulta(cdgTipoConsulta);
	}
	public TipoConsultaGrafico() {
		super();
	}
	
	public void setCdgTipoConsulta(String cdgTipoConsulta) 
	{
		this.cdgTipoConsulta = cdgTipoConsulta;
	}
	public String getCdgTipoConsulta() 
	{
		return cdgTipoConsulta;
	}
	
	public void setJavaClass(String javaClass) 
	{
		this.javaClass = javaClass;
	}
	public String getJavaClass() 
	{
		return javaClass;
	}
	
	public boolean getEsDefault()
	{
		return (esDefault.equals("S"));
	}
	
	public void setEsDefault(boolean varEsDefault)
	{
		if (varEsDefault)
		{
			esDefault = "S";
		}
		else
		{
			esDefault = "N";
		}
	}
	
	public String getNombreGrafico() 
	{
		return nombreGrafico;
	}
	
	public void setNombreGrafico(String nombre) 
	{
		nombreGrafico = nombre;
	}
	public void setCdgGrafico(String cdgGrafico) {
		this.cdgGrafico = cdgGrafico;
	}
	public String getCdgGrafico() {
		return cdgGrafico;
	}

}

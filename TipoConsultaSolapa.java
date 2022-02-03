package pcup.domain.model;

public class TipoConsultaSolapa {

	private String cdgTipoConsulta;
	private int cdgSolapa;
	private int orden;
	private Integer minimo;
	private Integer maximo;
	private String idSolapaHtml;
	private String numeroSolapaHtml;
	private String nombreArregloDatos;
	private String cdgLabel;
	
	public String getIdSolapaHtml()
	{
		return idSolapaHtml;
	}
	
	public void setIdSolapaHtml(String cdgTipoCons)
	{
		idSolapaHtml = cdgTipoCons;
	}

	public String getLabel()
	{
		return cdgLabel;
	}
	
	public void setLabel(String cdgTipoCons)
	{
		cdgLabel = cdgTipoCons;
	}

	public String getNumeroSolapaHtml()
	{
		return numeroSolapaHtml;
	}
	
	public void setNumeroSolapaHtml(String cdgTipoCons)
	{
		numeroSolapaHtml = cdgTipoCons;
	}
	
	public String getNombreArregloDatos()
	{
		return nombreArregloDatos;
	}
	
	public void setNombreArregloDatos(String cdgTipoCons)
	{
		nombreArregloDatos = cdgTipoCons;
	}
	
	public String getCdgTipoConsulta()
	{
		return cdgTipoConsulta;
	}
	
	public void setCdgTipoConsulta(String cdgTipoCons)
	{
		cdgTipoConsulta = cdgTipoCons;
	}
	
	public int getCdgSolapa()
	{
		return cdgSolapa;
	}
	
	public void setCdgSolapa(int cdgSol)
	{
		cdgSolapa = cdgSol;
	}
	
	public int getOrden()
	{
		return orden;
	}
	
	public void setOrden(int ord)
	{
		orden = ord;
	}
	
	public Integer getMinimo()
	{
		return minimo;
	}
	
	public void setMinimo(Integer min)
	{
		minimo = min;
	}
	
	public Integer getMaximo()
	{
		return maximo;
	}
	
	public void setMaximo(Integer max)
	{
		maximo = max;
	}
}

package pcup.domain.model;

import plataforma.domain.model.Pais;
import plataforma.domain.model.Servicio;

public class TipoProducto {

	private Pais _pais;
	private Servicio _servicio;
	private String _tipoProducto;
	private String _descripcion; 
	
	public String getDescripcion()
	{
		return _descripcion;
	}
	
	public void setDescripcion(String descripcion)
	{
		_descripcion = descripcion;
	}
	
	public String getTipoProducto()
	{
		return _tipoProducto;
	}
	
	public void setTipoProducto(String tipoProducto)
	{
		_tipoProducto = tipoProducto;
	}
	
	public Servicio getServicio()
	{
		return _servicio;
	}
	
	public long getCdgServicio()
	{
		return _servicio.getCodigo();
	}
	
	public void setServicio(long cdgServicio)
	{
		if (_servicio == null)
		{
			_servicio = new Servicio(cdgServicio);
		}
		else
		{
			_servicio.setCodigo(cdgServicio);
		}
	}
	
	public Pais getPais()
	{
		return _pais; 
	}
	
	public String getCdgPais()
	{
		return _pais.getPais();
	}
	
	public void setCdgPais(long cdgPais)
	{
		if (_pais == null)
		{
			_pais = new Pais(Long.toString(cdgPais));
		}
		else
		{
			_pais.setPais(Long.toString(cdgPais));
		}
	}
	
	public void setCdgPais(String cdgPais)
	{
		if (_pais == null)
		{
			_pais = new Pais(cdgPais);
		}
		else
		{
			_pais.setPais(cdgPais);
		}
	}
	
	public String getEsquema()
	{
		return _pais.getSchema();
	}
	
	public void setEsquema(String esquema)
	{
		if (_pais == null)
		{
			_pais = new Pais(null);
			_pais.setSchema(esquema);
		}
		else
		{
			_pais.setSchema(esquema);
		}
	}
}

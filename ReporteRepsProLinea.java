package pcup.domain.model;

import java.util.HashMap;

import plataforma.domain.model.Producto;
import plataforma.domain.model.Periodo;

public class ReporteRepsProLinea {
	
	private Producto producto;
	private HashMap<Periodo,String> hashPer;
	private String total;
	
	public ReporteRepsProLinea(Producto prod, Periodo per, String valor)
	{
		this.producto = prod;
		this.hashPer = new HashMap<Periodo, String>();
		this.put(per, valor);
	}
	
	public void put(Periodo per, String valor)
	{
		this.hashPer.put(per, valor);
	}
	
	public Producto getProducto()
	{
		return this.producto;
	}
	
	public String getValor(Periodo per)
	{
		return this.hashPer.get(per);
	}
	
	public String getTotal()
	{
		return this.total;
	}
	
	public void setTotal(String total)
	{
		this.total = total;
	}
	
	
}

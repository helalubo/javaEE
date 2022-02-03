package pcup.domain.model;

import java.util.HashMap;

import plataforma.domain.model.Mercado;
import plataforma.domain.model.Periodo;

public class ReporteRepsProLineaMercado {
	
	private Mercado mercado;
	private HashMap<Periodo,String> hashPer;
	private String total;
	
	public ReporteRepsProLineaMercado(Mercado m, Periodo per, String valor)
	{
		this.mercado = m;
		this.hashPer = new HashMap<Periodo, String>();
		this.put(per, valor);
	}
	
	public void put(Periodo per, String valor)
	{
		this.hashPer.put(per, valor);
	}
	
	public Mercado getMercado()
	{
		return this.mercado;
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

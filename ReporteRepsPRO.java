package pcup.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import plataforma.domain.model.Mercado;
import plataforma.domain.model.Metrica;
import plataforma.domain.model.Periodo;
import plataforma.domain.model.Producto;

public class ReporteRepsPRO {
	
	private HashMap<Metrica,List<ReporteRepsProLinea>> hashProducto;
	private HashMap<Metrica,List<ReporteRepsProLineaMercado>> hashMercado;
	
	public ReporteRepsPRO()
	{
		hashProducto = new HashMap<Metrica, List<ReporteRepsProLinea>>();
		hashMercado = new HashMap<Metrica, List<ReporteRepsProLineaMercado>>();
	}
	
	public List<ReporteRepsProLinea> getLineas(Metrica m)
	{
		return hashProducto.get(m);
	}
	
	public List<ReporteRepsProLineaMercado> getLineasMercado(Metrica m)
	{
		return hashMercado.get(m);
	}
	
	
	public void put(Metrica m, Producto prod, Periodo per, String valor, String total)
	{
		if(hashProducto.containsKey(m))
		{
			List<ReporteRepsProLinea> listaLineas = hashProducto.get(m);
			boolean encontrado = false;
			for(ReporteRepsProLinea linea:listaLineas)
			{
				if(prod.equals(linea.getProducto()))
				{
					linea.put(per, valor);
					encontrado = true;
					break;
				}
			}
			if(!encontrado)
			{
				ReporteRepsProLinea linea = new ReporteRepsProLinea(prod,per,valor);
				linea.setTotal(total);
				listaLineas.add(linea);
			}
			
		}
		else
		{
			ArrayList<ReporteRepsProLinea> lista = new ArrayList<ReporteRepsProLinea>();
			ReporteRepsProLinea linea = new ReporteRepsProLinea(prod,per,valor);
			linea.setTotal(total);
			lista.add(linea);
			hashProducto.put(m, lista);
		}
	}
	
	public void put(Metrica m, Mercado mer, Periodo per, String valor, String total)
	{
		if(hashMercado.containsKey(m))
		{
			List<ReporteRepsProLineaMercado> listaLineas = hashMercado.get(m);
			boolean encontrado = false;
			for(ReporteRepsProLineaMercado linea:listaLineas)
			{
				if(mer.equals(linea.getMercado()))
				{
					linea.put(per, valor);
					encontrado = true;
					break;
				}
			}
			if(!encontrado)
			{
				ReporteRepsProLineaMercado linea = new ReporteRepsProLineaMercado(mer,per,valor);
				linea.setTotal(total);
				listaLineas.add(linea);
			}
			
		}
		else
		{
			ArrayList<ReporteRepsProLineaMercado> lista = new ArrayList<ReporteRepsProLineaMercado>();
			ReporteRepsProLineaMercado linea = new ReporteRepsProLineaMercado(mer,per,valor);
			linea.setTotal(total);
			lista.add(linea);
			hashMercado.put(m, lista);
		}
	}
	
	public boolean isVacio()
	{
		return hashProducto.isEmpty();
	}
	
	public int getNumeroLineas()
	{
		int resultado = 0;
		for(Metrica m: hashProducto.keySet())
		{
			List<ReporteRepsProLinea> listaLineas = hashProducto.get(m);
			resultado+= listaLineas.size();
		}
		return resultado;
	}
	

}

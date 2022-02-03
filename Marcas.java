package pcup.domain.model;

import java.util.List;

public class Marcas {
	int cantidad;
	List<ItemLista> marcas;
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public List<ItemLista> getMarcas() {
		return marcas;
	}
	public void setMarcas(List<ItemLista> marcas) {
		this.marcas = marcas;
	}

}

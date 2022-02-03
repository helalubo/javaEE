package pcup.domain.model;

import java.util.LinkedList;
import java.util.List;

import plataforma.domain.model.Periodo;
import plataforma.domain.model.Producto;
import plataforma.domain.model.Raiz;
import plataforma.domain.model.Reporte;



public class ReporteReps{

	private Reporte reporte = null;
	private List<Periodo> periodos = null;
	private List<ReporteRepsTitulo> titulo = null;
	private List<ReporteRepsLinea> lineas = null;
	private List<Producto> productos = null;
	private Raiz raiz = null;
	private String order = null;
	private String orderType = null;
	private int cantTitulos = 0;
	
	public void preparar(){
		for(ReporteRepsTitulo tit:titulo)
			tit.setReporte(reporte);
	}
	
	public boolean estaOrdenadoPor(String cmp){
		return this.order != null && cmp != null && this.order.toUpperCase().equals(cmp.toUpperCase());
	}
	
	public boolean esDelTipoOrden(String cmp){
		return this.orderType != null && cmp != null && this.orderType.toUpperCase().equals(cmp.toUpperCase());
	}
	
	
	
	/**
	 * @return the reporte
	 */
	public Reporte getReporte() {
		return reporte;
	}

	/**
	 * @param reporte the reporte to set
	 */
	public void setReporte(Reporte reporte) {
		this.reporte = reporte;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void agregarLinea(ReporteRepsLinea linea){
		
		if (this.lineas == null){
			this.lineas = new LinkedList<ReporteRepsLinea>();
		}
		
		this.lineas.add(linea);
	}
	
	public int getTitulosSize(){
		int size = 0;
		for (ReporteRepsTitulo tit: titulo)
			size += tit.getSubtitulosSize();
		return size;
	}
	
	public int getTitulosSize(Periodo periodo){
		int size = 0;
		for (ReporteRepsTitulo tit: titulo)
			if (tit.esDelPeriodo(periodo))
				size += tit.getSubtitulosSize();
		return size;
	}
	
	
	/**
	 * @return the productos
	 */
	public List<Producto> getProductos() {
		return productos;
	}
	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	/**
	 * @return the lineas
	 */
	public List<ReporteRepsLinea> getLineas() {
		return lineas;
	}
	/**
	 * @param lineas the lineas to set
	 */
	public void setLineas(List<ReporteRepsLinea> lineas) {
		this.lineas = lineas;
	}
	/**
	 * @return the titulo
	 */
	public List<ReporteRepsTitulo> getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(List<ReporteRepsTitulo> titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the periodos
	 */
	public List<Periodo> getPeriodos() {
		return periodos;
	}
	/**
	 * @param periodos the periodos to set
	 */
	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public void setRaiz(Raiz raiz) {
		this.raiz = raiz;
	}

	public Raiz getRaiz() {
		return raiz;
	}

	public int getCantTitulos() {
		return cantTitulos;
	}

	public void setCantTitulos(int cantTitulos) {
		this.cantTitulos = cantTitulos;
	}

	
}
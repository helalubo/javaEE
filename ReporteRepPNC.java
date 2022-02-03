package pcup.domain.model;

import java.util.ArrayList;

public class ReporteRepPNC {
	
	private String fVenta = null;
	private String pxTotal = "";
	private String cantMedicos = "";
	private ArrayList<String> metricas = null;
	private ArrayList<String> metricasFiltroPx = null;
	private ArrayList<String> metricasFiltroProd = null;
	private ArrayList<String> metricasFiltroCm = null;
	private int nivel = 0;
	
	
	public String getCantMedicos() {
		return cantMedicos;
	}
	public void setCantMedicos(String cantMedicos) {
		this.cantMedicos = cantMedicos;
	}
	public String getPxTotal() {
		return pxTotal;
	}
	public void setPxTotal(String pxTotal) {
		this.pxTotal = pxTotal;
	}
	public String getfVenta() {
		return fVenta;
	}
	public void setfVenta(String fVenta) {
		this.fVenta = fVenta;
	}
	public ArrayList<String> getMetricas() {
		return metricas;
	}
	public void setMetricas(ArrayList<String> metricas) {
		this.metricas = metricas;
	}
	
	public ArrayList<String> getMetricasFiltroPx() {
		return metricasFiltroPx;
	}
	public void setMetricasFiltroPx(ArrayList<String> metricasFiltroPx) {
		this.metricasFiltroPx = metricasFiltroPx;
	}
	public ArrayList<String> getMetricasFiltroProd() {
		return metricasFiltroProd;
	}
	public void setMetricasFiltroProd(ArrayList<String> metricasFiltroProd) {
		this.metricasFiltroProd = metricasFiltroProd;
	}
	public ArrayList<String> getMetricasFiltroCm() {
		return metricasFiltroCm;
	}
	public void setMetricasFiltroCm(ArrayList<String> metricasFiltroCm) {
		this.metricasFiltroCm = metricasFiltroCm;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	

}

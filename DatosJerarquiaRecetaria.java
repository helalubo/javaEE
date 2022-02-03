package pcup.domain.model;

import java.util.ArrayList;


public class DatosJerarquiaRecetaria {
	private ArrayList<String> claves;
	private ArrayList<String> pos;
	private ArrayList<Double> pct;
	

	private int cantmedclv;
	private int cantmed;
	private int  rotra;
	
	private String cdgPeriodo;
	private int catUltima;
	private String categoria;
	private String mercado;
	private String producto;
	private Double pct_prod;
	private String pos_otras;
	private Double pct_otras;
	
	private String cdgProducto;
	private long cdgMercado;


	public ArrayList<String>  getClaves () {
		return  this.claves;
	}
	public void setClaves (ArrayList<String> claves) {
		this.claves = claves;
	}
	
	public ArrayList<String> getPos () {
		return this.pos;
	}
	public void setPos (ArrayList<String> pos) {
		this.pos = pos;
	}
	
	public ArrayList<Double>  getPct () {
		return this.pct;
	}
	public void setPct (ArrayList<Double> pct) {
		this.pct = pct;
	}
	
	public String getCdgPeriodo () {
		return this.cdgPeriodo;
	}
	public void setCdgPeriodo (String cdgPeriodo) {
		this.cdgPeriodo = cdgPeriodo;
	}
	
	public String getCdgProducto () {
		return this.cdgProducto;

	}
	public void setCdgProducto (String cdgProducto ) {
		this.cdgProducto = cdgProducto;
	}
	public long getCdgMercado () {
		return this.cdgMercado;
	}
	public void setCdgMercado (long cdgMercado) {
		this.cdgMercado = cdgMercado;
	}
	
	
	public int getCatUltima () {
		return this.catUltima;
	}
	public void setCatUltima (int catUltima) {
		this.catUltima = catUltima;
	}
	

	public String getCategoria () {
		return this.categoria;
	}
	public void setCategoria (String categoria) {
		this.categoria = categoria;
	}
	
	
	public Double getPct_otras () {
		return this.pct_otras;
	}
	public void setPct_otras (Double pct_otras) {
		this.pct_otras = pct_otras;
	}
	
	
	public String getPos_otras () {
		return this.pos_otras;
	}
	public void setPos_otras  (String pos_otras) {
		this.pos_otras = pos_otras;
	}
	
	
	public Double getPct_prod () {
		return this.pct_prod;
	}
	public void setPct_prod (Double pct_prod) {
		this.pct_prod = pct_prod;
	}
	
	
	public String getProducto () {
		return this.producto;
	}
	public void setProducto (String producto) {
		this.producto = producto;
	}
	
	
	public String getMercado () {
		return this.mercado;
	}
	public void setMercado (String mercado) {
		this.mercado = mercado;
	}
	public void setCantmedclv(int cantmedclv) {
		this.cantmedclv = cantmedclv;
	}
	public int getCantmedclv() {
		return cantmedclv;
	}
	public void setCantmed(int cantmed) {
		this.cantmed = cantmed;
	}
	public int getCantmed() {
		return cantmed;
	}
	public void setRotra(int rotra) {
		this.rotra = rotra;
	}
	public int getRotra() {
		return rotra;
	}

}
package pcup.domain.model;

public class FilaFiltro {
	private long usr_id; 
	private long filtro_num; 
	private String cdg_region = null; 
	private int cdg_ciudad; 
	private String cdg_zonapostal = null;
	private String cdg_postaldesde = null; 
	private String cdg_postalhasta = null;
	
	public FilaFiltro(long usr_id, long filtro_num, String cdg_region, int cdg_ciudad, String cdg_zonapostal, String cdg_postaldesde, String cdg_postalhasta) {
		super();
		this.usr_id = usr_id;
		this.filtro_num = filtro_num;
		this.cdg_region = cdg_region;
		this.cdg_ciudad = cdg_ciudad;
		this.cdg_zonapostal = cdg_zonapostal;
		this.cdg_postaldesde = cdg_postaldesde;
		this.cdg_postalhasta = cdg_postalhasta;
	}
	public FilaFiltro(String cdg_region, int cdg_ciudad, String cdg_zonapostal, String cdg_postaldesde, String cdg_postalhasta) {
		super();
		this.cdg_region = cdg_region;
		this.cdg_ciudad = cdg_ciudad;
		this.cdg_zonapostal = cdg_zonapostal;
		this.cdg_postaldesde = cdg_postaldesde;
		this.cdg_postalhasta = cdg_postalhasta;
	}
	public FilaFiltro(long filtro_num, String cdg_region, int cdg_ciudad, String cdg_zonapostal, String cdg_postaldesde, String cdg_postalhasta) {
		super();
		this.filtro_num = filtro_num;
		this.cdg_region = cdg_region;
		this.cdg_ciudad = cdg_ciudad;
		this.cdg_zonapostal = cdg_zonapostal;
		this.cdg_postaldesde = cdg_postaldesde;
		this.cdg_postalhasta = cdg_postalhasta;
	}
	public int getCdg_ciudad() {
		return cdg_ciudad;
	}
	public void setCdg_ciudad(int cdg_ciudad) {
		this.cdg_ciudad = cdg_ciudad;
	}
	public String getCdg_postaldesde() {
		return cdg_postaldesde;
	}
	public void setCdg_postaldesde(String cdg_postaldesde) {
		this.cdg_postaldesde = cdg_postaldesde;
	}
	public String getCdg_postalhasta() {
		return cdg_postalhasta;
	}
	public void setCdg_postalhasta(String cdg_postalhasta) {
		this.cdg_postalhasta = cdg_postalhasta;
	}
	public String getCdg_region() {
		return cdg_region;
	}
	public void setCdg_region(String cdg_region) {
		this.cdg_region = cdg_region;
	}
	public String getCdg_zonapostal() {
		return cdg_zonapostal;
	}
	public void setCdg_zonapostal(String cdg_zonapostal) {
		this.cdg_zonapostal = cdg_zonapostal;
	}
	public long getFiltro_num() {
		return filtro_num;
	}
	public void setFiltro_num(long filtro_num) {
		this.filtro_num = filtro_num;
	}
	public long getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}
	
	
}

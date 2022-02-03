package pcup.domain.model;
import java.util.Date;

/**
 * Mapea la entidad "Usuarios" de la base de datos
 * @author devel
 *
 */
public class DatosUsuario {
	
	private String usr_mail = null; 
	private String usr_password = null; 
	private String usr_nombre = null; 
	private String pais = null; 
	private String laboratorio = null;
    Date ultimo_acceso = null; 
    private String idioma_id = null; 
    private String administrador = null;
    private String tipo_usuario = null; 
    private String tipo_filtro = null; 
    private String apm_region = null;
    private String apm_supervisor = null; 
    private String apm_apm = null;

    public DatosUsuario(String usr_mail, String usr_password, String usr_nombre, String pais, String laboratorio, Date ultimo_acceso, String idioma_id, String tipo_usuario, String tipo_filtro, String administrador, String apm_region, String apm_supervisor, String apm_apm) {
		super();
		this.usr_mail = usr_mail;
		this.usr_password = usr_password;
		this.usr_nombre = usr_nombre;
		this.pais = pais;
		this.laboratorio = laboratorio;
		this.ultimo_acceso = ultimo_acceso;
		this.idioma_id = idioma_id;
		this.tipo_usuario = tipo_usuario;
		this.tipo_filtro = tipo_filtro;
		this.administrador = administrador;
		this.apm_region = apm_region;
		this.apm_supervisor = apm_supervisor;
		this.apm_apm = apm_apm;
	}
    
	public String getTipo_usuario() {
		return tipo_usuario;
	}
	public String getAdministrador() {
		return administrador;
	}
	public void setTipo_usuario(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	public String getApm_apm() {
		return apm_apm;
	}
	public void setApm_apm(String apm_apm) {
		this.apm_apm = apm_apm;
	}
	public String getApm_region() {
		return apm_region;
	}
	public void setApm_region(String apm_region) {
		this.apm_region = apm_region;
	}
	public String getApm_supervisor() {
		return apm_supervisor;
	}
	public void setApm_supervisor(String apm_supervisor) {
		this.apm_supervisor = apm_supervisor;
	}
	public String getIdioma_id() {
		return idioma_id;
	}
	public void setIdioma_id(String idioma_id) {
		this.idioma_id = idioma_id;
	}
	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}
	public String getTipo_filtro() {
		return tipo_filtro;
	}
	public void setTipo_filtro(String tipo_filtro) {
		this.tipo_filtro = tipo_filtro;
	}
	public Date getUltimo_acceso() {
		return ultimo_acceso;
	}
	public void setUltimo_acceso(Date ultimo_acceso) {
		this.ultimo_acceso = ultimo_acceso;
	}
	public String getUsr_mail() {
		return usr_mail;
	}
	public void setUsr_mail(String usr_mail) {
		this.usr_mail = usr_mail;
	}
	public String getUsr_nombre() {
		return usr_nombre;
	}
	public void setUsr_nombre(String usr_nombre) {
		this.usr_nombre = usr_nombre;
	}
	public String getUsr_password() {
		return usr_password;
	}
	public void setUsr_password(String usr_password) {
		this.usr_password = usr_password;
	}
	
}

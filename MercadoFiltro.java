package pcup.domain.model;

public class MercadoFiltro{
	
	private Integer secuencia;
	private String codTipoFiltro;
//	private String owner;
	private Long codMercado;
	private String codFiltro;
//	private Boolean incluir;
	
	private String descripcion;
	
	
	
	public MercadoFiltro() {
		super();
		secuencia = null;
		codTipoFiltro = null;
//		owner = null;
		codMercado = null;
		codFiltro = null;
//		incluir = null;
		descripcion = null;
		
		
		
	}

	public MercadoFiltro(Integer secuencia, String codTipoFiltro, String owner,
			Long codMercado, String codFiltro, Boolean incluir) {
		super();
		this.secuencia = secuencia;
		this.codTipoFiltro = codTipoFiltro;
//		this.owner = owner;
		this.codMercado = codMercado;
		this.codFiltro = codFiltro;
//		this.incluir = incluir;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public String getCodTipoFiltro() {
		return codTipoFiltro;
	}

	public void setCodTipoFiltro(String codTipoFiltro) {
		this.codTipoFiltro = codTipoFiltro;
	}

//	public String getOwner() {
//		return owner;
//	}

//	public void setOwner(String owner) {
//		this.owner = owner;
//	}

	public Long getCodMercado() {
		return codMercado;
	}

	public void setCodMercado(Long codMercado) {
		this.codMercado = codMercado;
	}

	public String getCodFiltro() {
		return codFiltro;
	}

	public void setCodFiltro(String codFiltro) {
		this.codFiltro = codFiltro;
	}

//	public Boolean getIncluir() {
//		return incluir;
//	}

//	public void setIncluir(Boolean incluir) {
//		this.incluir = incluir;
//	}
	
	
	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codFiltro == null) ? 0 : codFiltro.hashCode());
		result = prime * result
				+ ((codMercado == null) ? 0 : codMercado.hashCode());
		result = prime * result
				+ ((codTipoFiltro == null) ? 0 : codTipoFiltro.hashCode());
//		result = prime * result + ((incluir == null) ? 0 : incluir.hashCode());
//		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((secuencia == null) ? 0 : secuencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MercadoFiltro other = (MercadoFiltro) obj;
		if (codFiltro == null) {
			if (other.codFiltro != null)
				return false;
		} else if (!codFiltro.equals(other.codFiltro))
			return false;
		if (codMercado == null) {
			if (other.codMercado != null)
				return false;
		} else if (!codMercado.equals(other.codMercado))
			return false;
		if (codTipoFiltro == null) {
			if (other.codTipoFiltro != null)
				return false;
		} else if (!codTipoFiltro.equals(other.codTipoFiltro))
			return false;
//		if (incluir == null) {
//			if (other.incluir != null)
//				return false;
//		} else if (!incluir.equals(other.incluir))
//			return false;
//		if (owner == null) {
//			if (other.owner != null)
//				return false;
//		} else if (!owner.equals(other.owner))
//			return false;
		if (secuencia == null) {
			if (other.secuencia != null)
				return false;
		} else if (!secuencia.equals(other.secuencia))
			return false;
		return true;
	}
	
}
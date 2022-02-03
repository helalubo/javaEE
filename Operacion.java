package pcup.domain.model;

import pcup.exceptions.InvalidValueException;
import plataforma.domain.model.BusinessObject;
import plataforma.domain.model.DBMetadataValidator;

public class Operacion implements BusinessObject{
	
	//clave primaria
	private Long codMercado;
	private Integer indiceOperacion;
	
	//claves foraneas
	private Long codPrimerOperando;
	private Long codSegundoOperando;
	private Integer tipoOperacion;
	
	public Operacion(){
		codMercado = null;
		indiceOperacion = null;
		codPrimerOperando = null;
		codSegundoOperando = null;
		tipoOperacion = null;
	}
	
	public Long getCodMercado() {
		return codMercado;
	}	
	
	public void setCodMercado(long newVar){			
		codMercado = new Long(newVar);
	}

	
	public void setCodMercado(Long newVar){
		codMercado = newVar;
	}
	
	public Integer getIndiceOperacion() {
		return indiceOperacion;
	}
	
	public void setIndiceOperacion(int indiceOperacion)throws InvalidValueException {				
		this.indiceOperacion = indiceOperacion;
	}
	
	public void setIndiceOperacion(Integer indiceOperacion) throws InvalidValueException{		
		this.indiceOperacion = indiceOperacion;
	}
	
	public Long getCodPrimerOperando() {
		return codPrimerOperando;
	}
	public void setCodPrimerOperando(long codPrimerOperando)throws InvalidValueException {
		if(codPrimerOperando <= 0)
			throw new InvalidValueException("El código del primer operando no puede ser menor o igual a 0");
		this.codPrimerOperando = codPrimerOperando;
	}
	public void setCodPrimerOperando(Long codPrimerOperando)throws InvalidValueException {
		if(codPrimerOperando.longValue() <= 0)
			throw new InvalidValueException("El código del primer operando no puede ser menor o igual a 0");
		this.codPrimerOperando = codPrimerOperando;
	}
	public Long getCodSegundoOperando() {
		return codSegundoOperando;
	}
	
	public void setCodSegundoOperando(long codSegundoOperando)throws InvalidValueException {
		if(codSegundoOperando <= 0)
			throw new InvalidValueException("El código del segundo operando no puede ser menor o igual a 0");
		this.codSegundoOperando = codSegundoOperando;
	}
	
	public void setCodSegundoOperando(Long codSegundoOperando)throws InvalidValueException{
		if(codSegundoOperando.longValue() <= 0)
			throw new InvalidValueException("El código del segundo operando no puede ser menor o igual a 0");
		this.codSegundoOperando = codSegundoOperando;
	}
	
	public Integer getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(int tipoOperacion) throws InvalidValueException {
		if(tipoOperacion <= 0 )
			throw new InvalidValueException("El tipo de operación no puede ser menor o igual a 0");
		this.tipoOperacion = tipoOperacion;
	}
	public void setTipoOperacion(Integer tipoOperacion)throws InvalidValueException {
		if(tipoOperacion.intValue() <= 0 )
			throw new InvalidValueException("El tipo de operación no puede ser menor o igual a 0");
		this.tipoOperacion = tipoOperacion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codMercado == null) ? 0 : codMercado.hashCode());
		result = prime
				* result
				+ ((codPrimerOperando == null) ? 0 : codPrimerOperando
						.hashCode());
		result = prime
				* result
				+ ((codSegundoOperando == null) ? 0 : codSegundoOperando
						.hashCode());
		result = prime * result
				+ ((indiceOperacion == null) ? 0 : indiceOperacion.hashCode());
		result = prime * result
				+ ((tipoOperacion == null) ? 0 : tipoOperacion.hashCode());
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
		Operacion other = (Operacion) obj;
		if (codMercado == null) {
			if (other.codMercado != null)
				return false;
		} else if (!codMercado.equals(other.codMercado))
			return false;
		if (codPrimerOperando == null) {
			if (other.codPrimerOperando != null)
				return false;
		} else if (!codPrimerOperando.equals(other.codPrimerOperando))
			return false;
		if (codSegundoOperando == null) {
			if (other.codSegundoOperando != null)
				return false;
		} else if (!codSegundoOperando.equals(other.codSegundoOperando))
			return false;
		if (indiceOperacion == null) {
			if (other.indiceOperacion != null)
				return false;
		} else if (!indiceOperacion.equals(other.indiceOperacion))
			return false;
		if (tipoOperacion == null) {
			if (other.tipoOperacion != null)
				return false;
		} else if (!tipoOperacion.equals(other.tipoOperacion))
			return false;
		return true;
	}	
	
	@Override
	public String toString(){
		return ("Código de mercado: "+codMercado+" ,Indice de la operación: "+indiceOperacion );
	}
	
	public boolean isSameRegister(BusinessObject bo) {
		if(this.equals(bo))
			return true;
		return false;
	}

	public void validateFields() {
		try{
			if(!DBMetadataValidator.validateNumericKey("MERCADOS_OPERACIONES_CDG_MERCADO", codMercado))
				throw new InvalidValueException("El código de mercado no puede ser menor a "+DBMetadataValidator.getFieldLength("MERCADOS_OPERACIONES_CDG_MERCADO"));		
			if(!DBMetadataValidator.validateNumericKey("MERCADOS_OPERACIONES_INDICE", indiceOperacion.intValue()) )
				throw new InvalidValueException("El indice de operacion no puede ser menor o igual a "+DBMetadataValidator.getFieldLength("MERCADOS_OPERACIONES_INDICE"));
			if(!DBMetadataValidator.validateNumericKey("MERCADOS_OPERACIONES_CDG_MERCADO1", codPrimerOperando.longValue()))
				throw new InvalidValueException("El código del primer operando no puede ser menor o igual a "+DBMetadataValidator.getFieldLength("MERCADOS_OPERACIONES_CDG_MERCADO1"));
			if(!DBMetadataValidator.validateNumericKey("MERCADOS_OPERACIONES_CDG_MERCADO2",codSegundoOperando.longValue()))
				throw new InvalidValueException("El código del segundo operando no puede ser menor o igual a "+DBMetadataValidator.getFieldLength("MERCADOS_OPERACIONES_CDG_MERCADO2"));
			if(!DBMetadataValidator.validateNumericKey("MERCADOS_OPERACIONES_TIPO_OPERACION",codSegundoOperando.longValue() ))
				throw new InvalidValueException("El código del segundo operando no puede ser menor o igual a "+DBMetadataValidator.getFieldLength("MERCADOS_OPERACIONES_TIPO_OPERACION"));
		}
		catch(NullPointerException e){

		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
package com.helalubo.mook.cuenta.model;

//presicion en numeros para bancos, numero si o si precisos;
import java.math.BigDecimal;

import com.helalubo.mook.banco.model.Banco;
import com.helalubo.mook.cuenta.exceptions.DineroInsuficienteException;

public class Cuenta {

	private String persona;
	private BigDecimal saldo;
	private Banco banco;
	
	
	
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public void debito(BigDecimal monto) throws DineroInsuficienteException 
	{
		
		
		BigDecimal nuevoSaldo = this.saldo.subtract(monto);
		if(nuevoSaldo.compareTo(BigDecimal.ZERO)< 0) {
			throw new DineroInsuficienteException("Fondos insuficientes");
	
		}
		this.saldo = nuevoSaldo;
		
	}
	public void credito(BigDecimal monto)
	{
		this.saldo =this.saldo.add(monto);
	}
	
	
	public String getPersona() {
		return persona;
	}
	
	
	public Cuenta(String persona, BigDecimal saldo) {
		super();
		this.persona = persona;
		this.saldo = saldo;
	}


	public void setPersona(String persona) {
		this.persona = persona;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
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
		Cuenta other = (Cuenta) obj;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		if (saldo == null) {
			if (other.saldo != null)
				return false;
		} else if (!saldo.equals(other.saldo))
			return false;
		return true;
	}
	
	
	
}

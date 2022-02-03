package pcup.domain.model;

import java.util.LinkedList;
import java.util.List;

public class ReporteEstudiosVariables{
	
	public List<ReporteEstudiosVariable> variables = new LinkedList<ReporteEstudiosVariable>();

	public String getString(String nombreVariable){
		String valor = null;
		
		nombreVariable = nombreVariable.toLowerCase();
		
		for(int i = 0; i<variables.size();i++){
			if(variables.get(i).getNombre().toLowerCase().equals(nombreVariable)){
				valor = variables.get(i).getValor();
			}
		}
		
		return valor;
	}
	
	public Double getDouble(String nombreVariable){
		Double valor = null;
		
		nombreVariable = nombreVariable.toLowerCase();
		
		for(int i = 0; i<variables.size();i++){
			if(variables.get(i).getNombre().toLowerCase().equals(nombreVariable)){
				
				String varValor = variables.get(i).getValor();
				varValor = varValor.replace(",", ".");
				
				valor = Double.parseDouble(varValor);
			}
		}
		
		return valor;
	}
	
	public Integer getInt(String nombreVariable){
		Integer valor = null;
		
		nombreVariable = nombreVariable.toLowerCase();
		
		for(int i = 0; i<variables.size();i++){
			if(variables.get(i).getNombre().toLowerCase().equals(nombreVariable)){
				if(variables.get(i).getValor()!=null){
					valor = Integer.parseInt(variables.get(i).getValor());
				}
			}
		}
		
		return valor;
	}
	
	public void addVariable(String nombre, String valor){
		ReporteEstudiosVariable variable = new ReporteEstudiosVariable();
		variable.setNombre(nombre);
		variable.setValor(valor);
		
		variables.add(variable);
	}
	
	public void addVariable(ReporteEstudiosVariable variable){
		variables.add(variable);
	}
	
}
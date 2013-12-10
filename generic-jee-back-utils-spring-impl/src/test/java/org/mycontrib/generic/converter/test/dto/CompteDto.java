package org.mycontrib.generic.converter.test.dto;

import java.util.ArrayList;
import java.util.List;

public class CompteDto {
	private Integer numero;
	private String label;
	private Double solde;
	
    private List<OperationDto> operations = new ArrayList<OperationDto>();
	
	
	public List<OperationDto> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationDto> operations) {
		this.operations = operations;
	}

	public void addOperation(OperationDto op){
		operations.add(op);
	}
	
	
	@Override
	public String toString() {
		return "CompteDto [label=" + label + ", numero=" + numero + ", solde="
				+ solde + "]";  
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getSolde() {
		return solde;
	}
	public void setSolde(Double solde) {
		this.solde = solde;
	}
	
	
}

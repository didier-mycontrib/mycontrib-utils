package org.mycontrib.generic.converter.test.entity;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name="Compte")
public class CompteEntity {
	
	//@Id
	//@Column(name="numero")
	private Integer numCpt;
	private String label;
	private Double solde;
	
	// @Transient ou @OneToMany
	private List<OperationEntity> operations = new ArrayList<OperationEntity>();
	
	
	public List<OperationEntity> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationEntity> operations) {
		this.operations = operations;
	}

	public void addOperation(OperationEntity op){
		operations.add(op);
	}
	
	public CompteEntity() {
		super();		
	}
	public CompteEntity(Integer numCpt, String label, Double solde) {
		super();
		this.numCpt = numCpt;
		this.label = label;
		this.solde = solde; 
	}
	
		
	@Override 
	public String toString() {
		return "Compte [label=" + label + ", numCpt=" + numCpt + ", solde="
				+ solde + "]";
	}
	public Integer getNumCpt() {
		return numCpt;
	}
	public void setNumCpt(Integer numCpt) {
		this.numCpt = numCpt;
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

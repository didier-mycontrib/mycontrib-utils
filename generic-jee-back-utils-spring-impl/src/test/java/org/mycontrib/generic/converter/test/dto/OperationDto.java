package org.mycontrib.generic.converter.test.dto;

import java.util.Date;

public class OperationDto {
	private Integer numOp;
	private String label;
	private Double montant;
	private String dateOp;
	
	public OperationDto() {
		super();
		dateOp="?";
	}
	
	
	public OperationDto(Integer numOp, String label, Double montant) {
		super();
		this.numOp = numOp;
		this.label = label;
		this.montant = montant;
		this.dateOp = "?";
	}
	@Override
	public String toString() {
		return "OperationDto [dateOp=" + dateOp + ", label=" + label + ", montant="
				+ montant + ", numOp=" + numOp + "]";
	}
	public Integer getNumOp() {
		return numOp;
	}
	public void setNumOp(Integer numOp) {
		this.numOp = numOp;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}


	public String getDateOp() {
		return dateOp;
	}


	public void setDateOp(String dateOp) {
		this.dateOp = dateOp;
	}
	
	
	
}

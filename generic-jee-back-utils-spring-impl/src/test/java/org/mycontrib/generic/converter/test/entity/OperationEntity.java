package org.mycontrib.generic.converter.test.entity;

import java.util.Date;

public class OperationEntity {
	private Integer numOp;
	private String label;
	private Double montant;
	private Date date;
	
	public OperationEntity() {
		super();
		this.date = new Date();
	}
	
	
	public OperationEntity(Integer numOp, String label, Double montant) {
		super();
		this.numOp = numOp;
		this.label = label;
		this.montant = montant;
		this.date = new Date();
	}
	@Override
	public String toString() {
		return "Operation [date=" + date + ", label=" + label + ", montant="
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}

package org.mycontrib.generic.service.internal;

import java.io.Serializable;

import org.mycontrib.generic.classic.exception.ConflictException;
import org.mycontrib.generic.exception.GenericException;

// D : Type of Data(DTO) , E:Type of Entity , ID : Type of ID(pk)

//implemented by class GenericInternalServiceImpl<D,E,ID extends Serializable> 

public interface GenericInternalService<D,ID extends Serializable> {
	
	/*
	public void deleteEntity(ID pk); // remove entity from pk 
		inutile car deja accessible tel quel sur le DAO
	*/
	
	public  void updateEntityFromDto(D d) throws GenericException; // update entity (via merge) , may return RuntimeException
	
	public  D getDtoById(ID pk) throws GenericException ;//by primary key
	
    public  ID persistIdNewEntityFromDto(D d) throws GenericException ; // persist entity from dto (and return  pk )
}

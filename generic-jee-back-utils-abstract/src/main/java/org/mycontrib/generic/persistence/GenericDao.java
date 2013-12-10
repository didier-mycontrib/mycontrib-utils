package org.mycontrib.generic.persistence;
        //dans generic-jee-back-utils-abstract

import java.io.Serializable;

import org.mycontrib.generic.exception.GenericException;

public interface GenericDao<T,ID extends Serializable> {
	
	public void deleteEntity(ID pk) throws GenericException; // remove entity from pk
	public void removeEntity(T e) throws GenericException; // remove entity
	
	public  T updateEntity(T e) throws GenericException; // update entity (and return persist ref )
	
	public  T getEntityById(ID pk) throws GenericException;//by primary key , return null if not found
	
    public  T persistNewEntity(T e) throws GenericException; // persist entity (and return it with pk )
    public  ID persistIdNewEntity(T e) throws GenericException; // persist entity (and return id/pk )

}

package org.mycontrib.generic.test;

import java.io.Serializable;

public abstract class GenericDaoSimpleTest<T,ID extends Serializable> extends GenericDaoTest<T, ID> {
	
	//callback  eventuellement inutiles  a ne pas obligatoirement redefinir (si pas de 1-1 ni de  1-n ni de n-n , ... ):
	public void displayAttachedOtherEntities(T mainEntity){} 
	
	public void attachNewOtherEntities(T mainEntity){}
	public void assertNewAttachedOtherEntities(T mainEntity){}
	
	public void updateAttachedOtherEntities(T mainEntity){}
	public void assertApdatedAttachedOtherEntities(T mainEntity){}

}

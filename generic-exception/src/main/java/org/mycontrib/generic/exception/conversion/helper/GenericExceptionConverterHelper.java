package org.mycontrib.generic.exception.conversion.helper;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.factory.GenericExceptionFactory;

/*
 * interface d'une classe aidant à 
 * fabriquer des "GenericException" depuis d'autres types d'exceptions java
 * (SqlException , PersistenceException , ...)
 * 
 * La classe GenericExceptionFactory utilisera en interne une liste 
 * de "GenericExceptionConverterHelper" complémentaires
 * 
 * La classe BasicGenericExceptionConverterHelper convertira des exceptions de niveau JavaSE
 * D'autres classes convertiront des exceptions spécifiques à certaines api "Spring" , "JPA" , ...
 * 
 */

public interface GenericExceptionConverterHelper {	
	public GenericException convertToGenericException(Exception ex);
	public void setGenericExceptionFactory(GenericExceptionFactory f); //sera automatiquement appelée lors le l'enregistrement 
	                                                                   //lors de genericExceptionFactory.add...Helper(helper)
}

package org.mycontrib.generic.exception.conversion.helper;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.factory.GenericExceptionFactory;

/*
 * pour aider àfabriquer des "GenericException" depuis d'autres types d'exceptions java
 * (SqlException , NullPointerException , ...)
 * 
 */

public class EjbGenericExceptionConverterHelper implements GenericExceptionConverterHelper{
	
	private GenericExceptionFactory genExFactory = null;
	
	@Override //init callback
	public void setGenericExceptionFactory(GenericExceptionFactory f) {
		this.genExFactory=f;
		
	}
	
	@Override
	public GenericException convertToGenericException(Exception ex){
		GenericException genEx =null;
		genEx=convertMostClassicToGenericException(ex);
		if(genEx==null)
			genEx=convertClassicToGenericException(ex);
		return genEx;
	}
	
	private GenericException convertMostClassicToGenericException(Exception ex){
		GenericException genEx =null;
		/*if(ex instanceof NullPointerException) 
			genEx = genExFactory.newInternalException("unknown null pointer exception",ex);
		else if(ex instanceof ClassCastException) 
			genEx = genExFactory.newTechnicalException("invalid cast exception",ex);
		else if(ex instanceof ClassNotFoundException) 
			genEx = genExFactory.newTechnicalException("ClassNotFoundException",ex);
		else if(ex instanceof FileNotFoundException) 
			genEx = genExFactory.newNoAccessException("FileNotFoundException",ex);*/
		return genEx;
	}

	
	private GenericException convertClassicToGenericException(Exception ex){
		GenericException genEx =null;
		
		return genEx;
	}
	
	
}

package org.mycontrib.generic.exception.conversion.helper;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.factory.GenericExceptionFactory;

/*
 * pour aider àfabriquer des "GenericException" depuis d'autres types d'exceptions java
 * (SqlException , NullPointerException , ...)
 * 
 */

public class BasicGenericExceptionConverterHelper implements GenericExceptionConverterHelper{
	
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
		if(genEx==null)
			genEx=convertSpecificToGenericException(ex);
		return genEx;
	}
	
	private GenericException convertMostClassicToGenericException(Exception ex){
		GenericException genEx =null;
		if(ex instanceof NullPointerException) 
			genEx = genExFactory.newInternalException("unknown nullPointerException",ex);
		else if(ex instanceof ClassCastException) 
			genEx = genExFactory.newTechnicalException("invalid cast exception " +ex.getMessage(),ex);
		else if(ex instanceof ClassNotFoundException) 
			genEx = genExFactory.newTechnicalException("ClassNotFoundException " +ex.getMessage(),ex);
		else if(ex instanceof FileNotFoundException) 
			genEx = genExFactory.newNoAccessException("FileNotFoundException " +ex.getMessage(),ex);
		else if(ex instanceof NoInitialContextException)
			genEx = genExFactory.newTechnicalException("NoInitialContextException (jndi) " +ex.getMessage(),ex);
		else if(ex instanceof NamingException) 
			genEx = genExFactory.newTechnicalException("NamingException (lookup jndi) " +ex.getMessage(),ex);
		else if(ex instanceof SQLException){
			SQLException sqlEx= (SQLException) ex;
			if(sqlEx.getSQLState().startsWith("42")) //ok avec mysql , a verifier avec d'autres bases
			    genEx = genExFactory.newNoAccessException("(No DataBase Connection) " +ex.getMessage(),ex);
			else if(sqlEx.getSQLState().startsWith("28"))//ok avec mysql , a verifier avec d'autres bases
			    genEx = genExFactory.newDeniedException("(Access denied to database) " +ex.getMessage(),ex);
			else genEx = genExFactory.newTechnicalException("SQLException " +ex.getMessage() ,ex)
					                  .addDetail("sqlState", sqlEx.getSQLState());
		}
		return genEx;
	}

	
	private GenericException convertClassicToGenericException(Exception ex){
		GenericException genEx =null;
		
		return genEx;
	}
	
	private GenericException convertSpecificToGenericException(Exception ex){
		GenericException genEx =null;
		if(ex instanceof ArithmeticException) 
			genEx = genExFactory.newTechnicalException("ArithmeticException " +ex.getMessage() ,ex);
		return genEx;
	}

	
	
}

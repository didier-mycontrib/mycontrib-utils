package org.mycontrib.generic.classic.exception;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * NoAccessException = GenericException with exceptionType= NO_ACCESS
 * 
 *  no file , no database connexion , no web service connexion ,
 *	timeout , (temporary) unavailable , ...
 * */

public class NoAccessException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAccessException(String message) {
		super(message);
		this.setExceptionType(GenericExceptionType.NO_ACCESS);
	}

	
	public NoAccessException(Throwable cause) {
		super(cause);
		this.setExceptionType(GenericExceptionType.NO_ACCESS);
	}

	public NoAccessException(String message, Throwable cause) {
		super(message, cause);
		this.setExceptionType(GenericExceptionType.NO_ACCESS);
	}

	
	public NoAccessException() {
		this.setExceptionType(GenericExceptionType.NO_ACCESS);
	}

}

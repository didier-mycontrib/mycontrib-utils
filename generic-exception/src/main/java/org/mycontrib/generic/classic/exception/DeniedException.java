package org.mycontrib.generic.classic.exception;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * DeniedException = GenericException with exceptionType= DENIED
 * 
 *  wrong username or password , no privilege , no permission, forbidden , ....
 * */

public class DeniedException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeniedException(String message) {
		super(message);
		this.setExceptionType(GenericExceptionType.DENIED);
	}

	
	public DeniedException(Throwable cause) {
		super(cause);
		this.setExceptionType(GenericExceptionType.DENIED);
	}

	public DeniedException(String message, Throwable cause) {
		super(message, cause);
		this.setExceptionType(GenericExceptionType.DENIED);
	}

		
	public DeniedException() {
		this.setExceptionType(GenericExceptionType.DENIED);
	}

}

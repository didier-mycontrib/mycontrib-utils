package org.mycontrib.generic.classic.exception;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * ConflictException = GenericException with exceptionType= CONFLICT
 * 
 * duplicate primary key during insert , ...
 * */

public class ConflictException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConflictException(String message) {
		super(message);
		this.setExceptionType(GenericExceptionType.CONFLICT);
	}

	
	
	public ConflictException(Throwable cause) {
		super(cause);
		this.setExceptionType(GenericExceptionType.CONFLICT);
	}

	public ConflictException(String message, Throwable cause) {
		super(message, cause);
		this.setExceptionType(GenericExceptionType.CONFLICT);
	}


	public ConflictException() {
		this.setExceptionType(GenericExceptionType.CONFLICT);
	}

}

package org.mycontrib.generic.classic.exception;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * InternalException = GenericException with exceptionType= INTERNAL
 * 
 * generic internal (unknown) without precision , unknown null pointer , ....
 * */

public class InternalException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InternalException(String message) {
		super(message);
		this.setExceptionType(GenericExceptionType.INTERNAL);
	}

	

	public InternalException(Throwable cause) {
		super(cause);
		this.setExceptionType(GenericExceptionType.INTERNAL);
	}

	public InternalException(String message, Throwable cause) {
		super(message, cause);
		this.setExceptionType(GenericExceptionType.INTERNAL);
	}

	

	public InternalException() {
		this.setExceptionType(GenericExceptionType.INTERNAL);
	}

}

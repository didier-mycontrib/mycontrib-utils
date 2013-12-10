package org.mycontrib.generic.classic.exception;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * InvalidValueException = GenericException with exceptionType= INVALID_VALUE
 * 
 * invalid value (wrong type , out of bounded , rejected , ...  )  , ...
 * */

public class InvalidValueException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidValueException(String message) {
		super(message);
		this.setExceptionType(GenericExceptionType.INVALID_VALUE);
	}

		
	public InvalidValueException(Throwable cause) {
		super(cause);
		this.setExceptionType(GenericExceptionType.INVALID_VALUE);
	}

	public InvalidValueException(String message, Throwable cause) {
		super(message, cause);
		this.setExceptionType(GenericExceptionType.INVALID_VALUE);
	}

		
	public InvalidValueException() {
		this.setExceptionType(GenericExceptionType.INVALID_VALUE);
	}

}

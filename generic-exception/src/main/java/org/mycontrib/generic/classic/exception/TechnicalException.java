package org.mycontrib.generic.classic.exception;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * InternalException = GenericException with exceptionType= TECHNICAL
 * 
 * class not found , LazyException , Syntax error in SQL , ... , stack overflow , no more memory , 
 * bug , incompatible cast , ...
 * */

public class TechnicalException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TechnicalException(String message) {
		super(message);
		this.setExceptionType(GenericExceptionType.TECHNICAL);
	}

		
	public TechnicalException(Throwable cause) {
		super(cause);
		this.setExceptionType(GenericExceptionType.TECHNICAL);
	}

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
		this.setExceptionType(GenericExceptionType.TECHNICAL);
	}

		
	public TechnicalException() {
		this.setExceptionType(GenericExceptionType.TECHNICAL);
	}

}

package org.mycontrib.generic.classic.exception;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * NotExistException = GenericException with exceptionType= NOT_EXIST
 * 
 *  entity not found with this id , not exist during deletion, null , ....
 * */

public class NotExistException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistException(String message) {
		super(message);
		this.setExceptionType(GenericExceptionType.NOT_EXIST);
	}

	
	
	public NotExistException(Throwable cause) {
		super(cause);
		this.setExceptionType(GenericExceptionType.NOT_EXIST);
	}

	public NotExistException(String message, Throwable cause) {
		super(message, cause);
		this.setExceptionType(GenericExceptionType.NOT_EXIST);
	}

	
	public NotExistException() {
		this.setExceptionType(GenericExceptionType.NOT_EXIST);
	}

}

package org.mycontrib.generic.exception.util;

import java.io.PrintStream;

import org.mycontrib.generic.exception.GenericException;
import org.slf4j.Logger;

/*
 * implementation basique de GenericExceptionUtil
 * (mode texte , avec slf4j , sans jsf )
 */

public class BasicGenericExceptionUtil implements GenericExceptionUtil {
	
	private PrintStream out_or_err = null;//System.out or System.err or null
	private Logger logger=null;
	
	//utilisation: new BasicGenericExceptionUtil(System.out) ou  new BasicGenericExceptionUtil(System.err)
	public BasicGenericExceptionUtil(PrintStream ps){
		this.out_or_err=ps;
	}
	
	public BasicGenericExceptionUtil(Logger logger){
		this.logger = logger;
	}
	
	public BasicGenericExceptionUtil(){
		out_or_err = System.err; //par defaut
	}

	@Override
	public void show(GenericException e) {
		if(out_or_err!=null)
			out_or_err.println(e.getMessageWithType());
		if(logger!=null)
			logger.error(e.getMessageWithType());
	}

	@Override
	public void showWithDetails(GenericException e) {
		if(out_or_err!=null)
			out_or_err.println(e.getFullMessage());
		if(logger!=null)
			logger.error(e.getFullMessage());
	}

	public PrintStream getOut_or_err() {
		return out_or_err;
	}

	public void setOut_or_err(PrintStream out_or_err) {
		this.out_or_err = out_or_err;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	

}

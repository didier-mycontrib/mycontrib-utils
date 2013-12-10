package org.mycontrib.generic.exception.util;

import java.io.PrintStream;

import javax.swing.JOptionPane;

import org.mycontrib.generic.exception.GenericException;
import org.slf4j.Logger;

/*
 * implementation basique de GenericExceptionUtil
 * (mode texte , avec slf4j , sans jsf )
 */

public class SwingGenericExceptionUtil implements GenericExceptionUtil {
	
	
	private Logger logger=null;
	
	
	public SwingGenericExceptionUtil(Logger logger){
		this.logger = logger;
	}
	
	public SwingGenericExceptionUtil(){
		
	}

	@Override
	public void show(GenericException e) {
		
		if(logger!=null)
			logger.error(e.getMessageWithType());
		javax.swing.JOptionPane.showMessageDialog(null, e.getMessageWithType(), "exception", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showWithDetails(GenericException e) {
		
		if(logger!=null)
			logger.error(e.getFullMessage());
		javax.swing.JOptionPane.showMessageDialog(null, e.getFullMessage(), "exception", JOptionPane.ERROR_MESSAGE);
	}

	

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	

}

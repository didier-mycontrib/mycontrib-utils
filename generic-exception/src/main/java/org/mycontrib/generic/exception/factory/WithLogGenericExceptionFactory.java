package org.mycontrib.generic.exception.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* fabrique qui génère automatiquement des lignes de logs
 * en même temps que que générer les exceptions à soulever via throw 
 * 
 * Utilisation type :
 * 
 * private GenericExceptionFactory genExceptionFactory;
 * 
 * dans constructeur de la classe utilisatrice : 
 *       genExceptionFactory = new WithLogGenericExceptionFactory(logger);
 *    ou genExceptionFactory = new WithLogGenericExceptionFactory();  // default logger
 *    ou genExceptionFactory = new GenericExceptionFactory(); //with no log
 * 
 * dans partie "catch(Exception ex)":
 *    throw genExceptionFactory.newXxxxxException("message erreur",e); 
 *    ou throw genExceptionFactory.convertToGenericException(e);
 * 
 */

public class WithLogGenericExceptionFactory  extends GenericExceptionFactory{

	private Logger logger ;
	private static Logger defaultLogger = LoggerFactory.getLogger(WithLogGenericExceptionFactory.class);
	//ou mieux = logger en fonction du contexte
	
	public WithLogGenericExceptionFactory(){
		super();
		logger = defaultLogger; //logger par defaut
	}
	
	public WithLogGenericExceptionFactory(Logger specificLogger){
		super();
		logger = specificLogger; 
	}
	
	public void doPreAction(String msg){
		logger.error(msg);
	}
	
	public void doPreAction(String msg,Throwable cause){
		logger.error(msg,cause);
	}
	
	public void doPreAction(Throwable cause){
		logger.error(cause.getMessage());
	}
}

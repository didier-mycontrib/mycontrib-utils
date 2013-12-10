package org.mycontrib.generic.exception.util;

import org.mycontrib.generic.exception.GenericException;

/* objet utilitaire pour simplifier le treatment (affichage , ...)
 * d'une GenericException
 * 
 * ---> Au moins les implementations suivantes de l'interface:
 *     * BasicGenericExceptionUtil(System.out ou System.err)
 *       BasicGenericExceptionUtil() ou BasicGenericExceptionUtil(logger) avec logger slf4j
 *     * JsfGenericExceptionUtil() //avec FacesContext
 *     * SwingGenericExceptionUtil() // avec popup swing / MessageBox
 */

public interface GenericExceptionUtil {

	/* montrer/afficher une exception de façon abstraite 
	 * (console ou log ou popup ou message jsf ou ....)
	 * selon un contexte passé au constructeur d'une instance 
	 * d'une des classes d'implémentation*/ 
	public void show(GenericException e); 
	public void showWithDetails(GenericException e); 
	
	
}

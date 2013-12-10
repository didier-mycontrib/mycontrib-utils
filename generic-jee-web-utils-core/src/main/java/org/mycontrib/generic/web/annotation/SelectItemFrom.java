package org.mycontrib.generic.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Annotation permettant de preciser quelles sont
 * les proprietes d'un objet java
 * a prendre en compte pour remplir une liste deroulante
 * 
 * exemple:
 * 
 * @SelectItemFrom(collection="listeXy" , itemKey="numDep",itemLabel="fullString")
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.METHOD}) //applicable que sur les "fields" et methodes
public @interface SelectItemFrom {
	String collection();
	String itemKey();
	String itemLabel();
}

package org.mycontrib.generic.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 
 * @author Didier
 * 
 * Anotation permettant de generer un rafraichissement (via ajax)
 * d'une zone dependante de la zone courante.
 * 
 * refresh="nom_propriete_a_rafraichir" lorsque la propriete 
 * actuelle change (nouvelle selection)
 * 
 *  exemple (produits possibles a reactualiser en fonction
 *           de la categorie selectionnee):
 *  
 *  @In(selectFrom="listeCategories")
 *	@AjaxOnChange(refresh="productId")
 *	private long categorieId; // categorie a choisir
 *	 
 *	@In(selectFrom="listeProduits")	
 *	private long productId; // produit a choisir
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface AjaxOnChange {
	String refresh(); // nom de l'element a rafraichir
}

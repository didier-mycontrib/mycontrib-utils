package org.mycontrib.generic.web.dynview;


/**
 * 
 * @author Didier Defrance
 * 
 * 
 * Vocabulaire:
 *    objet de depart avec annotations @In,@out, ... : DynamicView (or DynamicForm)
 *    NB: dans le cas de JSF2 (@DynaView/@DynaForm au meme niveau que sur @ManagedBean ) 
 *    arbre abstrait ===> AbstractDynamicElement , AbstractDynamicForm , ...
 *
 * a priori:
 *     au moins 3 phases:
 *       1) instrospection --> construction d'un arbre abstrait
 *       2) ajout d'extension specifique (ex: jsf / Html , ...) dans l'arbre  
 *       3) execution des extensions sur arbre(s)  (ex: jsf) ---> generation JSF ou ...
 *       
 *  pour simplifier ,
 *      2 phases :
 *        a) introspection --> construction arbre abstrait et ajout d'extension spécifique en meme temps (extension avec ou sans value et type_element)
 *        b) execution des extensions sur arbres     
 */

//Abstract Dynamic Element (racine de l'arbre d'heritage Adxxx)
public class AdElement {
	
	protected AdElement parent=null; // pour liaison dans arbre (et prefix )
	protected AdExtension extension=null; //pour extension selon technologie (jsf, html, ...)
	protected String name; // essentiel
		
	public AdElement(){
		super();
	}
	
	public AdElement(AdElement parent ,String name){
		this(parent,name,null);
	}
	
	public AdElement(AdElement parent ,String name,AdExtension extension){
		this.name=name;
		this.parent=parent;
		this.extension=extension;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public AdElement getParent() {
		return parent;
	}

	public void setParent(AdElement parent) {
		this.parent = parent;
	}

	public AdExtension getExtension() {
		return extension;
	}

	public void setExtension(AdExtension extension) {
		this.extension = extension;
		if(extension.getElement()==null)
			extension.setElement(this);
	}

	

}

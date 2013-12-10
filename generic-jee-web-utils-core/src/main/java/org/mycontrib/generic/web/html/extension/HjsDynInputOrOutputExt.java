package org.mycontrib.generic.web.html.extension;



/**
 * 
 * @author didier defrance
 * 
 * Hjs = Html and javascript (js)
 *
 */

public abstract class HjsDynInputOrOutputExt extends HjsDynExtension {
	
	
	protected String value; 
	
    private ElementType element_type = ElementType.STRING;
		/* Enumeration des differents type d'element: */ 
	public static enum ElementType{ INTEGER, REAL, STRING }
	
	
	public HjsDynInputOrOutputExt(){
		super();
	}
	
	public HjsDynInputOrOutputExt(String value){
		super();
		this.value=value;
	}
	
	public HjsDynInputOrOutputExt(String name,String value){
		super(name);
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public ElementType getElement_type() {
		return element_type;
	}
	public void setElement_type(ElementType element_type) {
		this.element_type = element_type;
	}
	
	

}

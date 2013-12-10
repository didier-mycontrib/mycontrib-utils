package org.mycontrib.generic.web.dynview;

// classe de base pour extension selon technologie d'affichage exacte (ex: html , jsf)
public abstract class AdExtension {
	
	private AdElement element; // reference vers l'element a etendre

	public AdElement getElement() {
		return element;
	}

	public void setElement(AdElement element) {
		this.element = element;
	}

	public AdExtension() {
		this.element = null;
	}
	
	public AdExtension(AdElement element) {
		super();
		this.element = element;
	}
	
	
}

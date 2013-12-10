package org.mycontrib.generic.web.dynview;

// zone d'entree (saisie ou selection  ou ...)
public class AdInput extends AdElement {

	public AdInput() {
		super();
		
	}

	public AdInput(AdElement parent, String name, AdExtension extension) {
		super(parent, name, extension);
	}


	public AdInput(AdElement parent, String name) {
		super(parent, name);
	}
	
	
}

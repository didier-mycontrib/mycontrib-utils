package org.mycontrib.generic.web.jsf.extension;

import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdView;

public class JsfDynViewExt extends JsfDynExtension{

	
	public void buildAllHtmlElementsOfJsfViewSubTree(){		
		buildJsfTreePart();
	}



	@Override
	public void buildJsfTreePart() {
		AdView adView = (AdView)this.getElement();
		for(AdElement e : adView.getAdElementList()){
			((JsfDynExtension)e.getExtension()).buildJsfTreePart();
	}
		
	}
}

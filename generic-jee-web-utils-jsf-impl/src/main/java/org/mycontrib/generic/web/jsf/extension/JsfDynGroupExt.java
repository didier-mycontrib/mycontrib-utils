package org.mycontrib.generic.web.jsf.extension;

import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdGroup;

// a peut etre decliner en 2 versions (input & output) ?
public class JsfDynGroupExt extends JsfDynExtension {

	@Override
	public void buildJsfTreePart() {
		AdGroup adGroup = (AdGroup) this.getElement();	
		for(AdElement e : adGroup.getAdElementList()){
			((JsfDynExtension)e.getExtension()).buildJsfTreePart();			
		}
	}

}

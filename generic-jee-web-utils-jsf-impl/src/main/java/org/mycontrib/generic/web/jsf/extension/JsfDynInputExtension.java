package org.mycontrib.generic.web.jsf.extension;

import javax.faces.component.html.HtmlForm;

import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdGroup;

public abstract class JsfDynInputExtension extends JsfDynExtension{
	
		
	public HtmlForm getParentHtmlForm(){
		AdElement adParentElement = (AdElement) this.getElement().getParent();
		AdForm adForm = null;
		if(adParentElement instanceof AdForm){
			adForm=(AdForm) adParentElement;
		}else if(adParentElement instanceof AdGroup){
			adForm=(AdForm) adParentElement.getParent();
		}
		JsfDynFormExt jsfDynFormExt = (JsfDynFormExt) adForm.getExtension();
		return jsfDynFormExt.getHtmlForm();
	}
	
	
}

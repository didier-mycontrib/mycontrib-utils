package org.mycontrib.generic.web.jsf.extension;

import javax.faces.component.html.HtmlForm;

import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdView;

public abstract class JsfDynOutputExtension extends JsfDynExtension{
	

	public HtmlForm getParentHtmlForm() {
		AdForm adForm = null;
		AdElement parentElement= (AdElement) this.getElement().getParent();
		if(parentElement instanceof AdForm){
			adForm = (AdForm) parentElement;
		}else if(parentElement instanceof AdView){
			adForm = ((AdView)parentElement).getFirstAdForm();
		}	
		JsfDynFormExt jsfDynFormExt = (JsfDynFormExt) adForm.getExtension();
		return jsfDynFormExt.getHtmlForm();
	}
}

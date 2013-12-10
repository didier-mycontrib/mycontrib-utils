package org.mycontrib.generic.web.jsf.extension;

import javax.faces.component.html.HtmlForm;

import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.jsf.util.DynamicJsfUtil;

public class JsfDynActionExt extends JsfDynExtension {
	
	

	@Override
	public void buildJsfTreePart() {
		AdForm adForm = (AdForm) this.getElement().getParent();
		JsfDynFormExt jsfDynFormExt = (JsfDynFormExt) adForm.getExtension();
		HtmlForm htmlForm = jsfDynFormExt.getHtmlForm();
		String mName=this.getElement().getName();
		String beanPrefix = this.getBeanPrefix();
		DynamicJsfUtil.addCommandButtonInHtmlForm(htmlForm,beanPrefix,  mName);
		
	}

}

package org.mycontrib.generic.web.jsf.extension;

import javax.faces.component.html.HtmlForm;

import org.mycontrib.generic.web.jsf.util.DynamicJsfUtil;



public class JsfDynOutputTextExt extends JsfDynOutputExtension{
	
	@Override
	public void buildJsfTreePart(){
		String fName=this.getElement().getName();
		String beanPrefix = this.getBeanPrefix();
		HtmlForm htmlForm = this.getParentHtmlForm();
		DynamicJsfUtil.addPropertyOutputTextInHtmlForm(htmlForm,beanPrefix,fName);
	}

}

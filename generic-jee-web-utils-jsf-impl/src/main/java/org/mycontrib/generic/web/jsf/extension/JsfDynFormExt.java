package org.mycontrib.generic.web.jsf.extension;

import javax.faces.component.html.HtmlForm;

import org.mycontrib.generic.web.dynview.AdAction;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdGroup;
import org.mycontrib.generic.web.dynview.AdInput;
import org.mycontrib.generic.web.jsf.util.DynamicJsfUtil;

public class JsfDynFormExt extends JsfDynExtension{
	
	private HtmlForm htmlForm =null;

	public HtmlForm getHtmlForm() {
		return htmlForm;
	}
	
	public JsfDynFormExt(){
		htmlForm =new HtmlForm();
		htmlForm.getChildren().add(DynamicJsfUtil.genHtmlPanelGrid(null/*idPanelGrid*/, 2, "text-align:left;"));
	}

	
	@Override
	public void buildJsfTreePart() {
		AdForm adForm = (AdForm) this.getElement();	
		for(AdInput i : adForm.getAdInputElementList()){
			((JsfDynExtension)i.getExtension()).buildJsfTreePart();			
		}
		for(AdAction a : adForm.getAdActionElementList()){
			((JsfDynExtension)a.getExtension()).buildJsfTreePart();			
		}
		for(AdGroup g : adForm.getAdGroupElementList()){
			((JsfDynExtension)g.getExtension()).buildJsfTreePart();				
		}
	}
	
	
}

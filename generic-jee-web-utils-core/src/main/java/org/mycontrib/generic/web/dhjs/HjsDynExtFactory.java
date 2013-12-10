package org.mycontrib.generic.web.dhjs;

import java.lang.reflect.Field;

import org.mycontrib.generic.web.DynExtFactory;
import org.mycontrib.generic.web.annotation.AjaxOnChange;
import org.mycontrib.generic.web.dynview.AdAction;
import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdExtension;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdInputSelect;
import org.mycontrib.generic.web.dynview.AdInputText;
import org.mycontrib.generic.web.dynview.AdOutputText;
import org.mycontrib.generic.web.dynview.AdView;
import org.mycontrib.generic.web.html.extension.HjsDynActionExt;
import org.mycontrib.generic.web.html.extension.HjsDynAjaxRefreshExt;
import org.mycontrib.generic.web.html.extension.HjsDynExtension;
import org.mycontrib.generic.web.html.extension.HjsDynFormExt;
import org.mycontrib.generic.web.html.extension.HjsDynInputExt;
import org.mycontrib.generic.web.html.extension.HjsDynInputOrOutputExt;
import org.mycontrib.generic.web.html.extension.HjsDynInputSelectExt;
import org.mycontrib.generic.web.html.extension.HjsDynInputTextExt;
import org.mycontrib.generic.web.html.extension.HjsDynOutputTextExt;
import org.mycontrib.generic.web.html.extension.HjsDynViewExt;

//Fabrique d'extension "JsfDyn...Ext" pour "AdElement"
//+accrochage dans l'arbre
public class HjsDynExtFactory extends DynExtFactory {
	
	@Override
	public AdExtension buildAdExtensionWithType(AdElement elt,String typeName) {
		HjsDynExtension ext=null;
		boolean withType=false;
		if(elt instanceof AdView)
			ext=new HjsDynViewExt();
		else if(elt instanceof AdForm)
			ext=new HjsDynFormExt();
		else if(elt instanceof AdInputText){
			ext=new HjsDynInputTextExt();withType=true; }
		else if(elt instanceof AdInputSelect){
			ext=new HjsDynInputSelectExt();withType=true; }
		else if(elt instanceof AdOutputText){
			ext=new HjsDynOutputTextExt();withType=true; }
		else if(elt instanceof AdAction){
			ext=new HjsDynActionExt();
			((HjsDynActionExt)ext).setValue(elt.getName()); //default value of action/submit is name of method.
		}
		/*else if(elt instanceof AdGroup)
			ext=new HjsDynGroupExt();*/
		
		if(withType){
		HjsDynInputOrOutputExt extIo = (HjsDynInputOrOutputExt) ext;
		if(typeName==null)
		    extIo.setElement_type(HjsDynInputOrOutputExt.ElementType.STRING); //by default
		else if (typeName.equals("int")
				|| typeName.equals("long"))
			extIo.setElement_type(HjsDynInputOrOutputExt.ElementType.INTEGER);
		else if (typeName.equals("double")
				|| typeName.equals("float"))
			extIo.setElement_type(HjsDynInputOrOutputExt.ElementType.REAL);
		else extIo.setElement_type(HjsDynInputOrOutputExt.ElementType.STRING);
		}
		return ext;
	}
	
	
	/*
evolution a prevoir : tenir compte de @Min @Max 
 */
	
	private String getDynViewName(AdElement elt){
		String dynViewName=null;
		if(elt instanceof AdView){
			dynViewName=elt.getName();
		}
		else{
			AdElement parentElt = elt.getParent();
			if(parentElt != null)
				dynViewName= getDynViewName(parentElt); //recursivite
		}
		
		return dynViewName;
	}

	@Override  //par exemple : ajout d'information secondaire (AjaxRefresh , ...)
	public void updateDynExtension(AdElement elt, Object hint) {
		// tenir compte de @AjaxRefresh sur hint vu comme Field (of input) 
		
		String beanName=getDynViewName(elt);
		
		Field fIn = null;
		if(hint==null) return;
		if(!(hint instanceof Field)) return;
		HjsDynInputExt hjsDynInputExt  =  (HjsDynInputExt) elt.getExtension();
		if(hjsDynInputExt==null) return;
		fIn= (Field) hint;
		AjaxOnChange anotAjaxOnSelect = fIn.getAnnotation(AjaxOnChange.class);
		if (anotAjaxOnSelect != null) {
			HjsDynAjaxRefreshExt hjsajaxRefresh = new HjsDynAjaxRefreshExt(
					"hjsDynServlet?ajax=true&bean="
							+ beanName
							+ "&newHtmlContentFor="
							+ anotAjaxOnSelect
									.refresh(),
					anotAjaxOnSelect.refresh());
			hjsDynInputExt.setHjsAjaxRefresh(hjsajaxRefresh);
		}
		
	}
	

}

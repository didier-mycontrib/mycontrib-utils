package org.mycontrib.generic.web.jsf;

import javax.faces.component.html.HtmlForm;

import org.mycontrib.generic.web.AdViewBuilder;
import org.mycontrib.generic.web.dynview.AdView;
import org.mycontrib.generic.web.jsf.extension.JsfDynFormExt;
import org.mycontrib.generic.web.jsf.extension.JsfDynViewExt;
import org.mycontrib.generic.web.jsf.util.DynamicJsfUtil;

public class AbstractDynamicFormBean {

	
	private HtmlForm dynamicForm; //HtmlForm = type of <h:form > 
	
	/*
	 * <h:form  binding="#{myMBean.dynamicForm}"></form> in <f:view> in jsp
	 */
	public HtmlForm getDynamicForm() {
		// This will be called once in the first RESTORE VIEW phase.  
		 if (dynamicForm == null) {  
			 dynamicForm = populateDynamicForm(this);  
		} 
		return dynamicForm;
	}

	public void setDynamicForm(HtmlForm dynamicForm) {
		this.dynamicForm = dynamicForm;
	}

	
	public  static HtmlForm populateDynamicForm(AbstractDynamicFormBean jsfDynFormMBean){			 
		
		HtmlForm htmlForm = null;
		JsfDynExtFactory jsfDynExtFactory = new JsfDynExtFactory();
		AdViewBuilder adViewBuilder = new AdViewBuilder();
		AdView adView = adViewBuilder.buildDynamicViewWithExtension(jsfDynFormMBean,jsfDynExtFactory); //version "tout d'un coup" 
		//AdView adView = adViewBuilder.buildDynamicView(jsfDynFormMBean);  //construction arbre abtrait depuis intropection
		//jsfDynExtFactory.addAllDynExtension(adView); //ajout d'exension apres coup
		((JsfDynViewExt)adView.getExtension()).buildAllHtmlElementsOfJsfViewSubTree();
		htmlForm = ((JsfDynFormExt)adView.getFirstAdForm().getExtension()).getHtmlForm();
		return htmlForm;
	}
	
	/*
	public  static void populateDynamicFormWithSubObject(HtmlForm htmlForm, String beanPrefix,Class dataBeanClass){
		Field[] champs = dataBeanClass.getDeclaredFields();
		for(Field f : champs){
		   String fName= f.getName();
		   DynamicJsfUtil.addPropertyInputTextInHtmlForm(htmlForm,beanPrefix,fName);
		}
		  
	}*/


}

package org.mycontrib.generic.web.jsf.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;


//avec alignement controle par h:panelGrid et h:outputLabel
public class DynamicJsfUtil {
	

	public static void addPropertyInputTextInHtmlForm(HtmlForm htmlForm,String beanPrefix, String propName){		
		  attachToHtmlFormOrPanelGrid(htmlForm,
				                      DynamicJsfUtil.genHtmlOutputLabel(propName+":"),
				                      DynamicJsfUtil.genHtmlInputText(propName,"#{"+beanPrefix+"."+propName+"}"));
	}
	
	private static HtmlPanelGrid findPanelGridOfHtmlForm(HtmlForm htmlForm){
		HtmlPanelGrid panelGrid =null;
		for(UIComponent c :htmlForm.getChildren()){
			if(c instanceof HtmlPanelGrid){
				panelGrid = (HtmlPanelGrid) c;
				break;
			}
		}
		return panelGrid;
	}
	
	private static void attachToHtmlFormOrPanelGrid(HtmlForm htmlForm,UIComponent c){
		HtmlPanelGrid panelGrid = findPanelGridOfHtmlForm(htmlForm);
		if(panelGrid!=null)
			panelGrid.getChildren().add(c);
		else
			htmlForm.getChildren().add(c);
	}
	
	private static void attachToHtmlFormOrPanelGrid(HtmlForm htmlForm,UIComponent c1,UIComponent c2){
		HtmlPanelGrid panelGrid = findPanelGridOfHtmlForm(htmlForm);
		if(panelGrid!=null){
			panelGrid.getChildren().add(c1);
			panelGrid.getChildren().add(c2);
		}
		else{
			htmlForm.getChildren().add(c1);
			htmlForm.getChildren().add(c2);
			htmlForm.getChildren().add(genLineBreak());
		}
	}
	
	
	

	public static void addPropertyOutputTextInHtmlForm(HtmlForm htmlForm,String beanPrefix, String propName){
		   attachToHtmlFormOrPanelGrid(htmlForm,
                   DynamicJsfUtil.genHtmlOutputLabel(propName+":"),
                   DynamicJsfUtil.genHtmlOutputExprText("#{"+beanPrefix+"."+propName+"}"));
		
	}
	
	public static void addCommandButtonInHtmlForm(HtmlForm htmlForm,String beanPrefix, String methName){
		attachToHtmlFormOrPanelGrid(htmlForm,
                DynamicJsfUtil.genHtmlOutputLabel(""),
                DynamicJsfUtil.genHtmlCommandButton(methName,"#{"+beanPrefix+"."+ methName+"}"));
		}
	public static HtmlOutputText genHtmlOutputFieldNameText(String texte){
		HtmlOutputText htmlOutputText= new HtmlOutputText();
		htmlOutputText.setValue(texte);
		return htmlOutputText;
	}
	
	public static HtmlOutputLabel genHtmlOutputLabel(String texte){
		HtmlOutputLabel htmlOutputLabel= new HtmlOutputLabel();
		htmlOutputLabel.setValue(texte);		
		return htmlOutputLabel;
	}
	
	//nbCols = 2 if null , idPanelGrid may be null , ex de style = "text-align:left;"
	public static HtmlPanelGrid genHtmlPanelGrid(String idPanelGrid,Integer nbCols,String style){
		HtmlPanelGrid htmlPanelGrid= new HtmlPanelGrid();
		if(idPanelGrid!=null)
			htmlPanelGrid.setId(idPanelGrid);
		if(nbCols==null)
			nbCols=2;//par defaut
		htmlPanelGrid.setColumns(nbCols);
		if(style!=null)
			htmlPanelGrid.setStyle(style);
		return htmlPanelGrid;
	}
	
	public static HtmlOutputText genHtmlOutputExprText(String expression){
		HtmlOutputText htmlOutputText= new HtmlOutputText();
		htmlOutputText.setValueExpression("value",
				createValueExpression(expression,String.class));
		return htmlOutputText;
	}
	
	public static HtmlOutputText genHtml(String html){
		HtmlOutputText  htmlText = new HtmlOutputText();  
		htmlText.setValue(html);   htmlText.setEscape(false);
		return htmlText;
	}
	
	public static HtmlOutputText genLineBreak(){
		// linebreak construction  
		return genHtml("<br/>");
	}
	
	public static HtmlOutputText genHR(){
		// <hr/> construction   
		return genHtml("<br/>");
	}
	
	public static HtmlInputText genHtmlInputText(String idInput,String expression){
		//expression = "#{myMBean.client.nom}" ou ...
		HtmlInputText htmlInputText= new HtmlInputText();
		htmlInputText.setValueExpression("value",
				createValueExpression(expression,String.class));
		htmlInputText.setStyleClass("input_text");
		htmlInputText.setId(idInput);
		/*
		ClientValidatorBehavior cvb = (ClientValidatorBehavior)
		   getApplication().createBehavior(ClientValidatorBehavior.BEHAVIOR_TYPE);
		//	ClientValidatorBehavior.BEHAVIOR_TYPE = "org.richfaces.behavior.ClientValidator"
		htmlInputText.addClientBehavior("click", cvb); 
		*/
		
		return htmlInputText;
	}
	
	public static HtmlCommandButton genHtmlCommandButton(String labelValue,String expression){
		//expression = "#{myMBean.doAction}" ou ...
		HtmlCommandButton htmlCommandButton= new HtmlCommandButton();
		htmlCommandButton.setValue(labelValue);
		htmlCommandButton.setActionExpression(createMethodExpression(expression,String.class,new Class[0]));
		return htmlCommandButton;
	}
	
	// Helper ----"createValueExpression"-------------------------------------------------  
	 public static ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {  
	FacesContext facesContext = FacesContext.getCurrentInstance();  
	return facesContext.getApplication().getExpressionFactory().createValueExpression(  
	           facesContext.getELContext(), valueExpression, valueType);  
	}  
	// Helper ----"createMethodExpression"-------------------------------------------------  
	 public static MethodExpression createMethodExpression(String valueExpression, Class<?> valueType,Class<?>[] argTypes) {  
			FacesContext facesContext = FacesContext.getCurrentInstance();  
			return facesContext.getApplication().getExpressionFactory().createMethodExpression(  
			           facesContext.getELContext(), valueExpression, valueType,argTypes);  
			}  

	 public static Application getApplication() {
		   return FacesContext.getCurrentInstance().getApplication();
		  }

      public static ELContext getELContext() {
		   return FacesContext.getCurrentInstance().getELContext();
		  }

	public static ExpressionFactory getExpressionFactory() {
		   return getApplication().getExpressionFactory();
		  }
	
	//Pour alignement (probleme : ok ssi font non proportionnelle)
	public static String addNbSp(String ch,int n){
		String res=null;
		if(ch.length()>=n)
			res=ch;
		else {
			StringBuffer b = new StringBuffer(n);
			int diff = n - ch.length();
			for(int i=0;i<diff;i++)
				b.append("&nbsp;");
			b.append(ch);
			res=b.toString();
		}
		return res;
	}


}

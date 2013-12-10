package org.mycontrib.generic.web.html.extension;


import org.mycontrib.generic.web.dynview.AdExtension;

/**
 * 
 * @author didier defrance
 * 
 * Hjs = Html and javascript (js)
 *
 */

public abstract class HjsDynExtension extends AdExtension {
	
	//nb name is in getElement().getName()
	
	public String getName(){
		return getElement().getName();
	}
	
	public void setName(String name){
		getElement().setName(name);
	}
	
	
	public HjsDynExtension(){
		super();
	}
	
	public HjsDynExtension(String name){
		super();
		this.setName(name);
	}
	
	public String toHjsString(){
		return this.toHjsString(null);
	}
	
	public abstract String toHjsString(Object bean); //partie du rendu html + js
	
/* // pour rendu sans prefixe "xxx:" ni <span id="..."> </span>
 * ????*/
	 private boolean innerAjaxRenderOnly=false; 
		
	 public boolean isInnerAjaxRenderOnly() {
			return innerAjaxRenderOnly;
		}

		public void setInnerAjaxRenderOnly(boolean innerAjaxRenderOnly) {
			this.innerAjaxRenderOnly = innerAjaxRenderOnly;
		}


}

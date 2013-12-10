package org.mycontrib.generic.web.html.extension;

import org.mycontrib.generic.web.dhjs.HjsDynUtil;

public class HjsDynInputTextExt extends HjsDynInputExt{
	
	private boolean required=false;
	private String min="unbounded";
	private String max="unbounded";
	
	/*
	 
evolution a prevoir : tenir compte de @Min @Max 
  
	hjsInputText.setMin(anotIn.min());
	hjsInputText.setMax(anotIn.max());
	hjsInputText.setRequired(anotIn.required());
*/
	

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	@Override
	public String toHjsString(Object bean) {
		StringBuffer bf=new StringBuffer(64);
		this.value = HjsDynUtil.getEmptyButNotNullPropertyValue(bean,this.getName());
		if(!this.isInnerAjaxRenderOnly())
			bf.append(getName()+": <span id='"+this.getName()+"'>");
		 bf.append("<input type='text' name='"+this.getName()+"' value='"+ this.value +"' />");
		 if(!this.isInnerAjaxRenderOnly())
			    bf.append("</span><br/>");
		return bf.toString();
	}
	
	public HjsDynInputTextExt(){
		super();
	}
	
	public HjsDynInputTextExt(String name,String value){
		super(name,value);
	}
	

}

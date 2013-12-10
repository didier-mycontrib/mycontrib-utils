package org.mycontrib.generic.web.html.extension;

import org.mycontrib.generic.web.dhjs.HjsDynUtil;

public class HjsDynOutputTextExt extends HjsDynOutputExt {
	
	public HjsDynOutputTextExt(){
		super();
	}
	
	public HjsDynOutputTextExt(String name,String value){
		super(name,value);
	}

	@Override
	public String toHjsString(Object bean) {
		this.value = HjsDynUtil.getEmptyButNotNullPropertyValue(bean,this.getName());
		StringBuffer bf=new StringBuffer();
		  if(!this.isInnerAjaxRenderOnly()) bf.append(getName()+": <span id='out_"+this.getName()+"' >");
	
		bf.append(this.getValue());
		if(!this.isInnerAjaxRenderOnly())
		    bf.append("</span><br/>");
		return bf.toString();
	}

}

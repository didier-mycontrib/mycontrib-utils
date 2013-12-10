package org.mycontrib.generic.web.html.extension;

public class HjsDynActionExt extends HjsDynExtension{
	
	private String value;
	
	public HjsDynActionExt(){
		this.setValue("??? need @Action String doXxx(){...}");
	}
	
	public HjsDynActionExt(String value){
		this.setValue(value);
	}
	

	@Override
	public String toHjsString(Object bean) {
		String actionName=/*this.getName()*/ "action";//en coherence avec servlet actuel
		return "<input type='submit' name='"+actionName+"' value='"+this.value+"' />";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}

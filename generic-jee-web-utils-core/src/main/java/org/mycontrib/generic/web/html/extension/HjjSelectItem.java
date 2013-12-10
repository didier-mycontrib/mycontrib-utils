package org.mycontrib.generic.web.html.extension;

/* not a extension  , just <option .../> of select/list */
public class HjjSelectItem {
	
	private String label;
	private String value;
	private boolean selected=false;

	public HjjSelectItem(){
		super();
	}
	//ici super.name au sens label
	public HjjSelectItem(String value,String label){
		this.label=label;
		this.value=value;
	}
	
	
	
	public String toHjsString(Object bean) {
		StringBuffer bf=new StringBuffer(32);
		bf.append("<option value='"+this.value+"'");
		if(this.selected) bf.append(" selected='true' ");
		bf.append(" >"+this.label+"</option>");
		return  bf.toString();
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}

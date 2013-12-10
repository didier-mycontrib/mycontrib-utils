package org.mycontrib.generic.web.dynview;

import java.util.ArrayList;
import java.util.List;

//Vue d'IHM (avec eventuel(s) AdForm(s) et zone(s) d'output)
public class AdView extends AdElement implements AdContainer {
   private List<AdElement> adElementList=new ArrayList<AdElement>();
    
   public void addElement(AdElement e){
	   adElementList.add(e);
   }
    
	public List<AdElement> getAdElementList() {
		return adElementList;
	}

	public void setAdElementList(List<AdElement> adElementList) {
		this.adElementList = adElementList;
	}
	
	public AdForm getFirstAdForm(){
		AdForm firstAdForm=null;
		for(AdElement e : adElementList){
			if(e instanceof AdForm){
				firstAdForm = (AdForm)e; break;
			}
		}
		return firstAdForm;		
	}

	public AdView() {
		super();
	}

	public AdView(AdElement parent, String name, AdExtension extension) {
		super(parent, name, extension);
	}

	public AdView(AdElement parent, String name) {
		super(parent, name);
	}
	
	public boolean isWithInputForm() {
		boolean res=false;
		for(AdElement e : adElementList){
			if(e instanceof AdForm){
				res=true; break;
			}
		}
		return res;
	}
	public boolean isWithOutput() {
		boolean res=false;
		for(AdElement e : adElementList){
			if(e instanceof AdOutput){
				res=true; break;
			}
		}
		return res;
	}
	
	
	
}

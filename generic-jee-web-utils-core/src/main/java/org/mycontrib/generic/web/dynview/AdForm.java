package org.mycontrib.generic.web.dynview;

import java.util.ArrayList;
import java.util.List;

//formulaire dynamique (avec zones d'input et bouton(s) d'action)
public class AdForm extends AdElement  implements AdContainer {
	
	private List<AdInput> adInputElementList = new ArrayList<AdInput>();
	private List<AdAction> adActionElementList = new ArrayList<AdAction>();
	private List<AdGroup> adGroupElementList = new ArrayList<AdGroup>();   //un groupe par sous objet (ex: Adresse du Client)

	public AdAction getFirstAdAction(){
		AdAction adAction=null;
		if(adActionElementList.size()>=1)
			adAction=adActionElementList.get(0);
		return adAction;
	}
	
	public AdForm() {
		super();
		
	}

	public AdForm(AdElement parent, String name) {
		super(parent, name);
	}

	public AdForm(AdElement parent, String name, AdExtension extension) {
		super(parent, name, extension);
	}

	public List<AdInput> getAdInputElementList() {
		return adInputElementList;
	}

	public void setAdInputElementList(List<AdInput> adInputElementList) {
		this.adInputElementList = adInputElementList;
	}
	
	public void addAdInputElement(AdInput adInput){
		adInputElementList.add(adInput);
	}

	public List<AdAction> getAdActionElementList() {
		return adActionElementList;
	}

	public void setAdActionElementList(List<AdAction> adActionElementList) {
		this.adActionElementList = adActionElementList;
	}
	
	public void addAdActionElement(AdAction adAction){
		adActionElementList.add(adAction);
	}

	public List<AdGroup> getAdGroupElementList() {
		return adGroupElementList;
	}

	public void setAdGroupElementList(List<AdGroup> adGroupElementList) {
		this.adGroupElementList = adGroupElementList;
	}
	
	public void addAdGroupElement(AdGroup adGroup){
		adGroupElementList.add(adGroup);
	}

	@Override
	public void addElement(AdElement e) {
		if(e instanceof AdInput)
			this.addAdInputElement((AdInput)e);
		else if(e instanceof AdAction)
			this.addAdActionElement((AdAction)e);
		else if(e instanceof AdGroup)
			this.addAdGroupElement((AdGroup)e);
		
	}

	@Override
	public List<AdElement> getAdElementList() {
		List<AdElement> globalList = new ArrayList<AdElement>();
		globalList.addAll(this.adInputElementList);
		globalList.addAll(this.adGroupElementList);
		globalList.addAll(this.adActionElementList);
		return globalList;
	}

	
}

package org.mycontrib.generic.web.dynview;

import java.util.ArrayList;
import java.util.List;

// groupe de champ (ex: Input) pour un sous objet
// name of group = name of subObject
public class AdGroup extends AdElement implements AdContainer {
	
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

	public AdGroup() {
		super();
	}

	public AdGroup(AdElement parent, String name) {
		super(parent, name);		
	}

	public AdGroup(AdElement parent, String name, AdExtension extension) {
		super(parent, name, extension);
	}
	

}

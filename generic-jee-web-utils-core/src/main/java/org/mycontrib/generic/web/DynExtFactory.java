package org.mycontrib.generic.web;

import org.mycontrib.generic.web.dynview.AdAction;
import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdExtension;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdGroup;
import org.mycontrib.generic.web.dynview.AdInput;
import org.mycontrib.generic.web.dynview.AdView;

public abstract class DynExtFactory {
	
	public abstract AdExtension buildAdExtensionWithType(AdElement element,String typeName);
	
	//creation et ajout d'une extension sans precision du type
	public void addNewDynExtension(AdElement elt){
		elt.setExtension(buildAdExtensionWithType(elt,null));
	}
	
	//creation et ajout d'une extension avec eventuel precision du type
		public void addNewDynExtensionWithValue(AdElement elt,String typeName){
				elt.setExtension(buildAdExtensionWithType(elt,typeName));
		}
		
	//par exemple : ajout d'information secondaire (AjaxRefresh , ...)		
	public void updateDynExtension(AdElement elt,Object hint){	
       //nothing done by default
	}
	
	//parcours de l'arbre existant et ajout (apres coup) des extensions
		public  void addAllDynExtension(AdView adView){
		addNewDynExtension(adView);	
		for(AdElement e : adView.getAdElementList()){
			addNewDynExtension(e);
			if(e instanceof AdForm){
				AdForm adForm = (AdForm) e;
				for(AdInput i : adForm.getAdInputElementList()){
					addNewDynExtension(i);	
				}
				for(AdAction a : adForm.getAdActionElementList()){
					addNewDynExtension(a);					
				}
				for(AdGroup g : adForm.getAdGroupElementList()){
					addNewDynExtension(g);	
					for(AdElement ee : g.getAdElementList()){
						addNewDynExtension(ee);	
					}
				}
			}
			if(e instanceof AdGroup){
				AdGroup adGroup = (AdGroup) e;
				for(AdElement ee : adGroup.getAdElementList()){
					addNewDynExtension(ee);	
				}
			}
		}
		}


}

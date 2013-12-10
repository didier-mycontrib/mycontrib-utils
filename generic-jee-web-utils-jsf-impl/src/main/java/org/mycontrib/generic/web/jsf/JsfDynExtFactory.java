package org.mycontrib.generic.web.jsf;

import org.mycontrib.generic.web.DynExtFactory;
import org.mycontrib.generic.web.dynview.AdAction;
import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdExtension;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdGroup;
import org.mycontrib.generic.web.dynview.AdInput;
import org.mycontrib.generic.web.dynview.AdInputText;
import org.mycontrib.generic.web.dynview.AdOutputText;
import org.mycontrib.generic.web.dynview.AdView;
import org.mycontrib.generic.web.jsf.extension.JsfDynActionExt;
import org.mycontrib.generic.web.jsf.extension.JsfDynExtension;
import org.mycontrib.generic.web.jsf.extension.JsfDynFormExt;
import org.mycontrib.generic.web.jsf.extension.JsfDynGroupExt;
import org.mycontrib.generic.web.jsf.extension.JsfDynInputTextExt;
import org.mycontrib.generic.web.jsf.extension.JsfDynOutputTextExt;
import org.mycontrib.generic.web.jsf.extension.JsfDynViewExt;

//Fabrique d'extension "JsfDyn...Ext" pour "AdElement"
//+accrochage dans l'arbre
public class JsfDynExtFactory extends DynExtFactory {
	
	@Override
	public AdExtension buildAdExtensionWithType(AdElement elt,String typeName) {
		//typeName not yet use here (may be for f:converter or f:validate... ) 
		JsfDynExtension ext=null;
		if(elt instanceof AdView)
			ext=new JsfDynViewExt();
		else if(elt instanceof AdForm)
			ext=new JsfDynFormExt();
		else if(elt instanceof AdInputText)
			ext=new JsfDynInputTextExt();
		else if(elt instanceof AdOutputText)
			ext=new JsfDynOutputTextExt();
		else if(elt instanceof AdAction)
			ext=new JsfDynActionExt();
		else if(elt instanceof AdGroup)
			ext=new JsfDynGroupExt();
		return ext;
	}

	@Override //par exemple : ajout d'information secondaire
	public void updateDynExtension(AdElement elt, Object hint) {
		// TODO Auto-generated method stub
		
	}
	

}

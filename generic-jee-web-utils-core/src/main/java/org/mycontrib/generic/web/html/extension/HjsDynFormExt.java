package org.mycontrib.generic.web.html.extension;

import java.util.ArrayList;
import java.util.List;

import org.mycontrib.generic.web.dynview.AdAction;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdInput;

public class HjsDynFormExt extends HjsDynExtension{
	
	public HjsDynActionExt getFirstAction(){
		HjsDynActionExt firstAction = null;
		/*List<HjsDynActionExt> actions = getActions();
		if(actions!=null && !actions.isEmpty())
			firstAction = actions.get(0);*/
		firstAction= (HjsDynActionExt) (((AdForm) this.getElement()).getFirstAdAction()).getExtension();
		return firstAction;
	}
	
	public List<HjsDynInputExt> getInputs()	{
		List<HjsDynInputExt> inputs=new ArrayList<HjsDynInputExt>();
		List<AdInput> listAdInput = ((AdForm) this.getElement()).getAdInputElementList();
		for(AdInput adInput : listAdInput){
			inputs.add((HjsDynInputExt)adInput.getExtension());
		}
		return inputs;
	}
	
	public List<HjsDynActionExt> getActions()	{
		List<HjsDynActionExt> actions=new ArrayList<HjsDynActionExt>();
		List<AdAction> listAdAction = ((AdForm) this.getElement()).getAdActionElementList();
		for(AdAction adAction : listAdAction){
			actions.add((HjsDynActionExt)adAction.getExtension());
		}
		return actions;
	}
	

	
	@Override
	public String toHjsString(Object bean) {
		StringBuffer bf=new StringBuffer();
		bf.append("<form method='POST' onSubmit='return verifGlobale(this);' >\n");
		   for(HjsDynInputExt i : this.getInputs())
			   bf.append(i.toHjsString(bean)+"\n");
		bf.append(getFirstAction().toHjsString(bean)+'\n');
		bf.append("</form>\n");
		return bf.toString();
	}
	
	
	
}

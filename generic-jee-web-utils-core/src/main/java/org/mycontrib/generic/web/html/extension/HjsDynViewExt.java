package org.mycontrib.generic.web.html.extension;

import java.util.ArrayList;
import java.util.List;

import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdOutput;
import org.mycontrib.generic.web.dynview.AdView;

/** Abstract View (html + js)
 * 
 * Vue complete avec :
 *   - metode verifGlobale() javascript
 *   - eventuel formulaire HTML <form> .... </form>
 *   - eventuelles zones d'affichage (output) 
 * 
 * */
public class HjsDynViewExt extends HjsDynExtension{
	

	//private Collection<HjsDynOutputExt> outputs=new ArrayList<HjsDynOutputExt>();
	
	public boolean isWithInputForm() {
		return ((AdView) getElement()).isWithInputForm();
	}
	public boolean isWithOutput() {
		return ((AdView) getElement()).isWithOutput();
	}
	
	
	
	public HjsDynFormExt getForm() {
		return (HjsDynFormExt) (((AdView)this.getElement()).getFirstAdForm()).getExtension();
	}
	
	public List<HjsDynOutputExt> getOutputs()	{
		List<HjsDynOutputExt> ouputs=new ArrayList<HjsDynOutputExt>();
		List<AdElement> listAdElt = ((AdView) this.getElement()).getAdElementList();
		for(AdElement adElt : listAdElt){
			if(adElt instanceof AdOutput){
			    ouputs.add((HjsDynOutputExt)adElt.getExtension());
			}
		}
		return ouputs;
	}
	
	@Override
	public String toHjsString(Object bean) {
		StringBuffer bf=new StringBuffer();
		bf.append(jsGlobalVerifFunctionString());
		if(this.isWithInputForm())
			bf.append(getForm().toHjsString(bean));
		if(this.isWithOutput())
		   {
			bf.append("<hr/>\n");
			for(HjsDynOutputExt o: this.getOutputs())
				bf.append(o.toHjsString(bean));
		   }
		return bf.toString();
	} 
	
	
	public String jsGlobalVerifFunctionString()
	{
		StringBuffer bf = new StringBuffer();
		bf.append("<script language='javascript'>\n<!--\n");
		bf.append("function verifGlobale(frm){\n");
		if(this.isWithInputForm())
			for(HjsDynInputExt hjsInput : this.getForm().getInputs())
			{
				if(hjsInput.getElement_type()==HjsDynInputOrOutputExt.ElementType.INTEGER
				   ||hjsInput.getElement_type()==HjsDynInputOrOutputExt.ElementType.REAL)
				bf.append("if(!verifNum(frm."+hjsInput.getName()+".value,frm."+hjsInput.getName()+")) return false;\n");
				
				if(hjsInput instanceof HjsDynInputTextExt)
				{
					/*
					evolution a prevoir : tenir compte de @Min @Max 
					 */
							
					
					HjsDynInputTextExt zone  = (HjsDynInputTextExt) hjsInput;
					if(!zone.getMin().equals("unbounded") && !zone.getMax().equals("unbounded"))
						bf.append("if(!verifEntre(frm."+zone.getName()+".value,"
								     +zone.getMin()+","+zone.getMax()+")) return false;\n");	
					else if(!zone.getMin().equals("unbounded") && zone.getMax().equals("unbounded"))
					bf.append("if(!verifMin(frm."+zone.getName()+".value,"
						     +zone.getMin()+")) return false;\n");
					else if(zone.getMin().equals("unbounded") && !zone.getMax().equals("unbounded"))
						bf.append("if(!verifMax(frm."+zone.getName()+".value,"
							     +zone.getMax()+")) return false;\n");
					if(zone.isRequired())
						bf.append("if(!verifRequired(frm."+zone.getName()+".value,frm."+zone.getName()+")) return false;\n");
				}
			}
		bf.append("return true;\n");
		bf.append("}\n");
		bf.append("-->\n</script>\n");
		/*<SCRIPT LANGUAGE="JAVASCRIPT">
		<!--
		function verifGlobale(frm)
		{
		if(!verifNum(frm.age.value,frm.age)) return false;
		if(!verifEntre(frm.age.value,0,120)) return false;
		if(!verifNum(frm.dep.value,frm.dep)) return false;
		if(!verifEntre(frm.dep.value,1,95)) return false;
		if(!verifNum(frm.tel.value,frm.tel)) return false;
		if(frm.tel.value.length < 10) 
		   {
		  alert("10 chiffres mini pour le num�ro de t�l�phone");
		  return false;
		   }
		if(!verifEmail(frm.email.value))  return false;
		return true;
		}
		//-->
		</SCRIPT>*/
		
		return bf.toString();
	}
	
	

}

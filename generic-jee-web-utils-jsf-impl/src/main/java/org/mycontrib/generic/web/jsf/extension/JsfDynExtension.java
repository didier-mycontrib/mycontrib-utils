package org.mycontrib.generic.web.jsf.extension;

import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdExtension;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdGroup;
import org.mycontrib.generic.web.dynview.AdView;

// classe de base des extensions JSF
public abstract class JsfDynExtension extends AdExtension{
	
	public String getBeanPrefix(){
		String beanPrefix=null;
		AdElement parentElement = this.getElement().getParent();
		if(parentElement instanceof AdForm
			|| parentElement instanceof AdView){
		    beanPrefix = parentElement.getName();
		}
		else if(parentElement instanceof AdGroup){
			String parentBeanPrefix = ((JsfDynExtension)parentElement.getExtension()).getBeanPrefix();
			beanPrefix = parentBeanPrefix +"." +parentElement.getName(); 
		}
		return beanPrefix;
	}
	
	public abstract void buildJsfTreePart();//build part of jsf tree

}

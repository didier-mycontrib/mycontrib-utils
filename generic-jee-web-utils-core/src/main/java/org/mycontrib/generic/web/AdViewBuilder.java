package org.mycontrib.generic.web;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mycontrib.generic.reflection.util.ReflectionUtil;
import org.mycontrib.generic.web.annotation.DynAction;
import org.mycontrib.generic.web.annotation.DynInput;
import org.mycontrib.generic.web.annotation.DynOutput;
import org.mycontrib.generic.web.annotation.DynView;
import org.mycontrib.generic.web.annotation.SelectItemFrom;
import org.mycontrib.generic.web.dynview.AdAction;
import org.mycontrib.generic.web.dynview.AdContainer;
import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdForm;
import org.mycontrib.generic.web.dynview.AdGroup;
import org.mycontrib.generic.web.dynview.AdInput;
import org.mycontrib.generic.web.dynview.AdInputSelect;
import org.mycontrib.generic.web.dynview.AdInputText;
import org.mycontrib.generic.web.dynview.AdOutput;
import org.mycontrib.generic.web.dynview.AdOutputText;
import org.mycontrib.generic.web.dynview.AdView;


public class AdViewBuilder {
	

public  AdView buildDynamicView(Object bean){
	return buildDynamicViewWithExtension(bean,null);
}

private void addDynExtensionIfNotNull(AdElement adElt,DynExtFactory dynExtFactory,String typeName){
	if(dynExtFactory==null)return;
	dynExtFactory.addNewDynExtensionWithValue(adElt,typeName);
	
}

private AdInput buildAdInputWithExtension(Field fIn,AdElement parent,DynExtFactory dynExtFactory){
	   //NB: parent may be null (if ajax subtree) 
	   String fName= fIn.getName();
	   SelectItemFrom annotSelectItemFrom = fIn.getAnnotation(SelectItemFrom.class);
	   AdInput adInput= annotSelectItemFrom==null ? new AdInputText(parent,fName) 
	                    : new AdInputSelect(parent,fName,annotSelectItemFrom.collection(),
	                    		annotSelectItemFrom.itemKey(),annotSelectItemFrom.itemLabel());
	   if(parent !=null){
	     ((AdContainer) parent).addElement(adInput);
	   }
	   String typeName= fIn.getType().getSimpleName();
	   addDynExtensionIfNotNull(adInput,dynExtFactory,typeName);
	   dynExtFactory.updateDynExtension(adInput, fIn/*hint as Object as Field*/);
	   return adInput;
}

private AdOutput buildAdOutputWithExtension(Field fOut,AdElement parent,DynExtFactory dynExtFactory){
	   //NB: parent may be null (if ajax subtree) !!!
	   AdOutput adOutput =null;
	   String fName= fOut.getName();
	   AdOutputText adOutputText = new AdOutputText(parent,fName);
	   if(parent !=null){
		   ((AdContainer)parent).addElement(adOutputText);
	   }
	   String typeName= fOut.getType().getSimpleName();
	   addDynExtensionIfNotNull(adOutputText,dynExtFactory,typeName);
	   adOutput= adOutputText;
	   return adOutput;
}


public  AdElement buildDynamicElementWithExtension(Object bean, DynExtFactory dynExtFactory,String propertyName){	
	return buildDynamicElementWithParentAndExtension(bean,dynExtFactory,propertyName,null);
}

public  AdElement buildDynamicElementWithParentAndExtension(Object bean, DynExtFactory dynExtFactory,String propertyName,AdElement parent){	
	    //NB: parent may be null !!!
	    AdElement adElement = null;
	    try {
			Class c = bean.getClass();
			Field propField = c.getDeclaredField(propertyName);
			DynInput anotIn = propField.getAnnotation(DynInput.class);
			DynOutput anotOut = propField.getAnnotation(DynOutput.class);
			if (anotIn != null) {
				adElement = buildAdInputWithExtension(propField, parent, dynExtFactory);
							}
			else if (anotOut != null) {
				adElement = buildAdOutputWithExtension(propField, parent, dynExtFactory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    return adElement;
}


public  AdView buildDynamicViewWithExtension(Object bean, DynExtFactory dynExtFactory){			 
	    AdView adView = new AdView();

		Class c = bean.getClass(); // metaClass qui decrit la classe de l'instance "bean"
		String beanName=ReflectionUtil.firstLower(c.getSimpleName()); // ex: myMBean  // default name
		
		DynView annotView =  (DynView) c.getAnnotation(DynView.class);
		if(annotView != null && annotView.name() != null && annotView.name().length() > 0 )
			beanName = annotView.name();
		
		//view without form if anotView.viewType() != ViewType.OUT ????
		
		AdForm adForm = new AdForm(adView,beanName);
		adView.setName(beanName);
		addDynExtensionIfNotNull(adView,dynExtFactory,null /* N/A typeName*/);
		adView.addElement(adForm);
		addDynExtensionIfNotNull(adForm,dynExtFactory,null /* N/A typeName*/);
	
		Field[] champs = c.getDeclaredFields();
		for(Field fIn : champs){
		   String fName= fIn.getName();
		   DynInput annotIn = fIn.getAnnotation(DynInput.class);
		   if(annotIn!=null){
			   if(ReflectionUtil.notSubObject(fIn.getType().getName())){
				   buildAdInputWithExtension(fIn,adForm,dynExtFactory);				   
			   }
			   else {
				   // Sous objet (ex: client)
				   Class dataBeanClass = ReflectionUtil.getSubObjectClass(fIn);
				   AdGroup adGroup = new AdGroup(adForm,fName);
				   adForm.addAdGroupElement(adGroup);
				   addDynExtensionIfNotNull(adGroup,dynExtFactory,null /* N/A typeName*/);
				   
				   Field[] champsOfSubObject = dataBeanClass.getDeclaredFields();
					for(Field fInSubObj : champsOfSubObject){					 
					   buildAdInputWithExtension(fInSubObj,adGroup,dynExtFactory);					   
					}
			   }			 
		   }
		}
		Method[] methodes = c.getDeclaredMethods();
		for(Method m : methodes ){
			String mName=m.getName();
			DynAction annotAction = m.getAnnotation(DynAction.class);
			   if(annotAction!=null){
				   AdAction adAction = new AdAction(adForm,mName);
				   //adView.addElement(adAction);
				   adForm.addAdActionElement(adAction);				 
				   addDynExtensionIfNotNull(adAction,dynExtFactory,null /* N/A typeName*/);
			   }
		}
		
		for(Field fOut : champs){
			   DynOutput annotOut = fOut.getAnnotation(DynOutput.class);
			   if(annotOut!=null){
				   buildAdOutputWithExtension(fOut,adView,dynExtFactory);				   			   
			   }
			}   
		return adView;
	}
	


}

package org.mycontrib.generic.web.dhjs;


import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mycontrib.generic.reflection.util.ReflectionUtil;
import org.mycontrib.generic.web.AdViewBuilder;
import org.mycontrib.generic.web.annotation.DynView;
import org.mycontrib.generic.web.dynview.AdElement;
import org.mycontrib.generic.web.dynview.AdView;
import org.mycontrib.generic.web.html.extension.HjsDynExtension;
import org.mycontrib.generic.web.html.extension.HjsDynViewExt;




/* classe utilitaire produisant un arbre de composants Hjs...
 * a partir d'une classe java annotee via @DynInput, @DynOutput, ...
 */

public class HjsDynUtil {

	
	public static String getEmptyButNotNullPropertyValue(Object bean,String fName){
		String value = ReflectionUtil.getPropertyValue(bean, fName);
		if (value == null || value.equals("null"))
			value = "";
		return value;
		}

	
	/**
	 * 
	 * @param bean
	 * @param actionParamValue = nom de la methode d'action a executer
	 * @return statut ("ok" ou null ou ....)
	 */

	public static String executeAction(Object bean, String actionParamValue) {
		String statut = null;
		if (actionParamValue == null)
			return null;
		try {
			Class c = bean.getClass();
			Method m = c.getDeclaredMethod(actionParamValue);
			if (m != null)
				statut = String.valueOf(m.invoke(bean, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statut;
	}

	/**
	 * Peuple les valeurs des proprietes d'un javaBean via introspection
	 * @param bean
	 * @param paramsMap = Map<String, String[]> retourne par request.getParameterMap()
	 */
	public static void populateBeanWithParamsMap(Object bean,
			Map<String, String[]> paramsMap) {

		for (String paramName : paramsMap.keySet()) {
			String strParamValue = paramsMap.get(paramName)[0];
			Object paramValue = null;
			if (strParamValue != null) {
				System.out.println(paramName + ":" + strParamValue);
				try {
					Class c = bean.getClass();
					Method[] tabMeth = c.getMethods();
					int index = -1;
					for (int i = 0; i < tabMeth.length; i++)
						if (tabMeth[i].getName().equals(
								"set" + ReflectionUtil.firstUpper(paramName))) {
							index = i;
							break;
						}
					if (index > 0) {
						Type[] ts = tabMeth[index].getGenericParameterTypes();
						String firstParamTypeName = ts[0].toString();
						if (firstParamTypeName.equals("int"))
							paramValue = Integer.valueOf(strParamValue);
						if (firstParamTypeName.equals("long"))
							paramValue = Long.valueOf(strParamValue);
						else if (firstParamTypeName.equals("double"))
							paramValue = Double.valueOf(strParamValue);
						else if (firstParamTypeName.equals("float"))
							paramValue = Float.valueOf(strParamValue);
						else if (firstParamTypeName.equals("boolean"))
							paramValue = Boolean.valueOf(strParamValue);
						else
							paramValue = strParamValue;
						tabMeth[index].invoke(bean, paramValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param className
	 * @param session http
	 * @return instance javabean liee a  la classe renseignee "className"
	 *          NB: nom du bean = "modelviewBean" par defaut et remplace par name of @View
	 */
	public static Object getSessionScopeBeanFromClassName(String className,
			HttpSession session) {
		Object modelViewBean = null;
		String beanName = "modelViewBean"; // nom par defaut (remplace par name
											// of @View)
		try {
			Class c = Class.forName(className);
			DynView anotView = (DynView) c.getAnnotation(DynView.class);
			if (anotView != null)
				beanName = anotView.name();
			modelViewBean = session.getAttribute(beanName);
			if (modelViewBean == null) {
				modelViewBean = c.newInstance();
				session.setAttribute(beanName, modelViewBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelViewBean;
	}
	
	/**
	 * 
	 * @param bean
	 * @return l'objet AdView with extension HjsView correspondant a une representation abstraite/java d'une partie "html+js" de la page HTML
	 *         a generer , suite a donner : out.println(hjsView.toHjsString());
	 */
	
	public static AdView getAdViewWithHjsExtFromJavaBean(Object bean) {
		AdView adView =null;
		
		HjsDynExtFactory hjsDynExtFactory = new HjsDynExtFactory();
		AdViewBuilder adViewBuilder = new AdViewBuilder();
		adView = adViewBuilder.buildDynamicViewWithExtension(bean,hjsDynExtFactory); 	
		return adView;
	}
	
	public static HjsDynViewExt getHjsViewFromJavaBean(Object bean) {
		return (HjsDynViewExt) getAdViewWithHjsExtFromJavaBean(bean).getExtension();
	}
	
	public static AdElement getAdElementWithHjsExtFromJavaBean(Object bean,String propertyName) {
		AdElement adElement =null;		
		HjsDynExtFactory hjsDynExtFactory = new HjsDynExtFactory();
		AdViewBuilder adViewBuilder = new AdViewBuilder();
		adElement = adViewBuilder.buildDynamicElementWithExtension(bean,hjsDynExtFactory,propertyName); 	
		return adElement;
	}
	
	public static HjsDynExtension getHjsElementFromJavaBeanProperty(Object bean,
			String propertyName) {
		
		AdElement adElement = getAdElementWithHjsExtFromJavaBean(bean,propertyName);		
		return (HjsDynExtension) adElement.getExtension();
	}

}

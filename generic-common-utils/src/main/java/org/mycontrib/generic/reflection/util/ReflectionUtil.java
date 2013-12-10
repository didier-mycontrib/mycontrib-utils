package org.mycontrib.generic.reflection.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import org.apache.commons.beanutils.PropertyUtils;

public class ReflectionUtil {
	
	//methode recursive retournant la liste des NOMS des attributs/fields d'une classe
	//ainsi que tous ceux qui sont hérités des éventuelles classes parentes
	public static List<String> getPropertyNameListWithInheritance(Class c){
		List<String> listFieldName = new ArrayList<String>();
		for(Field f : c.getDeclaredFields()){
			listFieldName.add(f.getName());
		}
		Class sc = c.getSuperclass();
		if(!sc.getSimpleName().equals("Object")){
			listFieldName.addAll(getPropertyNameListWithInheritance(sc));
		}
		return listFieldName;
	}
	
	//methode recursive retournant la liste des NOMS des "Field" d'une classe
	//ainsi que tous ceux qui sont hérités des éventuelles classes parentes
	//Attention --> les "Fields" retournés peuvent appartenir à plusieurs classes différentes
	public static List<Field> getFieldListWithInheritance(Class c){
		List<Field> listField = new ArrayList<Field>();
		for(Field f : c.getDeclaredFields()){
			listField.add(f);
		}
		Class sc = c.getSuperclass();
		if(!sc.getSimpleName().equals("Object")){
			listField.addAll(getFieldListWithInheritance(sc));
		}
		return listField;
	}

	public static String getPropertyValue(Object bean, String paramName) {
		String res = null;
		try {
			Class c = bean.getClass();
			Method m = null;
			try {
				m=getGetterMethod(c,paramName);						
			} catch (Exception e) {
				// en cas d'échec , essai sur la super classe (pour propriété héritée)
				m=getGetterMethod(c.getSuperclass(),paramName);							
			}
			Object val = m.invoke(bean, null);
			if(val==null)
			     return null;
			//Field f  = c.getDeclaredField(paramName); f.setAccessible(true); 
			//Object val = f.get(bean);
			
			res = String.valueOf(val);
			//System.out.println("val:"+res + "type="+val.getClass().getSimpleName());
			if( val.getClass().getSimpleName().equals("Timestamp") && res.length()>10)
				res=res.substring(0,10);
			if( val.getClass().getSimpleName().equals("Date") ){
				Calendar cal = Calendar.getInstance();
				cal.setTime((Date)val);
				String chMonth = String.valueOf(cal.get(Calendar.MONTH) +1);
				if(chMonth.length()==1)
					chMonth="0"+chMonth;
				res=""+cal.get(Calendar.YEAR) +"-"+chMonth +"-"+ cal.get(Calendar.DAY_OF_MONTH);
			}
		} catch (Exception e) {
			System.err.println("echec getPropertyValue() avec paramName="+paramName);
			e.printStackTrace();
		}
		return res;
	}
	
	public static String setPropertyValue(Object bean, String propertyName,Class propertyClass,Object propertyValue) {
		String res = null;
		try {
			Class c = bean.getClass();		
			Class tabOfParamTypes[] = new Class[1];
			tabOfParamTypes[0]=propertyClass;
			Method m = null;
			try {
				m=c.getDeclaredMethod("set" + firstUpper(propertyName), tabOfParamTypes);
			} catch (Exception e) {
				// en cas d'échec , essai sur la super classe (pour propriété héritée)
				m=c.getSuperclass().getDeclaredMethod("set" + firstUpper(propertyName), tabOfParamTypes);
			}
			Object tabOfValues[] = new Object[1];
			tabOfValues[0]=propertyValue;
			m.invoke(bean, tabOfValues);
		} catch (Exception e) {
			System.err.println("echec setPropertyValue:");
			System.err.println("propertyName:"+propertyName);
			System.err.println("propertyClass:"+propertyClass.getSimpleName());
			System.err.println("propertyValue:"+propertyValue);
			System.err.println("propertyValueType:"+propertyValue.getClass().getSimpleName());
			e.printStackTrace();
		}
		return res;
	}
	
	public static Method getGetterMethod(Class c,String propertyName) throws NoSuchMethodException, SecurityException{
		return c.getDeclaredMethod("get" + firstUpper(propertyName), null);
	}
	
	public static boolean notSubObject(String typeName){
		if(typeName.equals("int")) return true;
		if(typeName.equals("long")) return true;
		if(typeName.equals("short")) return true;
		if(typeName.equals("double")) return true;
		if(typeName.equals("boolean")) return true;
		if(typeName.equals("java.lang.String")) return true;
		if(typeName.equals("java.lang.Double")) return true;
		if(typeName.equals("java.lang.Integer")) return true;
		if(typeName.equals("java.lang.Short")) return true;
		if(typeName.equals("java.lang.Long")) return true;
		if(typeName.equals("java.lang.Boolean")) return true;
		/*else*/
		return false;
	}
	
	public static Class getSubObjectClass(Field f){
		Class c=null;
		try {
			c=Class.forName(f.getType().getName());
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return c;
	}
	
	/*
	//not (no more) used
	public static Object getSubObjectInstance(Object obj,Field f){
		Object subObj=null;
		try {
			subObj = PropertyUtils.getProperty(obj, f.getName());
			// existingSubObject ? 
		} catch (Exception e) {			
			//e.printStackTrace();
			System.err.println(e.getMessage());
		} 
		if(subObj==null){
			// try new instance:
			try {
				subObj = Class.forName(f.getType().getName()).newInstance();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			} 
		}
		return subObj;		
	}*/
	
	public static String firstLower(String s){
		if(s==null)return null;
		if(s.length()==1)return s.toLowerCase();
		/*else*/
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
	public static String firstUpper(String s){
		if(s==null)return null;
		if(s.length()==1)return s.toUpperCase();
		/*else*/
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
}

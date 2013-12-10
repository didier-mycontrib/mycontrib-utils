package org.mycontrib.generic.web.html.extension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mycontrib.generic.reflection.util.ReflectionUtil;
import org.mycontrib.generic.web.annotation.SelectItemFrom;
import org.mycontrib.generic.web.dhjs.HjsDynUtil;
import org.mycontrib.generic.web.dynview.AdInputSelect;

/**
 * representation abstraite java d'une zone de liste
 *
 */

public class HjsDynInputSelectExt extends HjsDynInputExt{
	
	private List<HjjSelectItem> items=new ArrayList<HjjSelectItem>();
	
	public void addItem(HjjSelectItem sItem){
		items.add(sItem);
	}
	
	private void selectFirstItemByDefault()	{
	    boolean noSelect=true;
		if(items!=null) 
		{
			 for(HjjSelectItem item : items) {
				if(item.isSelected())noSelect=false; 
				break; 
			 }
		if(noSelect) items.get(0).setSelected(true);
		}
	}
	
	@Override
	public String toHjsString(Object bean) {
		this.value = HjsDynUtil.getEmptyButNotNullPropertyValue(bean,this.getName());
		initItemList(bean);
		StringBuffer bf=new StringBuffer();
		  if(!this.isInnerAjaxRenderOnly()) bf.append(getName()+": <span id='"+this.getName()+"' >");
		bf.append("<select size='1'  name='"+this.getName()+"' value='"+ this.value +"'");
		HjsDynAjaxRefreshExt hjsAjaxRefresh = this.getHjsAjaxRefresh();
		if(hjsAjaxRefresh!=null)
		{
		String jsFctCall="'refreshViaAjax(\""+hjsAjaxRefresh.getAjaxUrl()+"\",\""+hjsAjaxRefresh.getIdZone()+"\",this)' ";
		String strAjaxJsEventCall = " onFocus="+jsFctCall+" onChange="+jsFctCall;
		//System.out.println(strAjaxJsEventCall);
		bf.append(strAjaxJsEventCall);
		}
		bf.append(" >");
		if(items!=null) 
		 for(HjjSelectItem item : items)
		 { 
			if(item.getValue().equals(this.getValue())) 
				item.setSelected(true);
			bf.append(item.toHjsString(bean));
		 }
		bf.append("</select>");
		if(!this.isInnerAjaxRenderOnly())
		    bf.append("</span><br/>");
		return bf.toString();
	}
	
	public HjsDynInputSelectExt(){
		super();
	}
	
	public HjsDynInputSelectExt(String name,String value){
		super(name,value);
	}
	
	private void initItemList(Object bean){
		items.clear();
		AdInputSelect adInputSelect = (AdInputSelect) this.getElement();
		buildSelectedItemsFromAnnotatedList( bean,
				adInputSelect.getCollection(),adInputSelect.getItemKey(),adInputSelect.getItemLabel());
		// hjsInputSelect.addItem(new HjsSelectItem("75","Paris"));
		// hjsInputSelect.addItem(new HjsSelectItem("60","Oise"));
		
		/*
		 * if (anotAjaxOnSelect != null) {
				HjsDynAjaxRefreshExt hjsajaxRefresh = new HjsDynAjaxRefreshExt(
						"hsjDynServlet?ajax=true&bean="
								+ beanName
								+ "&newHtmlContentFor="
								+ anotAjaxOnSelect
										.refresh(),
						anotAjaxOnSelect.refresh());
				hjsInputSelect.setHjsAjaxRefresh(hjsajaxRefresh);
			}
		 */
		
	}
	
	
	/**
     * 
     * @param hjsInputSelect = liste abstraite/java (rendue plus tard en html)
     *                         dont il faut remplir les valeurs possibles 
     *                         (sous elements <option> html , HjsSelectItem en java)
     * @param bean
     * @param listeName = collection du javaBean qui contient les valeurs selectionnables
     */
	private void buildSelectedItemsFromAnnotatedList( Object bean, String listeName,String itemKey,String itemLabel) {
		try {
			Class c = bean.getClass();
			Collection listeObj = null;
			Method m = c.getDeclaredMethod("get" + ReflectionUtil.firstUpper(listeName));
			if (m != null) {				
				listeObj = (Collection) m.invoke(bean, null);
				if (listeObj != null)
					for (Object obj : listeObj) {
						if (itemKey != null && itemLabel!=null) {
							String key = ReflectionUtil.getPropertyValue(obj, itemKey);
							String label = ReflectionUtil.getPropertyValue(obj,itemLabel);
							this.addItem(new HjjSelectItem(key, label));
						} else
							this.addItem(new HjjSelectItem(obj
									.toString(), obj.toString()));
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

package org.mycontrib.generic.web.html.extension;


/** Abstract Input (html + js)
 *    ==> zone de saisie 
 *        ou bien
 *       zone de liste      
**/

public abstract class HjsDynInputExt extends HjsDynInputOrOutputExt{
	
	private HjsDynAjaxRefreshExt hjsAjaxRefresh=null;
	
	public HjsDynAjaxRefreshExt getHjsAjaxRefresh() {
		return hjsAjaxRefresh;
	}

	public void setHjsAjaxRefresh(HjsDynAjaxRefreshExt hjsAjaxRefresh) {
		this.hjsAjaxRefresh = hjsAjaxRefresh;
	}

	public HjsDynInputExt(){
		super();
	}
	
	public HjsDynInputExt(String name,String value){
		super(name,value);
	}
}

package org.mycontrib.generic.web.html.extension;

public class HjsDynAjaxRefreshExt {
	
	private String idZone;
	private String ajaxUrl;
	
	public HjsDynAjaxRefreshExt() {
		super();
	}
	public HjsDynAjaxRefreshExt(String ajaxUrl, String idZone) {
		super();
		this.ajaxUrl = ajaxUrl;
		this.idZone = idZone;
	}
	public String getIdZone() {
		return idZone;
	}
	public void setIdZone(String idZone) {
		this.idZone = idZone;
	}
	public String getAjaxUrl() {
		return ajaxUrl;
	}
	public void setAjaxUrl(String ajaxUrl) {
		this.ajaxUrl = ajaxUrl;
	}
	
	

}

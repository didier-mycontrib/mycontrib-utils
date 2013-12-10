package org.mycontrib.generic.web.dynview;

//zone de selection
public class AdInputSelect extends AdInput {
	
	private String collection=null;
	private String itemKey;
	private String itemLabel;
	
	
	public AdInputSelect() {
		super();
	}
	
	public AdInputSelect(AdElement parent, String name, String collection,String itemKey,String itemLabel) {
		super(parent, name);
		this.collection=collection;
		this.itemKey=itemKey;
		this.itemLabel=itemLabel;
	}
	
	
	public AdInputSelect(AdElement parent, String name, String collection) {
		this(parent,name,collection,null,null);
	}

	public AdInputSelect(AdElement parent, String name, AdExtension extension) {
		super(parent, name, extension);
	}

	public AdInputSelect(AdElement parent, String name) {
		super(parent, name);
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	
	
	
}

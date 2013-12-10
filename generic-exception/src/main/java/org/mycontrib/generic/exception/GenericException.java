package org.mycontrib.generic.exception;

import java.util.HashMap;
import java.util.Map;

import org.mycontrib.generic.exception.type.GenericExceptionType;

/* classe generic d'exceptions (indépendante de toute technologie)
 * unckecked (try/catch facultatif)
 * à éventuellement peaufiner par héritages (ex: NotExistException extends GenericException , ...)
 */

public class GenericException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,String> detailsMap = null; //détails supplémentaires sur l'exception (en plus du message principal)
	private GenericExceptionType exceptionType = GenericExceptionType.INTERNAL; // type d'exception (enumeration) , INTERNAL = default value
	

	public GenericException(String message) {
		super(message);
	}
	
	public GenericException(GenericExceptionType type,String message) {
		super(message);
		this.setExceptionType(type);
	}
	
	
	public GenericException(Throwable cause) {
		super(cause);
	}

	public GenericException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public GenericExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(GenericExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	

	public Map<String, String> getDetailsMap() {
		return detailsMap;
	}

	/*public void setDetailsMap(Map<String, String> detailsMap) {
		this.detailsMap = detailsMap;
	}*/
	
	public GenericException addDetail(String name,String value){
		if(this.detailsMap == null){
			this.detailsMap = new HashMap<String,String>();
		}
		this.detailsMap.put(name, value);
		return this;  // pour pouvoir enchainer (ex : throw new GenericException("...").addDetail("wrong id" , "1").addDetail("max" , "10");)
	}
	
	public String getExceptionDetail(String detailName){
		String detailValue=null;
		if(detailsMap!=null)
			detailValue=detailsMap.get(detailName);
		return detailValue;
	}
	
	private void appendExceptionTypeAndMessage(StringBuffer buf){
		buf.append("[");
		buf.append(this.exceptionType.toString());
		buf.append("] ");
		buf.append(this.getMessage());
	}
	
	private void appendAllDetails(StringBuffer buf){
		buf.append("[");
		if(this.detailsMap!=null)
		   for(String s : detailsMap.keySet()){
			   buf.append(s) ; buf.append("=") ; buf.append(detailsMap.get(s)); buf.append(" ;") ;
		   }
		buf.append("]");
	}
	
	public String getMessageWithType(){
		StringBuffer buf = new StringBuffer(32);
		appendExceptionTypeAndMessage(buf);
		return buf.toString();
	}
	
	public String getFullMessage(){
		StringBuffer buf = new StringBuffer(64);
		appendExceptionTypeAndMessage(buf);
		buf.append(" ");
		appendAllDetails(buf);
		return buf.toString();
	}
	
	public String getAllDetails(){
		StringBuffer buf = new StringBuffer(64);
		appendAllDetails(buf);
		return buf.toString();
	}

	public GenericException() {
	}

	
}

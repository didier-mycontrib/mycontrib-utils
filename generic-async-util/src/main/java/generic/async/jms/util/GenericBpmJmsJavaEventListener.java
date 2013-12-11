package generic.async.jms.util;

import generic.async.util.AsyncContext;
import generic.bpm.ProcessEventManager;

import java.util.HashMap;
import java.util.Map;

/*pris en charge par spring
 * 
 * <bean id="bpmJmsListener" class="generic.async.jms.util.GenericBpmJmsJavaEventListener" >
 *    <property name="processName" value="myAsyncProcess"/>
 *    <property name="eventName" value="statusNotif"/>
 *     <property name="className" value="tp.async.service.StatusNotif"/>
 *    <property name="objectName" value="statusObj"/>
 * </bean>
 */
public class GenericBpmJmsJavaEventListener extends GenericJmsToObjectListener{

	private ProcessEventManager processEvent;//(objet activiti ou jbpm a injecter)
	private String processName;//(valeur a injecter)
	private String eventName;//(valeur a injecter) si different de className (sans package et sans majuscule)
	private String className;//(valeur a injecter): nom de la classe de l'objet correspondant au message à recevoir
	private String objectName;//(valeur a injecter) si different de className (sans package et sans majuscule)

    //injection
	public void setProcessEvent(ProcessEventManager processEvent) {
		this.processEvent = processEvent;
	}

	//injection
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	//injection
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	private String getDefaultName(){
		String defaultName=null;
		int index = className.lastIndexOf(".");
		defaultName= className.substring(index+1);
		defaultName= defaultName.substring(0, 1).toLowerCase()+defaultName.substring(1);
		//System.out.println("defaultName:" + defaultName);
		return defaultName;
	}

	@Override
	public void OnJavaMessage(Object javaMsg,AsyncContext asyncContext) {
		try {
			System.out.println("generic - java message received for bpm process:" + javaMsg.toString());
			String processInstanceId = asyncContext.getCorrelationId();
			
			Map<String,Object> newProcessVariablesParams = new HashMap<String,Object>();
			
			objectName=(objectName!=null)?objectName:getDefaultName();
			newProcessVariablesParams.put(objectName, javaMsg);
			
			eventName=(eventName!=null)?eventName:getDefaultName();
			processEvent.messageEvent(processName, processInstanceId, eventName, newProcessVariablesParams);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public String getProcessName() {
		return processName;
	}
	

	public String getEventName() {
		return eventName;
	}

	public ProcessEventManager getProcessEvent() {
		return processEvent;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}



}
package generic.bpm;

import java.util.Map;

public interface ProcessManager extends ProcessEventManager{
	//return deployId or ... (for undeploy)
	public String deployProcessDefinition(String bpmnFileName);
	//return processInstanceId (or ...)
	public String startProcessInstance(String processName , Map<String,Object> initialProcessVariablesParams);
	
	//... undeploy ....
}

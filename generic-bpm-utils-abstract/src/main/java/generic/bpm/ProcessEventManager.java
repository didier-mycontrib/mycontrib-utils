package generic.bpm;

import java.util.Map;

public interface ProcessEventManager {
	public void signalEvent(String processName , String processInstanceId , String signalName);
	public void messageEvent(String processName , String processInstanceId , String eventName , Map<String,Object> newProcessVariablesParams);
}

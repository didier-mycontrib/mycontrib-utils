package generic.bpm.delegate;

public interface ProcessDataAccess {
	
	public Object getVariable(String varName);
	public void setVariable(String varName,Object value);
	
	public String getProcessInstanceId();

}

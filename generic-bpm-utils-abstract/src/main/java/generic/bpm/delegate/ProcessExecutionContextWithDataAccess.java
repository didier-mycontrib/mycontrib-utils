package generic.bpm.delegate;


public interface ProcessExecutionContextWithDataAccess {
	public Object doActionWithDataAccess(ProcessDataAccess pda,String actionName,Object ... args);
	public boolean evalWithDataAccess(ProcessDataAccess pda,String conditionName,Object ... args);
}

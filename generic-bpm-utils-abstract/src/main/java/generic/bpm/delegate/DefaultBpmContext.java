package generic.bpm.delegate;

import generic.bpm.delegate.annot.Action;


//public class DefaultBpmContext implements ProcessExecutionContextWithDataAccess {
public class DefaultBpmContext extends BpmContextWithAnnot {
		
		
	@Action("display")
	public Object doActionWithDataAccess(ProcessDataAccess pda,Object... args) {
		for(Object obj:args)
			System.out.print(obj.toString()+" ");
		System.out.print("\n");
		return null;
	}
	
	

}

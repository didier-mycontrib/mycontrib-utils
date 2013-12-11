package generic.process.context.itf;

import generic.process.context.instance.ProcessContextInstance;

public interface SharedProcessCtx {
	public void doActionForInstance(String actionName, ProcessContextInstance instance);
	//méthode à absolument redéfinir pour libéber certaines resources mémoires (ex:CorrelationHelper, ....)
	public void releaseResourcesOnEnd(ProcessContextInstance instance);
}

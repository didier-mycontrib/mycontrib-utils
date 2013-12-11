package generic.process.context.instance;

import generic.process.context.anot.Condition;
import generic.process.context.itf.OutboundProcessInstanceCtx;
import generic.process.context.itf.SharedProcessCtx;
import generic.process.exec.DynProcessHosting;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class ProcessContextInstance implements OutboundProcessInstanceCtx {
	
	private static Logger logger = LoggerFactory.getLogger(ProcessContextInstance.class);
	
	private transient SharedProcessCtx sharedProcessContext;
	private transient Map<String,Method> conditionMap ;
	private transient DynProcessHosting dynProcessHosting; // a injecter dans sous classe
	
	//Seules des données (spécifiques à la sous classe) seront éventuellement serialisées par activiti ou jpbm5
	
	//méthode à appeler en fin d'execution (ex: via end event listener) pour libérer les ressources
	public void releaseResourcesOnEnd(){
		sharedProcessContext.releaseResourcesOnEnd(this);
		conditionMap.clear();conditionMap=null;
		dynProcessHosting=null;
		logger.debug("releaseResourcesOnEnd");
	}
	
	public ProcessContextInstance(){
		//default constructor 
	}
	
	public ProcessContextInstance(SharedProcessCtx sharedProcessContext,DynProcessHosting dynProcessHosting){
		this.setSharedProcessContext(sharedProcessContext);
		this.setDynProcessHosting(dynProcessHosting);
	}

	public SharedProcessCtx getSharedProcessContext() {
		return sharedProcessContext;
	}
	
    //injection
	public void setSharedProcessContext(SharedProcessCtx sharedProcessContext) {
		this.sharedProcessContext = sharedProcessContext;
	}

	@Override
	public void doAction(String actionName) {
		sharedProcessContext.doActionForInstance(actionName, this);
		
	}
	
	private void initConditionMap(){
		if(conditionMap==null){
			conditionMap = new HashMap<String,Method>();
			//introspection pour trouver methode avec @Condition("conditionName")
			Method[] tabM = this.getClass().getDeclaredMethods();
			for(Method m : tabM){
				Condition conditionAnot = m.getAnnotation(Condition.class);
				if(conditionAnot!=null)
					conditionMap.put(conditionAnot.value(), m);			
			}
		}
	}
	
	private boolean invokeBooleanConditionMethod(Method m){
		boolean result=false;
		try {
			Object objRes  = m.invoke(this, (Object[]) null);
			if(objRes instanceof Boolean){
				result = ((Boolean) objRes).booleanValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("ProcessContextInstance --> called/eval : " + m.getName() + " return " + result);
		return result;
	}
	
	private boolean invokeConditionMethod(String conditionName){
		boolean res = false;
		initConditionMap(); //if necessary
		Method m = conditionMap.get(conditionName);
		if(m!=null)
			res = invokeBooleanConditionMethod(m);
		return res;
	}

	@Override
	public boolean eval(String conditionName) {
		return invokeConditionMethod(conditionName);
	}
	
	public void start() {
		dynProcessHosting.start();		
	}

	public void signalEvent(String eventName) {
		dynProcessHosting.signalEvent(eventName);		
	}

	public void setDynProcessHosting(DynProcessHosting dynProcessHosting) {		
		this.dynProcessHosting = dynProcessHosting;
		this.dynProcessHosting.setOutboundProcessInstanceContext(this);
	}
	
	

}

package generic.bpm.delegate;

import generic.bpm.delegate.annot.Action;
import generic.bpm.delegate.annot.Condition;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * appel automatique (sur sous classe):
 * @Action("action1") selon correspondance avec actionName
 * public Object action1abcd(ProcessDataAccess pda,Object ... args);
 * 
 * et appel automatique equivalent pour:
 * @Condition("condition1") selon correspondance avec conditionName
 * public boolean eval1xyz(ProcessDataAccess pda,Object ... args); 
 */

public class BpmContextWithAnnot implements ProcessExecutionContextWithDataAccess {
	
	private static Logger logger = LoggerFactory.getLogger(BpmContextWithAnnot.class);
	

	private Map<String,Method> actionMap ;
	private Map<String,Method> conditionMap ;

	private void initActionMap(){
		if(actionMap==null){
			actionMap = new HashMap<String,Method>();
			//introspection pour trouver methode avec @Action("actionName")
			Method[] tabM = this.getClass().getDeclaredMethods();
			for(Method m : tabM){
				Action actionAnot = m.getAnnotation(Action.class);
				if(actionAnot!=null)
					actionMap.put(actionAnot.value(), m);			
			}
		}
	}
	
	
	@Override
	public Object doActionWithDataAccess(ProcessDataAccess pda,
			String actionName, Object... args) {
		Object result=null;
		logger.debug("doActionWithDataAccess:" + actionName + ",instance:" + pda.getProcessInstanceId());
		result = invokeActionMethod(pda,actionName,args);//if exists
		return result;
	}
	
	private Object invokeProcessMethodForInstance(ProcessDataAccess pda,Method m,Object... args){
		Object result=null;
		logger.debug("BpmContextWithAnnot --> call : " + m.getName());
		Object[] argsForInvoke=new Object[args==null?1:2]; 
		argsForInvoke[0]=pda;
		if(args!=null)
			  argsForInvoke[1]=args;
		
		try {
			result=m.invoke(this, argsForInvoke);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Object invokeActionMethod(ProcessDataAccess pda,
			String actionName, Object... args){
		initActionMap(); //if necessary
		Method m = actionMap.get(actionName);
		if(m!=null)
			return invokeProcessMethodForInstance(pda,m,args);
		else return null;
	}
	
	

	@Override
	public boolean evalWithDataAccess(ProcessDataAccess pda, String conditionName,
			Object... args) {
		boolean bResult=invokeConditionMethod(pda, conditionName,args);
		return bResult;
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
	
	private boolean invokeBooleanConditionMethod(ProcessDataAccess pda,Method m,Object... args){
		boolean result=false;
		try {	
			Object[] argsForInvoke=new Object[args==null?1:2]; 
			argsForInvoke[0]=pda;
			if(args!=null)
				  argsForInvoke[1]=args;
			Object objRes  = m.invoke(this, argsForInvoke);
			if(objRes instanceof Boolean){
				result = ((Boolean) objRes).booleanValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("BpmContextWithAnnot --> called/eval : " + m.getName() + " return " + result);
		return result;
	}
	
	private boolean invokeConditionMethod(ProcessDataAccess pda, String conditionName,Object... args){
		boolean res = false;
		initConditionMap(); //if necessary
		Method m = conditionMap.get(conditionName);
		if(m!=null)
			res = invokeBooleanConditionMethod(pda,m,args);
		return res;
	}


}

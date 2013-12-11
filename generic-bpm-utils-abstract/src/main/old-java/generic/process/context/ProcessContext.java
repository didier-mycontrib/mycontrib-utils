package generic.process.context;

import generic.process.context.anot.Action;
import generic.process.context.anot.Post;
import generic.process.context.anot.Pre;
import generic.process.context.instance.ProcessContextInstance;
import generic.process.context.itf.SharedProcessCtx;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ProcessContext implements SharedProcessCtx {

	private static Logger logger = LoggerFactory.getLogger(ProcessContext.class);
	
	private Map<String,Method> actionMap ;
	private Map<String,Method> preActionMap ;
	private Map<String,Method> postActionMap ;
	
	
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
	
	private void initPreActionMap(){
		if(preActionMap==null){
			preActionMap = new HashMap<String,Method>();
			//introspection pour trouver methode avec @Pre(action="actionName")
			Method[] tabM = this.getClass().getDeclaredMethods();
			for(Method m : tabM){
				Pre preAnot = m.getAnnotation(Pre.class);
				if(preAnot!=null)
					preActionMap.put(preAnot.action(), m);					
			}
		}
	}
	
	private void initPostActionMap(){
		if(postActionMap==null){
			postActionMap = new HashMap<String,Method>();
			//introspection pour trouver methode avec @Pre(action="actionName")
			Method[] tabM = this.getClass().getDeclaredMethods();
			for(Method m : tabM){
				Post postAnot = m.getAnnotation(Post.class);
				if(postAnot!=null)
					postActionMap.put(postAnot.action(), m);					
			}
		}
	}


	@Override
	public void doActionForInstance(String actionName,
			ProcessContextInstance instance) {
		logger.debug("ProcessContext-doActionForInstance:" + actionName + ",instance:" + instance);
		invokePreActionMethod(actionName,instance);//if exists
		invokeActionMethod(actionName,instance);//if exists
		invokePostActionMethod(actionName,instance);//if exists
	
	}
	
	private void invokeProcessMethodForInstance(Method m,ProcessContextInstance processInstanceContext){
		logger.debug("ProcessContext --> call : " + m.getName());
		Object[] args=new Object[1]; args[0]=processInstanceContext;
		try {
			m.invoke(this, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void invokeActionMethod(String actionName,
			ProcessContextInstance instance){
		initActionMap(); //if necessary
		Method m = actionMap.get(actionName);
		if(m!=null)
			invokeProcessMethodForInstance(m,instance);
	}
	
	private void invokePreActionMethod(String actionName,
			ProcessContextInstance instance){
		initPreActionMap(); //if necessary
		Method m = preActionMap.get(actionName);
		if(m!=null)
				invokeProcessMethodForInstance(m,instance);				
	}
	
	private void invokePostActionMethod(String actionName,
			ProcessContextInstance instance){
		initPostActionMap(); //if necessary
		Method m = postActionMap.get(actionName);
		if(m!=null)
				invokeProcessMethodForInstance(m,instance);	
	}

}

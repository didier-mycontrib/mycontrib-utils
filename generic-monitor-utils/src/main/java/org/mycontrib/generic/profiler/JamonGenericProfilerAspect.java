package org.mycontrib.generic.profiler;

import org.aspectj.lang.ProceedingJoinPoint;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * JamonGenericProfilerAspect est une classe d'aspect pour Spring-AOP
 * Elle enregistre les temps d'execution des méthodes via l'api open source "JaMon" .
 * (pour récupérer les statistiques --> on peut  partir de MonitorFactory.getMonitor("signature methode", "ms."); 
 * ou de MonitorFactory.iterator() ou encore de MonitorFactory.getReport();
 * 
 * @author Didier DEFRANCE
 *
 * exemple de configuration Spring :
 * 
 * <bean id="jamonGenericProfilerAspectBean" class="org.mycontrib.generic.profiler.JamonGenericProfilerAspect"></bean>
	<aop:config>
		
		<aop:pointcut id="execution_methodes_generic_dao"
			expression="execution(* org.mycontrib.generic.persistence.*.*(..))" />
		<aop:pointcut id="execution_methodes_dao"
			expression="execution(* tp.myapp.minibank.impl.persistence.dao.jpa.*.*(..))" />	
		
		<aop:aspect id="myProfilerAspect" ref="jamonGenericProfilerAspectBean">
			<aop:around method="doProfiling" pointcut-ref="execution_methodes_generic_dao" />
		    <aop:around method="doProfiling" pointcut-ref="execution_methodes_dao" />		
		</aop:aspect>
	</aop:config>	
 *
 */

public class JamonGenericProfilerAspect implements GenericProfilerAspect {
	

	public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
		 // ou pjp.getSignature().toString() 
		 Monitor monitor = MonitorFactory.start(pjp.getSignature().toShortString());
			Object objRes = pjp.proceed();
		 monitor.stop();
			return objRes;
	}


}

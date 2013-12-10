package org.mycontrib.generic.profiler;

import org.aspectj.lang.ProceedingJoinPoint;

public interface GenericProfilerAspect {

	public abstract Object doProfiling(ProceedingJoinPoint pjp)
			throws Throwable;

}
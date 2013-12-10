package org.mycontrib.generic.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DynView {
	String name() default "";
	
	enum ViewType{ IN, OUT, IN_OUT }
    ViewType viewType() default ViewType.IN_OUT;
}

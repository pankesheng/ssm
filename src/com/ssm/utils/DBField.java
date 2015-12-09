package com.ssm.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DBField {
	
	String name() default "";
	
	String comment() default "";
	
	String type() default "";
	
	int length() default 11;
	/**
	 * 是否允许空
	 * @return
	 */
	boolean allowNull() default true;
	
	String  Default() default "";
	
}

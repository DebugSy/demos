package com.java.demo.JarScan;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description for a Carpo UDF
 * 
 * @author Zoom
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UDF {

	// default function name
	String name();

	// function type: should be UDF1, UDF2, UDF3, UDF4, UDF5
	String fnType();

	// data type for return value
	String returnType();

}

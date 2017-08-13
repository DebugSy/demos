package com.shi.java.SpringSource;

import java.lang.annotation.*;

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

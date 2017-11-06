package com.shi.java.Python;

import org.python.util.PythonInterpreter;

/**
 * Created by DebugSy on 2017/9/4.
 */
public class FirstJavaScript {
	public static void main(String args[]) {

		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.execfile("E:\\GitHub\\demos\\java-demo\\src\\main\\java\\com\\shi\\java\\Python\\input.py");
	}// main
}

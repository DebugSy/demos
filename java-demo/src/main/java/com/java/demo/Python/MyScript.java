package com.java.demo.Python;

import org.python.core.*;
import org.python.util.PythonInterpreter;

/**
 * Created by DebugSy on 2017/9/4.
 *
 * 目的：利用java调用python，将spark sql的dataframe对象传给python，然后处理完数据以后将结果返回给java程序
 */
public class MyScript {

	public static void main(String[] args) {
		try {
//			String[] arg = new String[]{"1","2","3"};
			scriptParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void scriptParam() throws Exception {
		PythonInterpreter interpreter = new PythonInterpreter();
//		String fileUrlPath ="E:\\GitHub\\demos\\java-demo\\src\\main\\java\\com\\shi\\java\\Python";
//		String scriptName ="myscript";
//		interpreter.exec("import sys\n" + "import os \n" + "sys.path.append('" + fileUrlPath + "')\n"+ "from "+scriptName+" import * \n");
		interpreter.execfile("E:/GitHub/demos/java-demo/src/main/java/com/shi/java/Python/myscript.py");
		String funcName ="myFunction";
		PyObject someFunc =interpreter.get(funcName);
		if (someFunc == null) {
			throw new Exception("Could notfind Python function: " + funcName);
		}
		try {
			PyObject pyObject = someFunc.__call__(new PyInteger(3), new PyInteger(4), new PyInteger(5));
			System.out.println(pyObject);
		} catch (PyException e) {
			e.printStackTrace();
		}
	}
}


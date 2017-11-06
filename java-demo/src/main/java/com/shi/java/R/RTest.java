package com.shi.java.R;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

import java.util.Arrays;

/**
 * Created by DebugSy on 2017/9/4.
 */
public class RTest {

	public static void main(String[] args) {
		RTest test2 = new RTest();
		test2.method2();
	}

	/**
	 * 加载R文件，调用函数
	 */
	@SuppressWarnings("deprecation")
	public void method2(){
		// R文件全路径
		String filePath = "E:/R/WorkSpace/1.R";

		// 初始化R解析类
		Rengine engine = new Rengine(null, false, null);
		System.out.println("Rengine created, waiting for R");

		// 等待解析类初始化完毕
		if (!engine.waitForR()) {
			System.out.println("Cannot load R");
			return;
		}
		// 将文件全路径复制给R中的一个变量
		engine.assign("fileName", filePath);
		// 在R中执行文件。执行后，文件中的两个函数加载到R环境中，后续可以直接调用
		engine.eval("source(fileName)");
		System.err.println("R文件执行完毕");

		{
			// 定义一个数组，与R中c1集合对应
			String[] arr1 = new String[]{"a", "b", "c"};
			// 将数组复制给R中的变量c1。R中变量无需预先定义
			engine.assign("c1", arr1);

			// 定义一个数组，与R中c2集合对应
			double[] arr2 = new double[]{1, 2, 3};
			// 将数组复制给R中的变量c2
			engine.assign("c2", arr2);

			// 将c1 c2连接为一个集合（R中的数据集，类似java的list），赋值给一个变量
			engine.eval("x <- data.frame(c1, c2)");
			// 将一个数值保存到一个变量中
			engine.eval("y <- 10");

			// 入参为list，出参为list。调用R中函数，将结果保存到一个对象中。
			REXP rexp = engine.eval("test_param_list(x, y)");
			System.out.println("rexp : " + rexp);
			System.out.println("type : " + rexp.getType());

			// 解析rexp对象，转换数据格式

			// list的标题
			RList list = rexp.asList();
			String[] key = list.keys();
			System.err.println("Arrays : " + Arrays.toString(key));
			if(key != null){
				int i = 0;
				while (i < key.length){
					i++;
				}
			}
			// list的数据
			RVector v =  rexp.asVector();
			for(int i=0; i<v.size(); i++){
				REXP rexpTemp = (REXP) v.get(i);
				if(REXP.INTSXP == rexpTemp.rtype){
					int[] arr = rexpTemp.asIntArray();
					System.err.println(Arrays.toString(arr));
				} else if(REXP.STRSXP == rexpTemp.rtype){
					String[] arr = rexpTemp.asStringArray();
					System.err.println(Arrays.toString(arr));
				} else if(REXP.REALSXP == rexpTemp.rtype){
					double[] arr = rexpTemp.asDoubleArray();
					System.err.println(Arrays.toString(arr));
				}
			}
			System.err.println("---------------2");
		}
		{
			engine.eval("x1 <- 1");
			engine.eval("y1 <- 10");
			REXP rexp = engine.eval("test_param(x1, y1)");
			System.out.println("result:" + rexp.asDouble());
		}

		{

			REXP rexp = engine.eval("test_no_param()");
			System.out.println("rexp : " + rexp);

			Person person = new Person("xiaoming", 20);
			REXP rJavaRef1 = new REXP(18, person);
			REXP rJavaRef2 = engine.createRJavaRef(person);
			engine.assign("p1", rJavaRef1);
			engine.assign("p2", rJavaRef2);
			REXP eval = engine.eval("test_person(p2)");
			System.out.println("eval : " + eval);
		}

		engine.stop();
	}


}

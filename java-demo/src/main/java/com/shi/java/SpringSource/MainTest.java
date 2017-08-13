package com.shi.java.SpringSource;

/**
 * Created by DebugSy on 2017/8/11.
 */
public class MainTest {

	public static void main(String[] args) {
		ShiyAnnotationConfigApplicationContext context = new ShiyAnnotationConfigApplicationContext();
		context.scan("D:\\tmp\\jars");
	}

}

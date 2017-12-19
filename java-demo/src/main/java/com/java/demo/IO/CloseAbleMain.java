package com.java.demo.IO;

import java.io.IOException;

/**
 * Created by DebugSy on 2017/8/3.
 *
 * 	测试CloseAble接口
 */
public class CloseAbleMain {

	public static void main(String[] args) {
		try(CloseAbleTest closeAbleTest = new CloseAbleTest()){
			closeAbleTest.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

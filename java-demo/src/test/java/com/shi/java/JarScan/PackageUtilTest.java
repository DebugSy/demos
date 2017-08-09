package com.shi.java.JarScan;

import java.util.Set;

/**
 * Created by DebugSy on 2017/8/9.
 */
public class PackageUtilTest {

	public static void main(String[] args) {
		Set<String> packageClass = PackageUtil.findPackageClass("com.shi.java.JarScan");
		for (String clazz : packageClass){
			System.out.println(clazz);
		}
	}

}

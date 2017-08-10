package com.shi.java.JarScan;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by DebugSy on 2017/8/9.
 */
public class PackageScanUtilTest {



	@Test
	public void testFindClassAllMethods(){
		Set<Method> methods = PackageScanUtil.findClassAllMethods("E:\\tmp\\jars");
		System.out.println("Method:");
		for (Method method : methods){
			System.out.println(method.getName());
		}
	}

	@Test
	public void testJarFile() {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile("E:\\GitHub\\demos\\java-demo\\target\\java-demo-1.0-SNAPSHOT.jar");
		} catch (Exception e) {
			System.out.println(e);
		}
		Enumeration<JarEntry> files = jarFile.entries();
		while (files.hasMoreElements()) {
			JarEntry entry = (JarEntry) files.nextElement();
			if (entry.getName().endsWith(".class")) {
				System.out.println(entry.getName().replace("/", ".").substring(0, entry.getName().lastIndexOf(".")));
			}
		}
		System.out.println();
	}

	@Test
	public void testFindJarsClasses(){
		Set<String> jars = PackageScanUtil.findJarsClasses("E:\\tmp\\jars");
		for (String str : jars){
			System.out.println(str);
		}
	}

}

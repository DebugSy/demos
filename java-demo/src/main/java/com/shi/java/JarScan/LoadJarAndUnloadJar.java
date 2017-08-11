package com.shi.java.JarScan;

import sun.misc.ClassLoaderUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by DebugSy on 2017/8/11.
 */
public class LoadJarAndUnloadJar {

	public static void main(String[] args) throws Exception {
		URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:E:\\tmp\\jars\\b-1.0-SNAPSHOT.jar")});
		Class classStudentServiceImpl = urlClassLoader.loadClass("com.shiy.test.B");
		Method method = classStudentServiceImpl.getMethod("test2", new Class[]{});
		Constructor localConstructor = classStudentServiceImpl.getConstructor(new Class[]{});
		Object instance = localConstructor.newInstance(new Object[]{});
		Object ret = method.invoke(instance);
		System.out.println(ret);

		ClassLoaderUtil.releaseLoader(urlClassLoader);
	}

}

package com.shi.java.JarScan;

import com.datapps.workflow.functions.UDF;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.ClassLoaderUtil;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by DebugSy on 2017/8/11.
 */
public class ScanJarClassAndMethod {

	private Set<Class> includeMethodFilter = new HashSet<>();

	private Set<Class> includeClassFilter = new HashSet<>();

	private Set<Method> matchMethod = new HashSet<>();

	private Set<Class> scanClazz = new HashSet<>();

	public void doScan(String basePackage) {
		File rootPackage = new File(basePackage);
		FileFilter fileFilter = new JarFileFilter();
		File[] jars = rootPackage.listFiles(fileFilter);
		JarFile jarFile = null;
		List<URL> urls = new ArrayList<>();
		List<String> clazzs = new ArrayList<>();
		try {
			for (File jar : jars) {
				jarFile = new JarFile(jar);
				Enumeration<JarEntry> files = jarFile.entries();
				while (files.hasMoreElements()) {
					JarEntry entry = files.nextElement();
					if (entry.getName().endsWith(".class")) {
						clazzs.add(entry.getName().replace("/", ".").substring(0, entry.getName().lastIndexOf(".")));
					}
				}
				urls.add(jar.toURI().toURL());
			}

			URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
			for (String clazzName : clazzs) {
				Class clazz = urlClassLoader.loadClass(clazzName);
				if (match(clazz, includeClassFilter)){
					scanClazz.add(clazz);
				}
				Method[] methods = clazz.getMethods();
				for (Method method : methods) {
					if (match(method, includeMethodFilter)) {
						matchMethod.add(method);
					}
				}
			}
			ClassLoaderUtil.releaseLoader(urlClassLoader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean match(Class clazz, Set<Class> filters) {
		for (Class clazzFilter : filters) {
			if (clazz.isAnnotationPresent(clazzFilter)) {
				return true;
			}
		}
		return false;
	}

	public boolean match(Method method, Set<Class> filters) {
		for (Class clazzFilter : filters) {
			if (method.isAnnotationPresent(clazzFilter)) {
				return true;
			}
		}
		return false;
	}

	public void registerMethodFilter(Class clazz) {
		this.includeMethodFilter.add(clazz);
	}

	public void registerClassFilter(Class clazz) {
		this.includeClassFilter.add(clazz);
	}

	public static void main(String[] args) {
		ScanJarClassAndMethod scanJarClassAndMethod = new ScanJarClassAndMethod();
		scanJarClassAndMethod.registerClassFilter(UDF.class);
		scanJarClassAndMethod.registerMethodFilter(Autowired.class);
		scanJarClassAndMethod.doScan("E:\\tmp\\jars");
		Set<Method> matchMethod = scanJarClassAndMethod.getMatchMethod();
		for (Method method : matchMethod) {
			System.out.println(method);
		}

		Set<Class> scanClazz = scanJarClassAndMethod.getScanClazz();
		for (Class clazz : scanClazz) {
			System.out.println(clazz);
		}
	}

	public Set<Method> getMatchMethod() {
		return matchMethod;
	}

	public Set<Class> getScanClazz() {
		return scanClazz;
	}
}

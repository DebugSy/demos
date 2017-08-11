package com.shi.java.JarScan;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;
import sun.misc.ClassLoaderUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包扫描工具，根据package路径，加载class
 */
public class PackageScanUtil {

	private final static Logger logger = LoggerFactory.getLogger(PackageScanUtil.class);
	//扫描  scanPackages 下的文件的匹配符
	protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.jar";

	private final static String PACKAGE_PREFIX = "file:";

	private final static String CLASSPATH_PREFIX = "classpath*:";

	/**
	 * 结合spring的类扫描方式
	 * 根据需要扫描的包路径，获取所有method
	 * 仅返回public方法，如果方法是非public类型的，不会被返回
	 * 可以扫描工程下的class文件及jar中的class文件
	 *
	 * @param scanPackages
	 * @return
	 */
	public static Set<Method> findClassAllMethods(String scanPackages) {
		//获取所有的类
		Set<String> clazzSet = findJarsClasses(scanPackages);
		Set<Method> methods = new HashSet<Method>();
		for (String clazz : clazzSet) {
			try {
				clazz = "com.shiy.test.B";
				File file = new File("E:/tmp/jars/b-1.0-SNAPSHOT.jar");
				URL url = file.toURI().toURL();
				URLClassLoader loader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
				Class<?> aClass = loader.loadClass(clazz);
				aClass.newInstance();
				Class<?> aClass1 = Class.forName(aClass.getName());
				System.out.println(aClass1.getName());
				System.out.println("aClass:" + aClass.getName());
				Set<Method> ms = findAllMethods(clazz);
				if (ms != null && !ms.isEmpty()) {
					methods.addAll(ms);
				}
			} catch (ClassNotFoundException ignore) {
				ignore.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		return methods;
	}


	/**
	 * 排重，避免多次扫描
	 *
	 * @param scanPackages
	 * @return 返回检查后有效的路径集合
	 */
	private static Set<String> checkPackage(String scanPackages) {
		if (StringUtils.isBlank(scanPackages)) {
			return Collections.EMPTY_SET;
		}
		Set<String> packages = new HashSet<String>();
		//排重路径
		Collections.addAll(packages, scanPackages.split(","));
		return packages;
	}

	/**
	 * 把action下面的所有method遍历一次，标记他们是否需要进行敏感词验证
	 * 如果需要，放入cache中
	 *
	 * @param fullClassName
	 */
	public static Set<Method> findAllMethods(String fullClassName) throws ClassNotFoundException {
		Set<Method> methodSet = new HashSet<Method>();
		Class<?> clz = Class.forName(fullClassName);
		Method[] methods = clz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getModifiers() != Modifier.PUBLIC) {
				continue;
			}
			methodSet.add(method);
		}
		return methodSet;
	}

	private static Set<String> getJarAllClasses(File file){
		Set<String> classes = new HashSet<>();
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(file);
			Enumeration<JarEntry> files = jarFile.entries();
			while (files.hasMoreElements()) {
				JarEntry entry = files.nextElement();
				if (entry.getName().endsWith(".class")) {
					String aClass = entry.getName().replace("/", ".").substring(0, entry.getName().lastIndexOf("."));
					classes.add(aClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	public static Set<String> findJarsClasses(String basePath) {
		Set<String> jarAllClasses = new HashSet<>();
		if (StringUtils.isBlank(basePath)) {
			return Collections.EMPTY_SET;
		}
		//验证及排重包路径,避免父子路径多次扫描
		Set<String> packages = checkPackage(basePath);
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		for (String basePackage : packages) {
			if (StringUtils.isBlank(basePackage)) {
				continue;
			}
			String packageSearchPath = PACKAGE_PREFIX +
					org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
			try {
				Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
				for (Resource resource : resources) {
					File file = resource.getFile();
					jarAllClasses.addAll(getJarAllClasses(file));
				}
			} catch (Exception e) {
				logger.error("获取包下面的类信息失败,package:" + basePackage, e);
			}

		}
		return jarAllClasses;
	}

	public static void main(String[] args) {
		Set<Method> methods = PackageScanUtil.findClassAllMethods("E:\\tmp\\jars");
		for (Method method : methods){
			System.out.println(method.getName());
		}

		Set<String> jarsClasses = PackageScanUtil.findJarsClasses("E:\\tmp\\jars");
		for (String jar : jarsClasses){
			System.out.println(jar);
		}
	}

}
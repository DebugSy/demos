package com.shi.java.JarScan;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
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
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
//		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//获取所有的类
		Set<String> clazzSet = findJarsClasses(scanPackages);
		Set<Method> methods = new HashSet<Method>();
		//遍历类，查询相应的annotation方法
		for (String clazz : clazzSet) {
			try {
				File file = new File("E:\\tmp\\jars\\grassland-cli-3.0.jar");
				URL url = file.toURI().toURL();
				URLClassLoader loader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
				System.out.println("clazz:" + clazz);
				Class<?> aClass = loader.loadClass(clazz);
				System.out.println("aClass:" + aClass.getName());
				Set<Method> ms = findAllMethods(clazz);
				if (ms != null && !ms.isEmpty()) {
					methods.addAll(ms);
				}
			} catch (ClassNotFoundException ignore) {
				ignore.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return methods;
	}

	/**
	 * 根据扫描包的,查询下面的所有类
	 *
	 * @param scanPackages 扫描的package路径
	 * @return
	 */
	public static Set<String> findPackageClass(String scanPackages) {
		if (StringUtils.isBlank(scanPackages)) {
			return Collections.EMPTY_SET;
		}
		//验证及排重包路径,避免父子路径多次扫描
		Set<String> packages = checkPackage(scanPackages);
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
		Set<String> clazzSet = new HashSet<String>();
		for (String basePackage : packages) {
			if (StringUtils.isBlank(basePackage)) {
				continue;
			}
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
			try {
				Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
				for (Resource resource : resources) {
					//检查resource，这里的resource都是class
					String clazz = loadClassName(metadataReaderFactory, resource);
					clazzSet.add(clazz);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return clazzSet;
	}

	/**
	 * 加载资源，根据resource获取className
	 *
	 * @param metadataReaderFactory spring中用来读取resource为class的工具
	 * @param resource              这里的资源就是一个Class
	 * @throws IOException
	 */
	private static String loadClassName(MetadataReaderFactory metadataReaderFactory, Resource resource) throws IOException {
		try {
			if (resource.isReadable()) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				if (metadataReader != null) {
					return metadataReader.getClassMetadata().getClassName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	@UDF(name = "testUDF", fnType = "UDF", returnType = "string")
	public void testUDF(){
		System.out.println("udf");
	}

	@UDF(name = "testUDF1", fnType = "UDF1", returnType = "string1")
	public void testUDF1(){
		System.out.println("udf1");
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
	}

}
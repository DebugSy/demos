package com.java.demo.SpringSource;

import com.java.demo.JarScan.UDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by DebugSy on 2017/8/14.
 */
public class GrasslandUtils<T> {

	private static final Logger logger = LoggerFactory.getLogger(GrasslandUtils.class);

	private Environment environment;

	static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	private String resourcePattern = DEFAULT_RESOURCE_PATTERN;

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

	private final List<Class> includeFiltersForBean = new LinkedList<Class>();

	private final List<Class> includeFiltersForMethod = new LinkedList<Class>();

	public GrasslandUtils(boolean useDefaultFilters, Environment environment) {
		if (useDefaultFilters) {
			registerClassFilters(UDF.class);
			registerMethodFilters(Autowired.class);
		}
		this.environment = environment;
	}

	public void registerClassFilters(Class<? extends Annotation> annotationClass) {
		this.includeFiltersForBean.add(annotationClass);
	}

	public void registerMethodFilters(Class<? extends Annotation> annotationClass) {
		this.includeFiltersForMethod.add(annotationClass);
	}

	public Set<String> findCandidateMethods(String basePackage){
		Set<String> methodName = new LinkedHashSet<String>();
		try {
			String packageSearchPath = "file:" +
					resolveBasePackage(basePackage) + "/" + this.resourcePattern;
			Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
			for (Resource resource : resources) {
				if (resource.isReadable()) try {
					MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
					for (Class clazz: this.includeFiltersForMethod) {
						AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
						if (metadata.hasAnnotatedMethods(clazz.getName())){
							Set<MethodMetadata> methodMetadatas = metadata.getAnnotatedMethods(clazz.getName());
							for (MethodMetadata methodMetadata : methodMetadatas){
								methodName.add(methodMetadata.getDeclaringClassName()+"."+methodMetadata.getMethodName());
							}
						}
					}
				} catch (Throwable ex) {
					throw new BeanDefinitionStoreException(
							"Failed to read candidate component class: " + resource, ex);
				}
			}
		} catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return methodName;
	}

	public Set<String> findCandidateComponents(String basePackage) {
		Set<String> candidates = new LinkedHashSet<String>();
		try {
			String packageSearchPath = "file:" +
					resolveBasePackage(basePackage) + "/" + this.resourcePattern;
			Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					try {
						MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
						for (Class clazz: this.includeFiltersForBean) {
							AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
							if (metadata.hasAnnotation(clazz.getName())){
								candidates.add(metadata.getClassName());
							}
						}
					} catch (Throwable ex) {
						throw new BeanDefinitionStoreException(
								"Failed to read candidate component class: " + resource, ex);
					}
				}
			}
		} catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return candidates;
	}

	/**
	 * 包路径转换
	 * @param basePackage
	 * @return
	 */
	protected String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(this.environment.resolveRequiredPlaceholders(basePackage));
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
	}

}

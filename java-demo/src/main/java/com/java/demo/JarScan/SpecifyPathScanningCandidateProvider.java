package com.java.demo.JarScan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by DebugSy on 2017/8/9.
 */
public class SpecifyPathScanningCandidateProvider {

	private final static Logger logger = LoggerFactory.getLogger(SpecifyPathScanningCandidateProvider.class);

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

	static final String DEFAULT_RESOURCE_PATTERN = "/**/*.class";

	private String resourcePattern = DEFAULT_RESOURCE_PATTERN;

	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		try {
			String s = SystemPropertyUtils.resolvePlaceholders(basePackage);
			System.out.println(SpecifyPathScanningCandidateProvider.class.getResource("/"));
			String packageSearchPath = s;
			System.out.println(packageSearchPath + "\\*");
			Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath + resourcePattern);

			boolean traceEnabled = logger.isTraceEnabled();
			boolean debugEnabled = logger.isDebugEnabled();
			for (Resource resource : resources) {
				if (traceEnabled) {
					logger.trace("Scanning " + resource);
				}
				if (resource.isReadable()) {
					try {
						MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
						System.out.println(metadataReader.getClassMetadata().getClassName());
					}
					catch (Throwable ex) {
						throw new BeanDefinitionStoreException(
								"Failed to read candidate component class: " + resource, ex);
					}
				}
				else {
					if (traceEnabled) {
						logger.trace("Ignored because not readable: " + resource);
					}
				}
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return candidates;
	}

//	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//		return (beanDefinition.getMetadata().isConcrete() && beanDefinition.getMetadata().isIndependent());
//	}
//
//	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
//		for (TypeFilter tf : this.excludeFilters) {
//			if (tf.match(metadataReader, this.metadataReaderFactory)) {
//				return false;
//			}
//		}
//		for (TypeFilter tf : this.includeFilters) {
//			if (tf.match(metadataReader, this.metadataReaderFactory)) {
//				return isConditionMatch(metadataReader);
//			}
//		}
//		return false;
//	}
//
//	private boolean isConditionMatch(MetadataReader metadataReader) {
//		if (this.conditionEvaluator == null) {
//			this.conditionEvaluator = new ShiyConditionEvaluator(getRegistry(), getEnvironment(), getResourceLoader());
//		}
//		return !this.conditionEvaluator.shouldSkip(metadataReader.getAnnotationMetadata());
//	}

}

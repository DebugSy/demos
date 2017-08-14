package com.shi.java.SpringSource;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.util.Assert;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by DebugSy on 2017/8/14.
 */
public class GrasslandScanner extends GrasslandUtils {

	private final BeanDefinitionRegistry registry;

	public GrasslandScanner(BeanDefinitionRegistry registry) {
		this(registry, true);
	}

	public GrasslandScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
		this(registry, useDefaultFilters, getOrCreateEnvironment(registry));
	}

	public GrasslandScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
		super(useDefaultFilters, environment);

		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		this.registry = registry;

		// Determine ResourceLoader to use.
		if (this.registry instanceof ResourceLoader) {
			setResourceLoader((ResourceLoader) this.registry);
		}
	}

	protected Set<String> doScan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		Set<String> beanDefinitions = new LinkedHashSet<String>();
		for (String basePackage : basePackages) {
			Set<String> candidates = findCandidateComponents(basePackage);
			for (String candidate : candidates) {
				beanDefinitions.add(candidate);
			}
		}
		return beanDefinitions;
	}

	private static Environment getOrCreateEnvironment(BeanDefinitionRegistry registry) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		if (registry instanceof EnvironmentCapable) {
			return ((EnvironmentCapable) registry).getEnvironment();
		}
		return new StandardEnvironment();
	}

	protected Set<String> doScanMethod(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		Set<String> beanDefinitions = new LinkedHashSet<String>();
		for (String basePackage : basePackages) {
			Set<String> methods = findCandidateMethods(basePackage);
			for (String method: methods) {
				beanDefinitions.add(method);
			}
		}
		return beanDefinitions;
	}

}

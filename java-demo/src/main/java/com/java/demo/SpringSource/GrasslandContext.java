package com.java.demo.SpringSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * Created by DebugSy on 2017/8/14.
 */
public class GrasslandContext extends GenericApplicationContext {

	private final GrasslandScanner scanner;

	public GrasslandContext() {
		this.scanner = new GrasslandScanner(this);
	}

	public Set<String> scan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
//		scanner.registerDefaultFilters(Component.class);
		scanner.registerMethodFilters(Autowired.class);
		Set<String> names = this.scanner.doScan(basePackages);
		return names;
	}

	public Set<String> scanMethod(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
//		scanner.registerDefaultFilters(Component.class);
		scanner.registerMethodFilters(Autowired.class);
		Set<String> names = this.scanner.doScanMethod(basePackages);
		return names;
	}

}

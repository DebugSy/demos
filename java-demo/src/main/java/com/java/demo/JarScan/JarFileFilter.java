package com.java.demo.JarScan;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by DebugSy on 2017/8/14.
 */
public class JarFileFilter implements FileFilter{
	@Override
	public boolean accept(File pathname) {
		if (pathname.getName().endsWith(".jar"))
			return true;
		return false;
	}
}

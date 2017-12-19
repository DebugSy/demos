package com.java.demo.SpringSorce;

import com.java.demo.SpringSource.GrasslandContext;
import org.junit.Test;

import java.util.Set;

/**
 * Created by DebugSy on 2017/8/14.
 */
public class SpringSourceTest {

	@Test
	public void testSpringSource(){
		GrasslandContext grasslandContext = new GrasslandContext();
		Set<String> names = grasslandContext.scan("E:\\tmp\\jars");
		for (String name : names){
			System.out.println("scan bean : " + name);
		}
	}

	@Test
	public void testFindCandidateMethods(){
		GrasslandContext grasslandContext = new GrasslandContext();
		Set<String> names = grasslandContext.scanMethod("E:\\tmp\\jars");
		for (String name : names){
			System.out.println("scan method : " + name);
		}
	}

}

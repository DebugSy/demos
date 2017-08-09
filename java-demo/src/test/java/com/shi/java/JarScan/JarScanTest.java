package com.shi.java.JarScan;

/**
 * Created by DebugSy on 2017/8/9.
 */
public class JarScanTest {

	public static void main(String[] args) {
		SpecifyPathScanningCandidateComponentProvider scan = new SpecifyPathScanningCandidateComponentProvider();
		scan.findCandidateComponents("file:E:\\GitHub\\demos\\java-demo\\target\\classes\\com\\shi\\java\\JarScan");
	}

}

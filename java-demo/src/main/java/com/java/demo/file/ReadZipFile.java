package com.java.demo.file;

import java.io.File;
import java.io.IOException;

/**
 * Created by DebugSy on 2018/4/23.
 */
public class ReadZipFile {

	public static void main(String[] args) {
		String filePath = args[0];
		System.out.println("load file: " + filePath);
		File file = new File(filePath);
		try {
			String txt = readFileToString(file);
			System.out.println(" file content: " + txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFileToString(File file) throws IOException {
		return org.apache.commons.io.FileUtils.readFileToString(file);
	}

}

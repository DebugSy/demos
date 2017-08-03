package com.shi.java.IO;

import java.io.*;

/**
 * Created by DebugSy on 2017/8/3.
 * <p>
 * 调用close方法来释放对象所在的资源
 */
public class CloseAbleTest implements Closeable {

	private FileInputStream in;

	public void readFile() throws IOException {
		File file = new File("E:\\GitHub\\demos\\java-demo\\src\\main\\resources\\test.txt");
		in = new FileInputStream(file);
		byte[] bytes = new byte[1024];
		int n = 0;
		while ((n = in.read(bytes)) != -1){
			System.out.println(new String(bytes));
		}
	}

	public void close() throws IOException {
		System.out.println("调用close方法！");
		in.close();
	}
}

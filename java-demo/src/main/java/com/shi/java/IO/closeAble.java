package com.shi.java.IO;

import java.io.*;

/**
 * Created by DebugSy on 2017/8/3.
 */
public class closeAble {

	public void readFile() {

			for (int i = 0; i < 10000000; i++) {


//				File file_read = new File("E:\\GitHub\\demos\\java-demo\\src\\main\\resources\\test.txt");
//				File file_read = new File("E:\\GitHub\\demos\\java-demo\\src\\main\\resources\\test.txt");
				File file_write = new File("E:\\GitHub\\demos\\java-demo\\src\\main\\resources\\test" + i + ".txt");
//				FileInputStream in = new FileInputStream(file_read);
//				FileOutputStream out = new FileOutputStream(file_write);

//				byte[] bytes = new byte[1024];
//				int n = 0;
//				while ((n = in.read(bytes)) != -1) {
//					System.out.println(new String(bytes));
//					out.write(bytes);
//				}
				file_write.deleteOnExit();
			}


	}

}

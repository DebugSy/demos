package com.hdfs.demo.hdfs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;


/**
 * 用流的方式来操作hdfs上的文件
 * 可以实现读取指定偏移量范围的数据
 *
 * 相对那些封装好的方法而言的更底层一些的操作方式
 * 上层那些mapreduce   spark等运算框架，去hdfs中获取数据的时候，就是调的这种底层的api
 *
 */
public class HdfsStreamAccess {
	
	FileSystem fs = null;
	Configuration conf = null;
	
	@Before
	public void init() throws Exception{
		
		conf = new Configuration();
		//拿到一个文件系统操作的客户端实例对象
//		fs = FileSystem.get(conf);
		//可以直接传入 uri和用户身份
		fs = FileSystem.get(new URI("hdfs://mini1:9000"),conf,"hadoop");
	}
	

	/**
	 * 通过流的方式上传文件到hdfs
	 * @throws Exception
	 */
	@Test
	public void testUpload() throws Exception {
		
		FSDataOutputStream outputStream = fs.create(new Path("/angelababy.love"), true);
		FileInputStream inputStream = new FileInputStream("c:/angelababy.love");
		
		IOUtils.copy(inputStream, outputStream);
		
	}
	
	
	/**
	 * 通过流的方式获取hdfs上数据
	 * @throws Exception
	 */
	@Test
	public void testDownLoad() throws Exception {
		
		FSDataInputStream inputStream = fs.open(new Path("/angelababy.love"));		
		
		FileOutputStream outputStream = new FileOutputStream("d:/angelababy.love");
		
		IOUtils.copy(inputStream, outputStream);
		
	}
	
	
	@Test
	public void testRandomAccess() throws Exception{
		
		FSDataInputStream inputStream = fs.open(new Path("/angelababy.love"));
	
		inputStream.seek(12);
		
		FileOutputStream outputStream = new FileOutputStream("d:/angelababy.love.part2");
		
		IOUtils.copy(inputStream, outputStream);
		
		
	}
	
	
	
	/**
	 * 显示hdfs上文件的内容
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testCat() throws IllegalArgumentException, IOException{
		
		FSDataInputStream in = fs.open(new Path("/angelababy.love"));
		
		IOUtils.copy(in, System.out);
		
//		IOUtils.copyBytes(in, System.out, 1024);
	}
	
	
	
	
	
}

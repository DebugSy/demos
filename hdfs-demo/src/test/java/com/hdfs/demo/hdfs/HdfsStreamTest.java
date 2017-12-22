package com.hdfs.demo.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by DebugSy on 2017/12/20.
 *
 * 可以实现读取指定偏移量范围的数据
 *
 * 一般底层的应用都调用这样的接口,像spark等等
 */
public class HdfsStreamTest {

	private FileSystem hdfs;

	private Configuration configuration;

	@Before
	public void init(){
		try {
			configuration = new Configuration();
			if (configuration.get("fs.defaultFS").startsWith("file:///")){
				throw new IllegalArgumentException("can't find config file core-site.xml or hdfs-site.xml");
			}
			hdfs = FileSystem.get(new URI("hdfs://192.168.112.128:8020"), configuration, "carpo");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void uploadFileWithStream() throws IOException {
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client/stream/data_copy.csv");
		FSDataOutputStream outputStream = hdfs.create(hdfsPath);
		FileInputStream inputStream = new FileInputStream("src/test/resources/data.csv");
		IOUtils.copyBytes(inputStream, outputStream, 65535);
	}

	@Test
	public void downloadFileWithStream() throws IOException {
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client/stream/data_copy.csv");
		FSDataInputStream in = hdfs.open(hdfsPath);
		FileOutputStream out = new FileOutputStream("src/test/resources/data_copy.csv");
		IOUtils.copyBytes(in, out, 65535);
	}

	/**
	 * 获取一个文件的所有block位置信息，然后读取指定block中的内容
	 *
	 * 场景:
	 * 在mapreduce 、spark等运算框架中，有一个核心思想就是将运算移往数据，
	 * 或者说，就是要在并发计算中尽可能让运算本地化，这就需要获取数据所在位置的信息并进行相应范围读取
	 */
	@Test
	public void blockRead() throws IOException {
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client/stream/data_copy.csv");
		FSDataInputStream in = hdfs.open(hdfsPath);

		FileStatus[] fileStatuses = hdfs.listStatus(hdfsPath);
		for (int i = 0; i < fileStatuses.length; i++) {
			FileStatus fileStatus = fileStatuses[i];
			BlockLocation[] blockLocations = hdfs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
			for (int j = 0; j < blockLocations.length; j++) {
				System.out.println("length:" + blockLocations[j].getLength());
				System.out.println("offset: " + blockLocations[j].getOffset());
			}
		}

	}


}

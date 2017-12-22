package com.hdfs.demo.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by DebugSy on 2017/12/19.
 */
public class HdfsClientTest {

	private FileSystem hdfs;

	private Configuration configuration;

	/**
	 * configuration加载的优先级：客户端设置 > classpath:hdfs-default.xml > hdfs-site.xml
	 */
	@Before
	public void init(){
		//辨别是否加载了hdfs配置，可以判断fs.defaultFS的值是否为file:///
		configuration = new Configuration();
		String defaultFS = configuration.get("fs.defaultFS");
		if (defaultFS != null && defaultFS.startsWith("file:///")){
			throw new IllegalArgumentException("can't find config file core-site.xml or hdfs-site.xml");
		}
		try {
//			this.hdfs = FileSystem.get(configuration);
			//指定hdfs用户
			this.hdfs = FileSystem.get(new URI("hdfs://192.168.112.128:8020"), configuration, "carpo");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出HDFS指定目录下的文件
	 * @throws IOException
	 */
	@Test
	public void listHdfsFile() throws IOException {
		Path path = new Path("/tmp/shiy/hdfs-client");
		RemoteIterator<LocatedFileStatus> files = hdfs.listFiles(path, false);
		while(files.hasNext()){
			LocatedFileStatus file = files.next();
			System.out.println(file.getPath().getName());
		}
		hdfs.close();
	}

	/**
	 * 列出HDFS指定目录下的目录及文件
	 * @throws IOException
	 */
	@Test
	public void listHdfsAll() throws IOException {
		Path path = new Path("/tmp/shiy/hdfs-client");
		FileStatus[] fileStatuses = hdfs.listStatus(path);
		for (int i = 0; i < fileStatuses.length; i++) {
			System.out.println(fileStatuses[i].getPath().getName());
		}
		hdfs.close();
	}

	/**
	 * 上传文件到HDFS
	 * 默认使用本地用户
	 */
	@Test
	public void uploadFile() throws IOException {
		Path localPath = new Path("src/test/resources/data.csv");
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client");
		hdfs.copyFromLocalFile(localPath, hdfsPath);
	}

	@Test
	public void downloadFile() throws IOException {
		Path localPath = new Path("src/test/resources/download");
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client/data.csv");
		hdfs.copyToLocalFile(hdfsPath, localPath);
	}

	/**
	 * 创建目录
	 * @throws IOException
	 */
	@Test
	public void createHdfsDir() throws IOException {
		//创建目录
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client/test");
		hdfs.mkdirs(hdfsPath);
	}

	/**
	 * 重命名目录
	 * @throws IOException
	 */
	@Test
	public void renameHdfsDir() throws IOException {
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client/test");
		Path renamePath = new Path("/tmp/shiy/hdfs-client/test_rename");
		hdfs.rename(hdfsPath, renamePath);
	}

	/**
	 * 删除目录
	 */
	@Test
	public void deleteHdfsDir() throws IOException {
		Path hdfsPath = new Path("/tmp/shiy/hdfs-client/test_rename");
		hdfs.deleteOnExit(hdfsPath);
	}

}

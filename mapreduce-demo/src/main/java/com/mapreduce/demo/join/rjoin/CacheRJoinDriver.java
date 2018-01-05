package com.mapreduce.demo.join.rjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by DebugSy on 2018/1/4.
 */
public class CacheRJoinDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {

		Configuration configuration = new Configuration();

		Job job = Job.getInstance(configuration, "RJoin Demo");

		job.setJarByClass(CacheRJoinDriver.class);

		job.setMapperClass(CacheRJoinMapper.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		// 指定需要缓存一个文件到所有的maptask运行节点工作目录
		/* job.addArchiveToClassPath(archive); */// 缓存jar包到task运行节点的classpath中
		/* job.addFileToClassPath(file); */// 缓存普通文件到task运行节点的classpath中
		/* job.addCacheArchive(uri); */// 缓存压缩包文件到task运行节点的工作目录
		/* job.addCacheFile(uri) */// 缓存普通文件到task运行节点的工作目录
		job.addCacheFile(new URI("file:///E:/tmp/develop/rjoin/input/product.txt"));

		TextInputFormat.addInputPath(job, new Path("file:///E:/tmp/develop/rjoin/input/order.txt"));
		TextOutputFormat.setOutputPath(job, new Path("file:///E:/tmp/develop/rjoin/output2"));

		//不设置task
		job.setNumReduceTasks(0);

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);

	}

}

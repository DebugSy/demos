package com.mapreduce.demo.combinefile.custome;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 * 合并小文件，能提高job执行效率
 */
public class CombineFileMapper extends Mapper<NullWritable, Text, Text, NullWritable> {

	/**
	 * map task直接将内容输出
	 * @param key
	 * @param value
	 * @param context
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	protected void map(NullWritable key, Text value, Context context) throws IOException, InterruptedException {
		context.write(value, NullWritable.get());
	}
}

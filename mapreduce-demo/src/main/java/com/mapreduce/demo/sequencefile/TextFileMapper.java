package com.mapreduce.demo.sequencefile;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class TextFileMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

	/**
	 * Called once for each key/value pair in the input split. Most applications
	 * should override this, but the default is the identity function.
	 *
	 * @param key
	 * @param value
	 * @param context
	 */
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		super.map(key, value, context);
	}
}

package com.mapreduce.demo.sequencefile;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class TextFileReducer extends Reducer<NullWritable, Text, NullWritable, Text> {

	/**
	 * This method is called once for each key. Most applications will define
	 * their reduce class by overriding this method. The default implementation
	 * is an identity function.
	 *
	 * @param key
	 * @param values
	 * @param context
	 */
	@Override
	protected void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		super.reduce(key, values, context);
	}
}

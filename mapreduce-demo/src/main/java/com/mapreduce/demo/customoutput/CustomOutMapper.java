package com.mapreduce.demo.customoutput;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class CustomOutMapper extends Mapper<LongWritable, Text, Text, Text>{

	private Text keyText = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Counter counter = context.getCounter("counterGroup", "counterName");
		try{
			//这里只是简单讲访问的地址拿出来作为key，方便后面的逻辑直接对key进行判断。当然最好的做法是value为NullWriteable
			String line = value.toString();
			String[] split = StringUtils.split(line, " ");
			String remote = split[10];
			keyText.set(remote);
			String returnLine = "\r\n";
			value.append(returnLine.getBytes(), 0, returnLine.length());
			context.write(keyText, value);
		}catch (Exception e){
			counter.increment(1);
		}

	}

}

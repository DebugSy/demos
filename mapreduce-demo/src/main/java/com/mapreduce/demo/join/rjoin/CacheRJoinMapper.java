package com.mapreduce.demo.join.rjoin;

import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DebugSy on 2018/1/4.
 *
 * 适用于关联表中有小表的情形
 *
 * 将小表缓存起来进行join
 */
public class CacheRJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

	private String pid = "";
	private Text text = new Text(pid);

	private Map<String, List<String>> pdInfoMap = new HashMap();

	//setup方法是在maptask处理数据之前调用一次 可以用来做一些初始化工作
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		URI[] cacheFiles = context.getCacheFiles();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(cacheFiles[0]))));
		String line;
		while ((line = reader.readLine()) != null){
			String[] splits = line.split("\t");
			ArrayList<String> list = new ArrayList<>();
			list.add(splits[1]);
			list.add(splits[3]);
			pdInfoMap.put(splits[0], list);
		}
		IOUtils.closeStream(reader);
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String orderLine = value.toString();
		String[] fields = orderLine.split("\t");
		String pid = fields[2];
		List<String> strings = pdInfoMap.get(pid);
		if (strings != null && strings.size() > 0){
			text.set(orderLine + "\t" + pid + "\t" + strings.get(0) + "\t" + strings.get(1));
		}
		context.write(text, NullWritable.get());
	}
}

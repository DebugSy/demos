package com.mapreduce.demo.join.rjoin.low;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/4.
 *
 * 这种方式中，join的操作是在reduce阶段完成，reduce端的处理压力太大,
 * map节点的运算负载则很低，资源利用率不高，且在reduce阶段极易产生数据倾斜
 *
 * 解决办法:map端join
 */
public class RJoinMapper extends Mapper<LongWritable, Text, Text, InfoBean> {

	private InfoBean infoBean = new InfoBean();
	private String pid = "";
	private Text text = new Text(pid);

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();


		FileSplit inputSplit = (FileSplit) context.getInputSplit();
		String name = inputSplit.getPath().getName();
		if (name.startsWith("order")){
			String[] orderSplits = line.split("\t");
			infoBean.set(Integer.parseInt(orderSplits[0]), orderSplits[1], 0);
			pid = orderSplits[2];
		} else {
			String[] productSplit = line.split("\t");
			infoBean.set(productSplit[1], Integer.parseInt(productSplit[2]), Double.parseDouble(productSplit[3]), 1);
			pid = productSplit[0];
		}
		text.set(pid);

		context.write(text, infoBean);
	}
}

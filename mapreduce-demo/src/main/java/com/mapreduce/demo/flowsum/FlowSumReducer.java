package com.mapreduce.demo.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/3.
 */
public class FlowSumReducer extends Reducer<Text, FlowBean, Text, FlowBean>{

	@Override
	protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
		long upFlowSum = 0;
		long dFlowSum = 0;
		for (FlowBean flowBean : values){
			upFlowSum += flowBean.getUpFlow();
			dFlowSum += flowBean.getdFlow();
		}

		context.write(key, new FlowBean(upFlowSum, dFlowSum));
	}
}

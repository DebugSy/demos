package com.mapreduce.demo.groupingCompare;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class GroupingCompare extends WritableComparator {

	//传入作为key的bean的class类型，以及制定需要让框架做反射获取实例对象
	protected GroupingCompare() {
		super(OrderBean.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		OrderBean orderBean1 = (OrderBean) a;
		OrderBean orderBean2 = (OrderBean) b;
		return orderBean1.getId().compareTo(orderBean2.getId());
	}


}

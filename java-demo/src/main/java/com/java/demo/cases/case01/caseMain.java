package com.java.demo.cases.case01;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DebugSy on 2017/8/3.
 *
 * 	测试目的：从kafka所有分区均匀获取指定数量的message
 *
 */
public class caseMain {

	public static void main(String[] args) {

		//模拟分区01
		Map<Integer,Integer> map01 = new HashMap();
		for (int i = 1; i <= 10; i++) {
			map01.put(i, i * 100);
		}

		//模拟分区02
		Map<Integer,Integer> map02 = new HashMap();
		for (int i = 1; i <= 2; i++) {
			map02.put(i, i * 100);
		}

		//模拟分区03
		Map<Integer,Integer> map03 = new HashMap();
		for (int i = 1; i <= 11; i++) {
			map03.put(i, i * 100);
		}

		//模拟一个Topic，该Topic有三个分区
		Map<Integer, Map> pool = new HashMap<Integer, Map>();
		pool.put(1, map01);
		pool.put(2, map02);
		pool.put(3, map03);

		//模拟Topic的offset，首次直接手动将offset设为最大
		Map<Integer,Integer> offsetPartitionMap = new HashMap<Integer, Integer>();
		offsetPartitionMap.put(1, map01.size());
		offsetPartitionMap.put(2, map02.size());
		offsetPartitionMap.put(3, map03.size());

		int numDelta = 1;//这个参数用来控制当一个分区没有数据时，不去获取
		int count = 10;//模拟将要获取的message数量
		while( numDelta != 0 && count > 0){
			numDelta = 0;
			for (int partiton : pool.keySet()){
				System.out.println("go into for");
				int i = (Integer) offsetPartitionMap.get(partiton);
				if (i > 0){
					numDelta++;
					count--;
					if (count < 0){
						break;
					}
					i--;
					offsetPartitionMap.put(partiton, i);
					System.out.println("partiton:"+partiton + " offset:"+i + " count:"+count);
				}
			}
		}
	}

}

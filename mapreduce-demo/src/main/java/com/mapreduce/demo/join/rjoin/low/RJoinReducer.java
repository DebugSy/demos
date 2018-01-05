package com.mapreduce.demo.join.rjoin.low;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by DebugSy on 2018/1/4.
 *
 * 缺点：
 * 这种方式中，join的操作是在reduce阶段完成，reduce端的处理压力太大，
 * map节点的运算负载则很低，资源利用率不高，且在reduce阶段极易产生数据倾斜
 *
 * 解决办法:map端join
 */
public class RJoinReducer extends Reducer<Text, InfoBean, InfoBean, NullWritable> {

	@Override
	protected void reduce(Text key, Iterable<InfoBean> beans, Context context) throws IOException, InterruptedException {

		InfoBean pdBean = new InfoBean();
		ArrayList<InfoBean> orderBeans = new ArrayList<InfoBean>();

		for (InfoBean bean : beans) {
			if (bean.getFlag() == 1) {	//产品的
				try {
					BeanUtils.copyProperties(pdBean, bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				InfoBean odbean = new InfoBean();
				try {
					BeanUtils.copyProperties(odbean, bean);
					orderBeans.add(odbean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		// 拼接两类数据形成最终结果
		for (InfoBean bean : orderBeans) {

			bean.setName(pdBean.getName());
			bean.setCategory_id(pdBean.getCategory_id());
			bean.setPrice(pdBean.getPrice());

			context.write(bean, NullWritable.get());
		}
	}
}

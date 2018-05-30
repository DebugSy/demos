package com.flink.demo.source;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

/**
 * Created by DebugSy on 2018/5/10.
 */
public class CollectionSourceTest {

	private StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

	private CollectionSource collectionSource;

	@Test
	public void collectionSource() throws Exception {
		List list = Arrays.asList("1", "2", "3");
		collectionSource = new CollectionSource(env, list);
		DataStreamSource streamSource = collectionSource.read();
		streamSource.print();

		env.execute("collection source");
	}

	@Test
	public void iteratorSource() throws Exception {
		List<Student> collection = new LinkedList<Student>();
		for (int i = 0; i < 10; i++) {
			collection.add(new Student(i, "name" + i));
		}
		Iterator<Student> it = collection.iterator();
		collectionSource = new CollectionSource(env, it, Long.class);
//		collectionSource = new CollectionSource(env, collection);
		DataStreamSource streamSource = collectionSource.read();
		streamSource.print();

		env.execute("iterator source");
	}

	@Test
	public void iteratorLongSource() throws Exception {
		ArrayList<Long> collection = new ArrayList<Long>();
		for (int i = 0; i < 10; i++) {
			collection.add(Long.valueOf(i));
		}
		Iterator<Long> it = collection.iterator();
		DataStreamSource<Long> longDataStreamSource = env.fromCollection(it, Long.class);

		longDataStreamSource.print();
		env.execute("iterator source");
	}

}

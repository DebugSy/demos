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
		ArrayList<Student> collection = new ArrayList<Student>();
		for (int i = 0; i < 10; i++) {
			collection.add(new Student(i, "name" + i));
		}
		Iterator<Student> iterator = collection.iterator();
		collectionSource = new CollectionSource(env, iterator, Student.class);
//		collectionSource = new CollectionSource(env, collection);
		DataStreamSource streamSource = collectionSource.read();
		streamSource.print();

		env.execute("iterator source");
	}

	class Student extends TypeInformation {

		private static final long serialVersionUID = 1L;

		private int id;
		private String name;

		public Student() {
		}

		public boolean isBasicType() {
			return false;
		}

		public boolean isTupleType() {
			return true;
		}

		public int getArity() {
			return 0;
		}

		public int getTotalFields() {
			return 0;
		}

		public Class getTypeClass() {
			return Student.class;
		}

		public boolean isKeyType() {
			return false;
		}

		public TypeSerializer createSerializer(ExecutionConfig config) {
			return null;
		}

		public String toString() {
			return null;
		}

		public boolean equals(Object obj) {
			return false;
		}

		public int hashCode() {
			return 0;
		}

		public boolean canEqual(Object obj) {
			return false;
		}

		public Student(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}

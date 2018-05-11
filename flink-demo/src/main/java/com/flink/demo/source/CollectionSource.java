package com.flink.demo.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.Tuple2;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by DebugSy on 2018/5/10.
 */
public class CollectionSource implements Source {

	private StreamExecutionEnvironment env;

	private Collection collection;

	private Iterator iterator;

	private Class aClass;

	public CollectionSource(StreamExecutionEnvironment env, Collection collection) {
		this.env = env;
		this.collection = collection;
	}

	public CollectionSource(StreamExecutionEnvironment env, Iterator iterator, Class clazz) {
		this.env = env;
		this.iterator = iterator;
		this.aClass = clazz;
	}

	public DataStreamSource read() {
		DataStreamSource streamSource = null;
		if (collection != null && !collection.isEmpty()){
			streamSource = env.fromCollection(collection);
		} else if (iterator != null){
			streamSource = env.fromCollection(iterator, aClass);
		}
		return streamSource;
	}

}

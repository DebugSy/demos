package com.flink.demo.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;

/**
 * Created by DebugSy on 2018/5/9.
 */
public interface Source {

	DataStreamSource read();

}

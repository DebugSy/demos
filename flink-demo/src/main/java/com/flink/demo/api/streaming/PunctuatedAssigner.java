package com.flink.demo.api.streaming;

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * Created by DebugSy on 2018/5/31.
 */
public class PunctuatedAssigner implements AssignerWithPunctuatedWatermarks<MyEvent> {
	@Nullable
	@Override
	public Watermark checkAndGetNextWatermark(MyEvent lastElement, long extractedTimestamp) {
		return new Watermark(extractedTimestamp);
	}

	@Override
	public long extractTimestamp(MyEvent element, long previousElementTimestamp) {
		return element.getTimestamp();
	}
}

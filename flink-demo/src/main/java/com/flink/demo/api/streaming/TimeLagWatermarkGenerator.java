package com.flink.demo.api.streaming;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * Created by DebugSy on 2018/5/31.
 */
public class TimeLagWatermarkGenerator implements AssignerWithPeriodicWatermarks<MyEvent> {

	private final long maxTimeLag = 5000;

	@Nullable
	@Override
	public Watermark getCurrentWatermark() {
		return new Watermark(System.currentTimeMillis() - maxTimeLag);
	}

	@Override
	public long extractTimestamp(MyEvent element, long previousElementTimestamp) {
		return element.getTimestamp();
	}
}

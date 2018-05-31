package com.flink.demo.api.streaming;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * 自定义时间戳/水印生成器
 * Created by DebugSy on 2018/5/31.
 */
public class BoundedOutOfOrdernessGenerator implements AssignerWithPeriodicWatermarks<MyEvent> {

	private final long maxOutOfOrderness = 3500; // 3.5 seconds

	private long currentMaxTimestamp;

	@Nullable
	@Override
	public Watermark getCurrentWatermark() {
		return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
	}

	@Override
	public long extractTimestamp(MyEvent element, long previousElementTimestamp) {
		long timestamp = element.getTimestamp();
		currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
		return timestamp;
	}
}

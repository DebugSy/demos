package com.mapreduce.demo.combinefile.custome;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class CombineFileRecordReader extends RecordReader<NullWritable, Text> {

	private FileSplit split;

	private Configuration configuration;

	private Text value = new Text();

	private boolean processed = false;

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		this.split = (FileSplit) split;
		this.configuration = context.getConfiguration();
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (!processed) {
			long length = split.getLength();
			byte[] content = new byte[((int) length)];


			Path path = split.getPath();
			FileSystem fs = FileSystem.get(configuration);
			FSDataInputStream inputStream = fs.open(path);
			IOUtils.readFully(inputStream, content, 0, content.length);
			value.set(content, 0, content.length);
			IOUtils.closeStream(inputStream);
			processed = true;
			return true;
		}
		return false;
	}

	@Override
	public NullWritable getCurrentKey() throws IOException, InterruptedException {
		return NullWritable.get();
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		return value;
	}


	//返回当前进度
	@Override
	public float getProgress() throws IOException, InterruptedException {
		return processed?1.0f:0.0f;
	}

	@Override
	public void close() throws IOException {

	}
}

package com.mapreduce.demo.combinefile.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.util.LineReader;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class CombineRecordReader extends RecordReader<LongWritable, Text> {

	private CombineFileSplit split;

	private Configuration configuration;

	private FileSystem fs;

	private Path path;

	private Text value = new Text();

	private long startOffset; //offset of the chunk;
	private long end; //end of the chunk;
	private long pos; // current pos

	private FSDataInputStream fileIn;
	private LineReader reader;

	public CombineRecordReader(CombineFileSplit split, TaskAttemptContext context, Integer index) throws IOException {
		this.split = split;
		this.path = split.getPath(index);
		this.fs = path.getFileSystem(context.getConfiguration());

		this.startOffset = split.getOffset(index);
		this.end = startOffset + split.getLength(index);
		boolean isSkipFirstLine = false;

		fileIn = fs.open(path);
		if (startOffset !=0 ){
			isSkipFirstLine = true;
			--startOffset;
			fileIn.seek(startOffset);
		}
		reader = new LineReader(fileIn);
		if (isSkipFirstLine){// 跳过第一行并重新建立"startOffset"
			startOffset += reader.readLine(new Text(), 0,
					(int)Math.min((long)Integer.MAX_VALUE, end - startOffset));
		}
		this.pos = startOffset;
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		this.split = (CombineFileSplit) split;
		this.configuration = context.getConfiguration();
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {

		if (value == null) {
			value = new Text();
		}
		int newSize = 0;
		if (pos < end) {
			newSize = reader.readLine(value);
			pos += newSize;
		}
		if (newSize == 0) {
			value = null;
			return false;
		} else {
			return true;
		}
	}

	@Override
	public LongWritable getCurrentKey() throws IOException, InterruptedException {
		return new LongWritable(pos);
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		return value;
	}


	//返回当前进度
	@Override
	public float getProgress() throws IOException, InterruptedException {
		if (startOffset == end) {
			return 0.0f;
		} else {
			return Math.min(1.0f, (pos - startOffset) / (float)(end - startOffset));
		}
	}

	@Override
	public void close() throws IOException {

	}
}

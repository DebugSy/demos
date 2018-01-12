package com.mapreduce.demo.combinefile;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;
import java.util.List;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class CombineFileInputFormat extends FileInputFormat<NullWritable, Text> {

	@Override
	public RecordReader<NullWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		CombineFileRecordReader combineFileRecordReader = new CombineFileRecordReader();
		combineFileRecordReader.initialize(split, context);
		return combineFileRecordReader;
	}

}

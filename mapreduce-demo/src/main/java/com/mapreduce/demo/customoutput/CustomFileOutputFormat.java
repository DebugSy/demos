package com.mapreduce.demo.customoutput;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class CustomFileOutputFormat extends FileOutputFormat {

	private FSDataOutputStream googleOutStream;

	private FSDataOutputStream otherOutStream;

	@Override
	public RecordWriter getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
		//拿到FileSystem
		FileSystem fs = FileSystem.get(new Configuration());

		//从FileSystem创建两个输出流
		Path googlePath = new Path("E:/tmp/develop/customOutput/output/google.txt");
		Path otherPath = new Path("E:/tmp/develop/customOutput/output/other.txt");

		FSDataOutputStream googleOutStream = fs.create(googlePath);
		FSDataOutputStream otherOutStream = fs.create(otherPath);

		//返回一个RecordWriter
		return new CustomRecordWriter(googleOutStream, otherOutStream);
	}

	//泛型与map的输出类型保持一致
	static class CustomRecordWriter extends RecordWriter<Text, Text>{

		private FSDataOutputStream googleOutStream;

		private FSDataOutputStream otherOutStream;

		public CustomRecordWriter(FSDataOutputStream googleOutStream, FSDataOutputStream otherOutStream) {
			this.googleOutStream = googleOutStream;
			this.otherOutStream = otherOutStream;
		}

		@Override
		public void write(Text key, Text value) throws IOException, InterruptedException {
			String toString = key.toString();
			//key中包含google输出到googleOutStream，不包含的输出到otherOutStream
			if (toString.contains("google")){
				googleOutStream.write(value.getBytes());
			} else {
				otherOutStream.write(value.getBytes());
			}
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			if (this.googleOutStream != null){
				googleOutStream.close();
			}
			if (this.otherOutStream != null){
				otherOutStream.close();
			}
		}
	}

}

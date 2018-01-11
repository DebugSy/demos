package com.mapreduce.demo.commonfriends.firstStep;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CmFrientsMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text keyText = new Text();
    private Text valueText = new Text();
//    private ArrayWritable array = new ArrayWritable(Text.class);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] splits = line.split(",|:");
        valueText.set(splits[0]);
        for (int i = 1; i < splits.length; i++) {
            keyText.set(splits[i]);
            context.write(keyText, valueText);
        }
    }
}

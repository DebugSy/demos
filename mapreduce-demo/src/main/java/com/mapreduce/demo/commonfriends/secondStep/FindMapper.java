package com.mapreduce.demo.commonfriends.secondStep;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

public class FindMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text keyText = new Text();
    private Text valueText = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] splits = line.split("\t");
        valueText.set(splits[0]);
        String[] relationships = splits[1].split(",");
        Arrays.sort(relationships);
        for (int i = 0; i < relationships.length; i++) {
            for (int j = i+1; j < relationships.length; j++) {
                keyText.set(relationships[i] + "-" + relationships[j]);
                context.write(keyText, valueText);
            }
        }
    }
}

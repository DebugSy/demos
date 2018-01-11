package com.mapreduce.demo.commonfriends.secondStep;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class FindReducer extends Reducer<Text, Text, Text, Text>{

    private Text valueText = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Iterator<Text> iterator = values.iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (iterator.hasNext()){
            stringBuffer.append(iterator.next());
            if (iterator.hasNext()){
                stringBuffer.append(",");
            }
        }
        valueText.set(stringBuffer.toString());
        context.write(key, valueText);
    }
}

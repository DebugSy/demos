package com.mapreduce.demo.commonfriends;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class CmFrientsDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "find common friends");

        job.setJarByClass(CmFrientsDriver.class);

        job.setMapperClass(CmFrientsMapper.class);
        job.setReducerClass(CmFrientsReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path("D:/tmp/develop/friends/input/frients.txt"));
        FileOutputFormat.setOutputPath(job, new Path("D:/tmp/develop/friends/output"));

        boolean completion = job.waitForCompletion(true);
        System.exit(completion?0:1);

    }

}

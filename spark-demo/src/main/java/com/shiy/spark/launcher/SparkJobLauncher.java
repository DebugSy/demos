package com.shiy.spark.launcher;

import org.apache.spark.launcher.SparkLauncher;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by DebugSy on 2017/9/15.
 */
public class SparkJobLauncher {

	public void launcher(){
		SparkLauncher launcher = new SparkLauncher();
		launcher.setAppName("spark-demo");
		launcher.setAppResource("");
		launcher.setMaster("yarn-cluster");
		launcher.setConf(SparkLauncher.DRIVER_MEMORY, "512m");
		launcher.setConf(SparkLauncher.EXECUTOR_MEMORY, "512m");
		launcher.setConf(SparkLauncher.EXECUTOR_CORES, "2");

		try {
			Process process = launcher.launch();
			InputStream errorStream = process.getErrorStream();
			InputStream stdInput = process.getInputStream();

			System.out.println("---------------- read msg -----------------");
			dumpInput(stdInput);

			System.out.println("-------------- read err msg ---------------");
			dumpInput(errorStream);

			System.out.println("launcher over");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void dumpInput(InputStream input) throws IOException {
		byte[] buff = new byte[1024];

		while (true) {
			int len = input.read(buff);

			if (len < 0) {
				break;
			}

			System.out.println(new String(buff, 0, len));
		}
	}

	public static void main(String[] args) {
		SparkJobLauncher launcher = new SparkJobLauncher();
		launcher.launcher();
	}

}

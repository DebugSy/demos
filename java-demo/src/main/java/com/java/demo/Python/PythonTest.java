package com.java.demo.Python;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DebugSy on 2017/9/5.
 */
public class PythonTest {
	public static void main(String[] args) {
		try {
			System.out.println("start........");
			List<String> commands = new ArrayList();
			commands.add("python");
			commands.add("E:\\GitHub\\demos\\java-demo\\src\\main\\java\\com\\shi\\java\\Python\\input.py");
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(commands);
//			Map workerEnv = pb.environment();
//			workerEnv.put("PYTHONPATH", "E:\\GitHub\\demos\\java-demo\\src\\main\\java\\com\\shi\\java\\Python");
			Process worker = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(worker.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

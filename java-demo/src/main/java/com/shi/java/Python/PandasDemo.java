package com.shi.java.Python;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DebugSy on 2017/9/6.
 */
public class PandasDemo {

	public static void main(String[] args) {

		try {
			System.out.println("start........");
			List<String> commands = new ArrayList();
			commands.add("python");
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(commands);
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

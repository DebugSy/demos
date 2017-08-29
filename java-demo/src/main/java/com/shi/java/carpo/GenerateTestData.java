package com.shi.java.carpo;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;

/**
 * Created by DebugSy on 2017/8/28.
 */
public class GenerateTestData {

	public static void main(String[] args) {

		if (args.length < 2){
			throw new IllegalArgumentException("Parameters can not be less than 2!");
		}

		String path = args[0];
		int size = Integer.parseInt(args[1]);

		try {
			File dataFile = new File(path);
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
//			OutputStream outputStream = new FileOutputStream(dataFile);
			Writer writer = new FileWriter(dataFile);
			CSVWriter csvWriter = new CSVWriter(writer, ',');
			int i = 1;
			while(i <= size){
				Person person = Person.builer(i, "name_"+i, i);
				String[] personInfo = {String.valueOf(person.getId()),person.getName(),String.valueOf(person.getAge())};
				csvWriter.writeNext(personInfo);
				i++;
			}
			csvWriter.flush();
			csvWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

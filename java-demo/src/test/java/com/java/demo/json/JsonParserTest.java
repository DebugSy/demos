package com.java.demo.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DebugSy on 2017/12/21.
 */
public class JsonParserTest {

	private static JsonBuilder jsonParser = JsonBuilder.getInstance();

	public static void main(String[] args) {
		String json = "{\n" +
				"    \"name\": \"质检\",\n" +
				"    \"lastUpdateTime\": \"2016-09-26 15:00:06\",\n" +
				"    \"createUser\": \"bf889f60-83b5-11e6-9199-e7120b905f9c\",\n" +
				"    \"actions\": [\n" +
				"        {\n" +
				"            \"jumpTo\": \"5b5ee490-7d75-11e6-bcd6-cfa3b709f9ec\",\n" +
				"            \"_id\": \"008dddd0-83b7-11e6-9965-8d413c3f5b94\",\n" +
				"            \"actionRole\": \"50652990-839d-11e6-9199-e7120b905f9c\",\n" +
				"            \"name\": \"流转至海外现场\",\n" +
				"            \"actionFields\": []\n" +
				"\t\t}\n" +
				"\t]\n" +
				"}";

		Map map = jsonParser.fromJson(json, Map.class);
		System.out.println(map);

//		StringBuilder sb = new StringBuilder("(");
//		for (Object key : map.keySet()){
//			Object value = map.get(key);
//			if (value instanceof ArrayList){
//				sb.append(key);
//			} else {
//				sb.append(key).append(" string");
//			}
//		}

	}

//	public String parserArrayList(ArrayList list){
//		StringBuilder sb = new StringBuilder("array<struct<");
//		for (int i = 0; i < list.size(); i++) {
//			Object object = list.get(i);
//			if (object instanceof ArrayList){
//				parserArrayList((ArrayList) object);
//			} else {
//				sb.append()
//			}
//		}
//	}

}

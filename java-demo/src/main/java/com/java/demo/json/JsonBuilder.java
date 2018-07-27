package com.java.demo.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonBuilder {

	private ObjectMapper mapper = new ObjectMapper();

	private static JsonBuilder _instance = new JsonBuilder();

	public static JsonBuilder getInstance() {
		return _instance;
	}

	public JsonBuilder() {
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public JsonBuilder pretty() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return this;
	}

	public <T> T fromJson(String json, Class<T> typeOfT) {
		if (json == null) {
			throw new IllegalArgumentException("json string should not be null");
		}
		try {
			return mapper.readValue(json, typeOfT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static ObjectMapper buildObjectMapper(boolean prettyJson) {
		ObjectMapper m = new ObjectMapper();
		if (prettyJson) {
			m.enable(SerializationFeature.INDENT_OUTPUT);
		}
		m.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		m.setSerializationInclusion(Include.NON_NULL);
		return m;
	}

	public static ObjectMapper defaultObjectMapper = buildObjectMapper(false);

}

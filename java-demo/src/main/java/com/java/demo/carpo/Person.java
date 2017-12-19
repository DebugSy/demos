package com.java.demo.carpo;

import java.io.Serializable;

/**
 * Created by DebugSy on 2017/8/28.
 */
public class Person implements Serializable{

	private long id;

	private String name;

	private int age;

	public Person(long id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static Person builer(long id, String name, int age){
		return new Person(id, name, age);
	}
}

package com.shiy.spring.jpa.data;

import javax.persistence.*;

/**
 * Created by DebugSy on 2018/7/24.
 */
@Entity
public class Grade {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Integer id;

	@Column(name="studentId")
	public String studentId;

	@Column
	public String address;

	@Column
	public Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}

package com.shiy.springmvc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Spitter {

  private Long id;

  /**
   *  Spring java 检验API,所有的注解位于javax.validation.constraints.*下
   *
   *  @AssertFalse  注解元素是boolean，并且值必须为false
   *  @AssertTrue   注解元素是boolean，并且值必须为true
   *  @DecimalMax   注解元素是数字，并且值必须小于或等于给定的bigDecimalString的值
   *  @DecimalMin   注解元素是数字，并且值必须大于或等于给定的bigDecimalString的值
   *  @Digits       注解元素是数字，并且值必须有指定的位数
   *  @Future       注解元素的值必须是一个将来的日期
   *  @Max          注解元素必须是数字，并且小于或等于给定的值
   *  @Min          注解元素必须是数字，并且大于或等于给定的值
   *  @NotNull      注解元素的值不能是null
   *  @Null         注解元素的值必须是null
   *  @Past         注解元素的值必须是一个过去的日期
   *  @Pattern      注解元素的值必须匹配给定的正则表达式
   *  @Size         注解元素的值必须是String/集合/数组。并且他的长度符合给定的范围
   *
   *  作用：可用于表单的检验processRegistration()方法
   */

  @NotNull
  @Size(min=5, max=16)
  private String username;

  @NotNull
  @Size(min=5, max=25)
  private String password;
  
  @NotNull
  @Size(min=2, max=30)
  private String firstName;

  @NotNull
  @Size(min=2, max=30)
  private String lastName;
  
  @NotNull
  @Email
  private String email;

  public Spitter() {}
  
  public Spitter(String username, String password, String firstName, String lastName, String email) {
    this(null, username, password, firstName, lastName, email);
  }

  public Spitter(Long id, String username, String password, String firstName, String lastName, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object that) {
    return EqualsBuilder.reflectionEquals(this, that, "firstName", "lastName", "username", "password", "email");
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "firstName", "lastName", "username", "password", "email");
  }

}

package com.shiy.springmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

/**
 *  Spring Securiry
 *
 *  authenticated()
 *
 */

@Configuration
//@EnableWebSecurity 一样
@EnableWebMvcSecurity //启用Web安全性，必须配置在一个实现了WebSecurityConfigurer的Bean中
public class SecurityConfig extends WebSecurityConfigurerAdapter {//简单起见，直接拓展WebSecurityConfigurerAdapter

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .formLogin()//启用默认登陆页面 （难看）
        .loginPage("/login")
      .and()
        .logout()
          .logoutSuccessUrl("/")
      .and()
      .rememberMe()//不用每次去登陆 ：通过在cookie中存储token（默认是两周有效）
        .tokenRepository(new InMemoryTokenRepositoryImpl())
        .tokenValiditySeconds(2419200)
        .key("spittrKey")//设置私钥
      .and()
       .httpBasic()//启动HTTP Basic认证
         .realmName("Spittr")
      .and()
      .authorizeRequests() //配置安全性细节
        .antMatchers("/").authenticated()  //antMatchers可以替换为regexMatchers
        .antMatchers("/spitter/me").authenticated()//该路径需要验证 authenticated()
        .antMatchers(HttpMethod.POST, "/spittles").authenticated()//
        .anyRequest().permitAll()//其他所有的都允许	permitAll()
	  .and()
			.requiresChannel()
			.antMatchers("/spitter/form").requiresSecure();//;//指定/spitter/form提交的走的是Https通道 requiresInsecure：http
//	  .and()
//			.csrf().disable() 可以通过这个关闭csrf防护(跨站请求伪造)

  }

  /**
   *  基于内存的用户存储配置
   *
   * @param auth
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .inMemoryAuthentication()
        .withUser("user").password("password").roles("USER").and()//创建两个用户
        .withUser("shiy").password("123456").roles("USER", "ADMIN");
  }


}

package com.shiy.demo.controller;//package com.shiy.demo.controller;
//
//import com.shiy.demo.entity.User;
//import com.shiy.demo.jdbcTemplate.data.UserRepositoryImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * Created by DebugSy on 2017/8/17.
// */
//@Controller
//@RequestMapping(value = "/", produces = "application/json")
//public class UserController {
//
//	@Autowired
//	private UserRepositoryImpl userRepository;
//
//	@RequestMapping(method= RequestMethod.GET, value = "/{name}")
//	@ResponseBody//这样会返回想要的类型，如json
//	public User findByName(@PathVariable("name") String name){
//		User user = userRepository.findByName(name);
//		return user;
//	}
//
//	@RequestMapping(method= RequestMethod.POST, consumes = "multipart/form-data")
//	@ResponseBody//这样会返回想要的类型，如json
//	public void save(User user){
//		userRepository.save(user);
//	}
//
//	@RequestMapping(method= RequestMethod.DELETE, value = "/{name}")
//	@ResponseBody//这样会返回想要的类型，如json
//	public void delete(@PathVariable("name")String name){
//		userRepository.deleteByName(name);
//	}
//
//}

package com.shiy.demo.controller;


import com.shiy.demo.data.SpringDataUserRepository;
import com.shiy.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by DebugSy on 2017/8/18.
 */
@Controller
public class SpringDataUserController {

	@Autowired
	private SpringDataUserRepository userRepository;

	@RequestMapping(method= RequestMethod.GET, value = "/{name}")
	@ResponseBody//这样会返回想要的类型，如json
	public UserEntity findByName(@PathVariable("name") String name){
		UserEntity user = userRepository.findByName(name);
		return user;
	}

	@RequestMapping(method= RequestMethod.GET, value = "/id/{id}")
	@ResponseBody//这样会返回想要的类型，如json
	public UserEntity findById(@PathVariable("id") int id){
		UserEntity user = userRepository.findOne(id);
		return user;
	}

	@RequestMapping(method= RequestMethod.POST, consumes = "multipart/form-data")
	@ResponseBody//这样会返回想要的类型，如json
	public void save(UserEntity user){
		userRepository.save(user);
	}

	@RequestMapping(method= RequestMethod.DELETE, value = "/{name}")
	@ResponseBody//这样会返回想要的类型，如json
	public void delete(@PathVariable("name")String name){
		userRepository.deleteByName(name);
	}

	@RequestMapping(method= RequestMethod.DELETE, value = "/id/{id}")
	@ResponseBody//这样会返回想要的类型，如json
	public void delete(@PathVariable("id")int id){
		userRepository.delete(id);
	}

}

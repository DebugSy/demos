package com.shiy.spring.data;

import com.shiy.spring.jpa.data.CommonRepository;
import com.shiy.spring.jpa.data.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JpaConfig.class)
public class UserRepositoryTest {


    @Autowired
    private CommonRepository userRepository;

    @Test
    public void testFindOneUser(){
        List<User> users = userRepository.findAll();
        for (User user : users){
            System.out.println(user.getId() + ":" + user.getName() + ":" + user.getAge());
        }
    }

}

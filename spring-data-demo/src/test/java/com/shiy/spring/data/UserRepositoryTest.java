package com.shiy.spring.data;

import com.shiy.spring.jpa.data.Grade;
import com.shiy.spring.jpa.data.UserRepository;
import com.shiy.spring.jpa.data.User;
import org.h2.expression.ExpressionColumn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JpaConfig.class)
public class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOneUser(){
        List<User> users = userRepository.findAll();
        for (User user : users){
//            System.out.println(user.getId() + ":" + user.getName() + ":" + user.getAge());
        }
    }

    @Test
    public void testFindByName(){
        User user = userRepository.findOneByName(1, "a");
        System.out.println(user);
    }

    @Test
    public void testCriteriaQuery(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(cb.equal(root.get("name"), "a"));
        TypedQuery<User> query = entityManager.createQuery(cq);
        System.out.println(query.getResultList());
    }

    //这里是想实现两张没有设置关系的表进行left join
    // User表和Grade表，不设置任务关系（OneTOMany等）
    @Test
    public void testCriteriaQueryJoin(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        Join<User, Grade> join = root.join("grade");
        Predicate predicate = cb.equal(join.get("grade").as(String.class), "1111");

        CriteriaQuery<User> query1 = cq.where(predicate);

        TypedQuery<User> query = entityManager.createQuery(query1);
        System.out.println(query.getResultList());
    }

}

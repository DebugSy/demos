package com.shiy.spring.jpa.data;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonRepository extends JpaRepository<User, Integer>{

    public User findByName(String name);

}

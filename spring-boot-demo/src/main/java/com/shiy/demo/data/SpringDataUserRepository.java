package com.shiy.demo.data;


import com.shiy.demo.entity.UserEntity;
import com.shiy.demo.springdata.UserAdvanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by DebugSy on 2017/8/18.
 */
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer>,UserAdvanceRepository {

	UserEntity findByName(String name);

}

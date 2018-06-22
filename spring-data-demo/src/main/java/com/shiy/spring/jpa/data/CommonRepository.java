package com.shiy.spring.jpa.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by DebugSy on 2018/6/21.
 */
@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	@Query("select t from #{#entityName} t where t.age = ?1 and t.name = ?2")
	T findOneByName(int age, String name);

}

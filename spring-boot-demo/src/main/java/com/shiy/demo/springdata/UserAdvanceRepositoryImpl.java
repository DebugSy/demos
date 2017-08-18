package com.shiy.demo.springdata;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by DebugSy on 2017/8/18.
 */
public class UserAdvanceRepositoryImpl implements UserAdvanceRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void deleteByName(String name) {
		String sql = "delete from user where name = ?";
		entityManager.createNativeQuery(sql, name).executeUpdate();
	}
}

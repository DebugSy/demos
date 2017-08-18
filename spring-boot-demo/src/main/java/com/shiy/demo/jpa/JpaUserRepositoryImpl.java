package com.shiy.demo.jpa;//package com.shiy.demo.jpa;
//
//
//import com.shiy.demo.data.JpaUserRepository;
//import com.shiy.demo.entity.UserEntity;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
///**
// * Created by DebugSy on 2017/8/18.
// */
//@Repository
//@Transactional
//public class JpaUserRepositoryImpl implements JpaUserRepository {
//
//	@PersistenceContext
//	private EntityManager em;
//
//	@Override
//	public UserEntity findByName(String name) {
//		UserEntity user = em.find(UserEntity.class, name);
//		return user;
//	}
//
//	@Override
//	public UserEntity findById(int id) {
//		UserEntity user = em.find(UserEntity.class, id);
//		return user;
//	}
//
//	@Override
//	public void save(UserEntity user) {
//		em.persist(user);
//	}
//
//	@Override
//	public void deleteByName(String name) {
//		em.remove(findByName(name));
//	}
//}

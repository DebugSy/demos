package com.shiy.demo.jdbcTemplate.data;//package com.shiy.demo.jdbcTemplate.data;
//
//import com.shiy.demo.data.UserRepository;
//import com.shiy.demo.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.stereotype.Repository;
//
///**
// * Created by DebugSy on 2017/8/17.
// */
//@Repository
//public class UserRepositoryImpl implements UserRepository {
//
//	@Autowired
//	private JdbcOperations jdbcOperations;
//
//
//	@Override
//	public User findByName(String name) {
//		String sql = "select * from user where name=?";
//		User user = jdbcOperations.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), name);
//		return user;
//	}
//
//	@Override
//	public void save(User user) {
////		jdbcOperations.update()
//		String sql = "INSERT INTO user VALUES (?,?,?)";
//		jdbcOperations.update(sql, user.getId(), user.getName(), user.getAge());
//	}
//
//	@Override
//	public void deleteByName(String name) {
//		String sql = "DELETE FROM user WHERE name = ?";
//		jdbcOperations.update(sql, name);
//	}
//}

package com.shiy.demo.springdata;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DebugSy on 2017/8/18.
 */
@Transactional
public interface UserAdvanceRepository {

	void deleteByName(String name);

}

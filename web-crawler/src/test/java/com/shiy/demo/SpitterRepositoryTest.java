package com.shiy.demo;

import com.shiy.demo.config.DataConfig;
import com.shiy.demo.repository.StockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataConfig.class)
public class SpitterRepositoryTest {

	@Autowired
	StockRepository stockRepository;
	
	@Test
	@Transactional
	public void count() {
		assertEquals(20, stockRepository.count());
		System.out.println(1);
	}
	
}

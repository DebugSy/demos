package com.shiy.demo;

import com.shiy.demo.config.DataConfig;
import com.shiy.demo.main.ExtMarketOilStockMain;
import com.shiy.demo.model.ExtMarketOilStockModel;
import com.shiy.demo.repository.StockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static javafx.scene.input.KeyCode.L;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataConfig.class)
public class SpitterRepositoryTest {

	@Autowired
	StockRepository stockRepository;

	@Autowired
	ExtMarketOilStockMain extMarketOilStockMain;

	@Test
	@Transactional
	public void count() {
		assertEquals(1, stockRepository.count());
		System.out.println(1);
	}

	@Test
//	@Transactional
	public void run(){
		try {
			extMarketOilStockMain.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void save(){
		ExtMarketOilStockModel model = new ExtMarketOilStockModel();
		model.setDate("2017-10-13");
		model.setStock_id("000001");
		model.setStock_name("哈德大卫");
		model.setCraw_time("2018-10-13");
		model.setStock_amplitude(1);
		System.out.println("before :" + stockRepository.count());
		ExtMarketOilStockModel model1 = stockRepository.saveAndFlush(model);
		ExtMarketOilStockModel one = stockRepository.findOne(model1.getStock_id());
		System.out.println(one.getStock_id());
		System.out.println("end :" + stockRepository.count());
	}

}

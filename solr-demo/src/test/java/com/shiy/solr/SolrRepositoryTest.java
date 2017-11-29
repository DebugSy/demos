package com.shiy.solr;

import com.shiy.solr.entity.TestEntity;
import com.shiy.solr.repository.SolrRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Created by DebugSy on 2017/11/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.shiy.solr.config.SolrConfig.class)
public class SolrRepositoryTest {

	@Autowired
	private SolrRepository solrRepository;

//	@Before
//	public void init(){
//		System.err.println("开始输出：");
//	}

	@Test
	public void testFindEntityByName(){
		TestEntity entity = solrRepository.findEntityByName("test02");
		System.out.println(entity.getDescendent());
	}

//	@Test
//	public void testFindEntityByPath(){
//		TestEntity entity = solrRepository.findEntityByPath("test02");
//		System.out.println(entity.getAncestor_path());
//	}

	@Test
	public void testFindEntitysByPath(){
		List<TestEntity> entities = solrRepository.findEntitysByDescendent("shiy");
		System.out.println("开始输出：");
		for (TestEntity entity : entities){
			System.out.println(entity.getId() + ":" + entity.getName() + " " + entity.getDescendent());
		}
		System.out.println("结束输出");
	}

//	@After
//	public void destroy(){
//		System.err.println("结束输出。");
//	}

}

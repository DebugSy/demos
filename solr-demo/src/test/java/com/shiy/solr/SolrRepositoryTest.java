package com.shiy.solr;

import com.shiy.solr.entity.TestEntity;
import com.shiy.solr.repository.SolrRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by DebugSy on 2017/11/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.shiy.solr.config.SolrConfig.class)
public class SolrRepositoryTest {

	@Autowired
	private SolrRepository solrRepository;

	@Test
	public void testFindEntityByName(){
		TestEntity entity = solrRepository.findEntityByName("test01");
		System.out.println(entity.getPath());
	}

}

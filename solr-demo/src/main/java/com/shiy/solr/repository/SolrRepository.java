package com.shiy.solr.repository;

import com.shiy.solr.entity.TestEntity;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Created by DebugSy on 2017/11/24.
 */
public interface SolrRepository extends SolrCrudRepository<TestEntity, Integer> {

	TestEntity findEntityByName(String name);

}

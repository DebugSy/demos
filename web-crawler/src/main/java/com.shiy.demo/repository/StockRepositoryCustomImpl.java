package com.shiy.demo.repository;

import com.shiy.demo.model.ExtMarketOilStockModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by DebugSy on 2017/10/12.
 */
public class StockRepositoryCustomImpl implements StockRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public List<ExtMarketOilStockModel> findRecent() {
		return findRecent(10);
	}

	public List<ExtMarketOilStockModel> findRecent(int count) {
		return (List<ExtMarketOilStockModel>) entityManager.createQuery("select s from ext_market_oil_stock s order by s.postedTime desc")
				.setMaxResults(count)
				.getResultList();
	}
}

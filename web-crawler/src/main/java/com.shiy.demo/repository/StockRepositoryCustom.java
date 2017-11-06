package com.shiy.demo.repository;

import com.shiy.demo.model.ExtMarketOilStockModel;

import java.util.List;

/**
 * Created by DebugSy on 2017/10/12.
 */
public interface StockRepositoryCustom {

	List<ExtMarketOilStockModel> findRecent();

	List<ExtMarketOilStockModel> findRecent(int count);

}

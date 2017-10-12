package com.shiy.demo.repository;

/**
 * Created by DebugSy on 2017/10/12.
 */

import com.shiy.demo.model.ExtMarketOilStockModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<ExtMarketOilStockModel, String>{


}

package com.itheima.store.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;

public interface ProductService {

	List<Product> findByNew() throws SQLException;

	List<Product> findByHot() throws SQLException;

	PageBean<Product> findByPageCid(String cid, Integer pageSize) throws SQLException;

	Product findByPid(String pid) throws SQLException;

	PageBean<Product> findByPage(Integer currPage)throws SQLException;

	void save(Product product)throws SQLException;

	void update(Product product)throws SQLException;

	List<Product> findBypushDown()throws SQLException;


}

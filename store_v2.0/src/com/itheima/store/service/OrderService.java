package com.itheima.store.service;


import com.itheima.store.domain.Order;
import com.itheima.store.domain.PageBean;

public interface OrderService {

	void save(Order order);

	PageBean<Order> findByUid(Integer currPage, String uid) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws Exception;
	

}

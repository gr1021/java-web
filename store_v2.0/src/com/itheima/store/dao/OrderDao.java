package com.itheima.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;

public interface OrderDao {
	

	void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException;

	void saveOrder(Connection conn, Order order) throws SQLException;

	Integer findCountByUid(String uid) throws Exception;

	List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws Exception;


}

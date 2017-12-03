package com.itheima.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void save(Order order) {
		Connection conn = null;
		try {
			// 开启事务
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			// 执行保存操作
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
			orderDao.saveOrder(conn,order);
			// 循环保存订单中的订单项
			for (OrderItem orderItem : order.getOrderItems()) {
				orderDao.saveOrderItem(conn, orderItem);
			}
			//提交事务
			DbUtils.commitAndCloseQuietly(conn);
		} catch (Exception e) {
			//回滚事务
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
	}

	//根据用户id查询用户的订单信息
	@Override
	public PageBean<Order> findByUid(Integer currPage, String uid) throws Exception {
		PageBean<Order> pageBean = new PageBean<Order>();
		//设置参数
		pageBean.setCurrPage(currPage);
		//设置每页显示的记录数
		Integer pageSize = 3;
		pageBean.setPageSize(pageSize);
		//设置总记录数 从数据库中查询
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		Integer totalCount = orderDao.findCountByUid(uid);
		pageBean.setTotalCount(totalCount);
		//将总记录数转为double型
		double tc = totalCount;
		//设置总页数
		Double num = Math.ceil(tc / pageSize);
		//将num保存在pageBean中
		pageBean.setTotalPage(num.intValue());
		//设置每页显示的数据的集合
		int begin = (currPage-1)*pageSize;
		List<Order> list = orderDao.findPageByUid(uid,begin,pageSize);
		//保存list
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 根据订单id查找订单信息findByOid
	 */
	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		Order order = orderDao.findByOid(oid);
		return order;
	}

	@Override
	public void update(Order order) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		orderDao.update(order);
	}

}

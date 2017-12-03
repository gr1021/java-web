package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	//查询最新产品的方法
	@Override
	public List<Product> findByNew() throws SQLException {
		//ProductDao productDao = new ProductDaoImpl();
		ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		List<Product> newList = productDao.findByNew();
		return newList;
	}

	//查询最热产品的方法
	@Override
	public List<Product> findByHot() throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		List<Product> hotList = productDao.findByHot();
		return hotList;
	}

	//分类查询产品的Service
	@Override
	public PageBean<Product> findByPageCid(String cid, Integer currPage) throws SQLException {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置参数
		//设置当前页数
		pageBean.setCurrPage(currPage);
		//设置每页显示的记录数
		Integer pageSize = 12;
		pageBean.setPageSize(pageSize);
		//设置总记录数
		ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		Integer totalCount = productDao.findCountByCid(cid);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());
		//设置每页显示的记录数的集合
		int begin = (currPage - 1)*pageSize;
		List<Product> list = productDao.findPageByCid(cid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 根据id查询商品详情信息
	 */
	@Override
	public Product findByPid(String pid) throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		Product product = productDao.findByPid(pid);
		return product;
	}


	/**
	 * 后台商品信息的分页显示
	 */
	@Override
	public PageBean<Product> findByPage(Integer currPage) throws SQLException {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置参数
		//设置当前页
		pageBean.setCurrPage(currPage);
		//设置每页显示的记录数
		Integer pageSize = 10;
		pageBean.setPageSize(pageSize);
		//设置总记录数
		ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		Integer totalCount = productDao.findCount();
		pageBean.setTotalCount(totalCount);
		//设置总页数
		double tc = totalCount;
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//设置每页显示的记录数集合list
		int begin = (currPage -1)*pageSize;
		List<Product> list = productDao.findByPage(begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 后台保存商品信息的方法：save
	 */
	@Override
	public void save(Product product) throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		productDao.save(product);
	}

	/**
	 * 下架商品的方法：
	 */
	@Override
	public void update(Product product) throws SQLException {
		ProductDao productDao = (ProductDao)BeanFactory.getBean("ProductDao");
		productDao.update(product);
	}

	/**
	 * 查询已经下架的商品信息
	 */
	@Override
	public List<Product> findBypushDown() throws SQLException {
		ProductDao productDao = (ProductDao)BeanFactory.getBean("ProductDao");
		return productDao.findBypushDown();
	}

	


}

package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.domain.Category;
import com.itheima.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	//查询所有分类的方法
	@Override
	public List<Category> findAll() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		List<Category> list = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}

	/**
	 * admin
	 * 		保存添加的数据
	 */
	@Override
	public void save(Category category) throws SQLException {
		QueryRunner queryRUnner= new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category values (?,?)";
		queryRUnner.update(sql, category.getCid(),category.getCname());
	}

	
	/**
	 * 修改数据前进行查询数据
	 */
	
	@Override
	public Category findByCid(String cid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		Category category = queryRunner.query(sql, new BeanHandler<Category>(Category.class), cid);
		return category;
	}

	/**
	 * 修改分类管理数据
	 */
	@Override
	public void update(Category category) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		queryRunner.update(sql, category.getCname(),category.getCid());
	}

	
	@Override
	public void remove(String cid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		String sql = "update product set cid = null where cid = ?";
		queryRunner.update(sql, cid);
		
		sql = "delete from category where cid = ?";
		queryRunner.update(sql, cid);
	}
}

package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	//查询用户名是否存在
	@Override
	public User findByUsername(String username) throws SQLException {
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ?";
		User user = queryRunner.query(sql, new BeanHandler<User>(User.class), username);
		return user;
	}

	//保存用户注册的信息  
	@Override
	public void save(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
				user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		queryRunner.update(sql, params);
		
	}

	//用户登录的方法
	@Override
	public User login(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? and state = ?";
		User existUser = queryRunner.query(sql, new BeanHandler<User>(User.class), user.getUsername(),user.getPassword(),2);
		return existUser;
	}

	//激活用户的方法
	@Override
	public User findByCode(String code) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where code = ? ";
		User user = queryRunner.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	@Override
	public void update(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set username = ? ,password = ?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid = ?";
		Object[]  params = {user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode(),user.getUid() };
		queryRunner.update(sql, params);
	}


}

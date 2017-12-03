package com.itheima.store.service.impl;

import java.sql.SQLException;

import com.itheima.store.dao.UserDao;
import com.itheima.store.dao.impl.UserDaoImpl;
import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MailUtils;
import com.itheima.store.utils.UUIDUtils;

public class UserServiceImpl implements UserService{

	//校验用户名是否存在
	@Override
	public User findByUsername(String username) throws SQLException {
		//UserDao userDao = new UserDaoImpl();
		UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");
		User user = userDao.findByUsername(username);
		return user;
	}
	//保存注册页面的信息
	@Override
	public void save(User user) throws SQLException {
		//UserDao userDao = new UserDaoImpl();
		UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");
		user.setUid(UUIDUtils.getUUID());
		user.setState(1);// 1代表未激活，2代表激活
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		
		//发送邮件
		MailUtils.sendMail(user.getEmail(), code);
	}

	//登录页面的实现
	@Override
	public User login(User user) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");
		User existUser = userDao.login(user);
		return existUser;
	}
	
	//根据激活码查询用户名
	@Override
	public User findByCode(String code) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");
 		User existUser = userDao.findByCode(code);
		return existUser;
	}
	
	@Override
	public void update(User existUser) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");
		userDao.update(existUser);
	}

	

}

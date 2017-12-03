package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.dao.impl.CategoryDaoImpl;
import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BeanFactory;
/*
 *分类的Service实现类
 */
public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() throws SQLException {
		/*CategoryDao categoryDao = new CategoryDaoImpl();
		List<Category> list = categoryDao.findAll();*/
		/**
		 * 使用缓存优化程序，因为每次加载都需要访问数据库，效率不好，所以使用缓存优化
		 * 现在缓存中查询，若缓存中没有再去数据库查询，并存到缓存中
		 * 减少了访问数据库的次数
		 */
		
		//读取配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//获取缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		//判断缓存区中是否有list集合
		Element element = cache.get("list");
		List<Category> list = null;
		if (element == null) {
			//如果没有数据，则查询数据库，并将查询到的数据存到缓存中
			//System.out.println("缓存中没有数据，从数据库中查找");
		//	CategoryDao categoryDao = new CategoryDaoImpl();
			CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
			list = categoryDao.findAll();
			//将list存到element中
			element = new Element("list", list);
			cache.put(element);
		}else{
		//	System.out.println("缓存中有数据，从缓存中查找");
			//如果有数据，则直接从缓存中查询数据，返回给servlet
			list = (List<Category>)element.getObjectValue();
		}
		return list;
	}

	/**
	 * 后台管理
	 * 			保存添加分类管理数据的方法：save
	 */
	@Override
	public void save(Category category) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		categoryDao.save(category);
		
		//清空缓存
		//读取配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//获取缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		//从缓存区将list集合移除
		cache.remove("list");
	}

	
	/**
	 * 根据cid查询数据 进行修改
	 */
	@Override
	public Category findByCid(String cid) throws SQLException {
		CategoryDao categoryDao = (CategoryDao)BeanFactory.getBean("CategoryDao");
		Category category = categoryDao.findByCid(cid);
		return category;
	}

	/**
	 * 修改分类管理数据：update
	 */
	@Override
	public void update(Category category) throws SQLException {
		CategoryDao categoryDao = (CategoryDao)BeanFactory.getBean("CategoryDao");
		categoryDao.update(category);
		
		//清空缓存
		//读取配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//获取缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		//从缓存区将list集合移除
		cache.remove("list");
	}

	/**
	 * 删除数据的方法
	 */
	@Override
	public void remove(String cid) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		categoryDao.remove(cid);
		
		//清空缓存
		//读取配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//获取缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		//从缓存区将list集合移除
		cache.remove("list");
	}
	
	/**
	 *  
	 */
	
	
}

package com.itheima.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.impl.CategoryServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * 商品分类的Servlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询所有分类的Servlet的执行的方法：findAll
	 */
	public String findAll (HttpServletRequest req,HttpServletResponse resp){
		try{
//		CategoryService categoryService = new CategoryServiceImpl();
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = categoryService.findAll();
		//将list转化为JSON 并输出到页面上
		JSONArray fromObject = JSONArray.fromObject(list);
		//System.out.println(fromObject.toString());
		//将字符串输出到页面
		resp.getWriter().println(fromObject.toString());
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
}

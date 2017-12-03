package com.itheima.store.web.servlet;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtils;

/**
 * 后台分类管理的Servlet
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类管理查询的方法：findAll
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp){
		try{
		//调用业务层处理数据
		CategoryService categoryService = (CategoryService)BeanFactory.getBean("CategoryService");
		List<Category> list = categoryService.findAll();
		//将list存到req中
		req.setAttribute("list",list);
		//跳转页面
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "/admin/category/list.jsp";
	}
	/**
	 * 跳转到添加分类管理的页面
	 */
	public String saveUI(HttpServletRequest req , HttpServletResponse resp){
		return "/admin/category/add.jsp";
	}
	/**
	 * 保存添加的分类管理的数据：save
	 */
	public  String save(HttpServletRequest req , HttpServletResponse resp){
		try{
		//接收参数
		String cname = req.getParameter("cname");
		//封装数据
		Category category = new Category();
		category.setCid(UUIDUtils.getUUID());
		category.setCname(cname);
		//调用业务层
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		categoryService.save(category);
		//跳转页面
		resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	/**
	 * 跳转到编辑分类管理的方法：edit
	 */
	public String edit(HttpServletRequest req , HttpServletResponse resp){
		try{
			//接受参数
			String cid = req.getParameter("cid");
			// 调用业务层  根据cid查询数据
			CategoryService categoryService = (CategoryService)BeanFactory.getBean("CategoryService");
			Category category = categoryService.findByCid(cid);
			
			//存储
			req.setAttribute("category", category);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/category/edit.jsp";
	}
	
	/**
	 * 修改分类管理的方法：update
	 */
	public String update(HttpServletRequest req , HttpServletResponse resp){
		try{
		//接受数据
		Map<String, String[]> map = req.getParameterMap();
		//封装数据
		Category category = new Category();
		BeanUtils.populate(category, map);
		//处理数据
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		categoryService.update(category);
		//跳转页面
		resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");
		
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	/**
	 * 删除分类管理的方法：remove
	 */
	public String remove(HttpServletRequest req , HttpServletResponse resp){
		try{
			//接受参数
			String cid = req.getParameter("cid");
			//调用业务层处理数据
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
			categoryService.remove(cid);
			
			//跳转页面
			resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	
	
	
}

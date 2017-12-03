package com.itheima.store.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.itheima.store.domain.Category;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * 后台商品显示的Servlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 后台商品显示的方法：findAll  分页显示
	 */
	public String findAll(HttpServletRequest req,HttpServletResponse resp){
		try{
			//接收数据
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			//调用业务层处理数据
			ProductService productService = (ProductService)BeanFactory.getBean("ProductService");
			PageBean<Product> pageBean = productService.findByPage(currPage);
			
			//将list存到req域中
			req.setAttribute("pageBean", pageBean);
			//跳转页面
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/product/list.jsp";
	}
	/**
	 * 跳转到添加页面的方法：addUI
	 */
	public String addUI(HttpServletRequest req,HttpServletResponse resp){
		//查询所有分类
		try{
			//调用业务层
			CategoryService categoryService = (CategoryService)BeanFactory.getBean("CategoryService");
			List<Category> list = categoryService.findAll();
		
			req.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}
	/**
	 * 下架商品的方法：pushdown
	 */
	public String pushdown(HttpServletRequest req,HttpServletResponse resp){
		try{
			//接收参数
			String pid = req.getParameter("pid");
			//调用业务层处理数据
			ProductService productService = (ProductService)BeanFactory.getBean("ProductService");
			Product product = productService.findByPid(pid);
			
			product.setPflag(1);
			productService.update(product);
			
			//跳转页面
			resp.sendRedirect(req.getContextPath()+"/AdminProductServlet?method=findAll&currPage=1");
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return null;
	}
	
	/**
	 * 查询已经下架商品的方法：
	 */
	public String findByPushDown(HttpServletRequest req,HttpServletResponse resp){
		try{
			//调用业务层处理数据
			ProductService productService = (ProductService)BeanFactory.getBean("ProductService");
		
			List<Product> list = productService.findBypushDown();
			
			req.setAttribute("list", list);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "admin/product/pushDown_list.jsp";
	}
	/**
	 * 上架商品的方法：pushUp
	 */
	public String pushUp(HttpServletRequest req,HttpServletResponse resp){
		try{
			//接收参数
			String pid = req.getParameter("pid");
			//调用业务层处理数据
			ProductService productService = (ProductService)BeanFactory.getBean("ProductService");
			Product product = productService.findByPid(pid);
			
			product.setPflag(0);
			productService.update(product);
			
			//跳转页面
			resp.sendRedirect(req.getContextPath()+"/AdminProductServlet?method=findAll&currPage=1");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}

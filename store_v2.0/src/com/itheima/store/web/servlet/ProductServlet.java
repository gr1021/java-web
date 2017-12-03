package com.itheima.store.web.servlet;


import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.CookieUtils;

/**
 * 商品的查询
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 根据id查询某个分类下的商品：分页查询：findByCid
	 */
	public String findByCid(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接收参数cid和currpage
		String cid = req.getParameter("cid");
		Integer currPage  = Integer.parseInt(req.getParameter("currPage"));
		//调用业务层处理数据
		//ProductService productService = new ProductServiceImpl();
		ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
		PageBean<Product> pageBean = productService.findByPageCid(cid,currPage);
		//将pageBean存储到request域中
		req.setAttribute("pageBean", pageBean);
		//跳转页面
		return "/jsp/product_list.jsp";
		}catch(Exception e ){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	/**
	 * 根据id查询商品详情信息：findByPid
	 */
	public  String findByPid(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接收参数
		String pid = req.getParameter("pid");
		//调用业务层处理数据
		ProductService productService = (ProductService)BeanFactory.getBean("ProductService");
		Product product = productService.findByPid(pid);
		
		/**
		 * 商品的浏览记录的实现
		 */
		
		
		//首先获取所有的Cookie
		Cookie[] cookies = req.getCookies();
		Cookie findCookie = CookieUtils.findCookie(cookies, "history");
		//判断当前客户端是否有包含浏览记录的Cookie
		if (findCookie == null) {
			//如果没有：
			//创建Cookie 保存当前商品的id
			Cookie cookie = new Cookie("history", pid);
			cookie.setMaxAge(60*60*24*7);
			cookie.setPath("/store_v2.0");
			//并返回给浏览器
			resp.addCookie(cookie);
		}else{
			//如果有：
			//获取Cookie的值，得到有个数组， 浏览记录：1-2-3  3-4-5
			String value = findCookie.getValue();
			String[] strings = value.split("-");
			//将数组转换为linkedList集合
			LinkedList<String> list = new LinkedList<String>(Arrays.asList(strings));
			//判断集合中是否有当前商品的id：
			if (list.contains(pid)) {
				//有：
				//删除当前商品之前的id
				list.remove(pid);
				//将当前商品的id放在第一位
				list.addFirst(pid);
			}else{
				//没有：
				//判断集合的长度：
				if (list.size() >= 6) {
					//如果大于等于6：
					//删除最后一个记录
					list.removeLast();
					//将当前商品的id放在第一位
					list.addFirst(pid);
				}
						//如果小于6：
						//	将当前商品的id放在第一位
				list.addFirst(pid);
			}
					
					
		//	将集合转化为字符串，并存到Cookie中，
			StringBuffer buffer = new StringBuffer();
			//遍历集合  拼接成一个字符串
			for (String id : list) {
				buffer.append(id).append("-");
			}
			String idstr = buffer.toString().substring(0,buffer.length()-1);
			//再发送给浏览器
			//创建Cookie 保存当前商品的id
			Cookie cookie = new Cookie("history", idstr);
			cookie.setMaxAge(60*60*24*7);
			cookie.setPath("/store_v2.0");
			//并返回给浏览器
			resp.addCookie(cookie);
			
		}
		

	
		
		//存储数据到request中
		req.setAttribute("p", product);
		//跳转页面
		return "/jsp/product_info.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}

package com.itheima.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 *	商品购物模块的Servlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String addCart(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接受参数
		String pid = req.getParameter("pid");
		Integer  count = Integer.parseInt(req.getParameter("count"));
		
		//封装CartItem
		CartItem cartItem = new CartItem();
		cartItem.setCount(count);
		//调用业务层找到商品信息
		ProductService productService = (ProductService)BeanFactory.getBean("ProductService");
		Product product = productService.findByPid(pid);
		cartItem.setProduct(product);
		
		//调用Cart的方法处理数据
		//因为购物车不能每次都新建一个，这样太浪费资源
	//	Cart cart = new Cart();
		Cart cart = getCart(req);
		cart.addCart(cartItem);
		
		//页面跳转
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 获得购物车的方法
	 */
	private Cart getCart(HttpServletRequest req){
		//获取session
		HttpSession session = req.getSession();
		//获取购物车
		Cart cart = (Cart)session.getAttribute("cart");
		//判断cart是否为空
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	/**
	 *清空购物车执行的方法 
	 */
	public String clearCart(HttpServletRequest req,HttpServletResponse resp){
		try{
		//调用Cart中的方法
		Cart cart = getCart(req);
		cart.clearCart();
		//跳转页面
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	/**
	 * 删除商品执行的方法
	 */
	public String deleteCart(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接受参数
		String pid = req.getParameter("pid");
		//调用cart中的方法
		Cart cart = getCart(req);
		cart.deleteCart(pid);
		//跳转页面
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
}

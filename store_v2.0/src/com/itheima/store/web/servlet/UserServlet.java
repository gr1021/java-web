package com.itheima.store.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MyDateConverter;

/**
 * 用户模块的Servlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户跳转到注册页面的Servlet
	 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp) {

		return "/jsp/regist.jsp"; 
	}

	/**
	 * 校验用户名的方法:checkUsername
	 */
	public String checkUsername(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// 接收参数
			String username = req.getParameter("username");
			// 调用业务层处理数据
			//UserService userService = new UserServiceImpl();
			UserService userService = (UserService) BeanFactory.getBean("UserService");
			User existuser = userService.findByUsername(username);
			if (existuser == null) {
				// 用户名可以使用
				resp.getWriter().println("true");
			} else {
				// 用户名已经存在
				resp.getWriter().println("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	/**
	 * 用户注册的的执行方法：regist
	 */
	public String regist(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接收数据
		Map<String, String[]> map = req.getParameterMap();
		//封装数据
		User user = new User();
		
		//将date类型转换为String
		//ConvertUtils.register(new MyDateConverter(), Date.class);
		
		DateConverter converter = new DateConverter();
		converter.setPattern("yyyy-MM-dd");
		ConvertUtils.register(converter, Date.class);
		
		//将map中的数据封装到user中
		BeanUtils.populate(user, map);
		//处理数据
		UserService userService = (UserService) BeanFactory.getBean("UserService");
		userService.save(user);
		//调用业务层跳转页面
		req.setAttribute("msg", "注册成功！请去邮箱激活");
		return "/jsp/msg.jsp";
		
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	 
	/**
	 * 用户跳转到登录页面的方法：loginUI
	 */
	public String loginUI(HttpServletRequest req,HttpServletResponse resp){
		
		return "/jsp/login.jsp";
	}
	
	/**
	 * 用户登录的方法：login
	 */
	public String login(HttpServletRequest req , HttpServletResponse resp){
		
		try{
		//接收数据
		Map<String, String[]>  map = req.getParameterMap();
		
		//获取用户输入的验证码
		String checkImg = req.getParameter("checkImg");
		
		//从session中获取服务器提供的验证码
		String checkcode = (String)req.getSession().getAttribute("checkcode");
		
		//验证码只使用一次  
		req.getSession().removeAttribute("checkcode");
		
		//判断用户输入的验证码与服务器提供的验证码是否相同
		if (!checkImg.equalsIgnoreCase(checkcode)) {
			//说明验证码输入错误
			//在页面显示一段内容
			req.setAttribute("msg", "验证码输入不正确");
			//请求转发 跳转到登录页面
			req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
		}
		
		//封装数据
		User user = new User();
		BeanUtils.populate(user, map);
		
		//处理数据
		UserService userService = (UserService) BeanFactory.getBean("UserService");
		User existUser = userService.login(user);
		//判断用户信息是否存在
		if(existUser==null){
			req.setAttribute("msg", "用户名或密码不存在或用户未激活");
			return "/jsp/login.jsp";
		}else{
			//将信息保存到session中
			req.getSession().setAttribute("existUser", existUser);
			
			/***********记住用户名**********/
			String remember = req.getParameter("remember");
			if("remember".equals(remember)){
			Cookie cookie = new Cookie("username",existUser.getUsername());
			cookie.setMaxAge(60*60*24*7);
			cookie.setPath("/store_v2.0");
			resp.addCookie(cookie);
			
			}
			
			/***********自动登录**********/
			//获取数据
			String autoLogin = req.getParameter("autoLogin");
			if ("true".equals(autoLogin)) {
				//说明用户勾选了自动登录
				Cookie cookie2 = new Cookie("autoLogin",existUser.getUsername()+"#"+existUser.getPassword());
				cookie2.setMaxAge(60*60*24*7);
				cookie2.setPath("/store_v2.0");
				resp.addCookie(cookie2);
			}
					
			//页面重定向到首页
		//	resp.sendRedirect(req.getContextPath()+"/index.jsp");
			return "/index.jsp";
		}
		//跳转页面
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * 用户退出的方法  销毁session中的信息
	 */
	public  String logOut(HttpServletRequest req , HttpServletResponse resp){
		try {
		//手动销毁session
		req.getSession().invalidate();
		//清空自动登录中保存信息的cookie
		Cookie cookie = new Cookie("autoLogin","");
		cookie.setMaxAge(0);
		cookie.setPath("/store_v2.0");
		resp.addCookie(cookie);
		//重定向到首页
			resp.sendRedirect(req.getContextPath()+"/jsp/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 用户激活用户名的方法
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp){
		try{
		//接收激活码
		String code = req.getParameter("code");
		//根据激活码查询
		UserService userService = (UserService) BeanFactory.getBean("UserService");
		User existUser =  userService.findByCode(code);
		//进行判断
		if(existUser==null){
			//说明激活码有错误
			req.setAttribute("msg", "激活码错误，请重新激活");
		}else{
			//激活操作
			existUser.setState(2);//设置激活的状态为2
			existUser.setCode(null);
			
			userService.update(existUser);
			req.setAttribute("msg", "激活成功，请去登录");
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/jsp/msg.jsp";
		
	}
}

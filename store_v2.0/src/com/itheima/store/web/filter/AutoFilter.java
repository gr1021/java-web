package com.itheima.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.CookieUtils;

public class AutoFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//进行强转
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		//判断用户是否登录
		User user = (User)httpServletRequest.getSession().getAttribute("existUser");
		if(user!=null){
			//说明已经登录 直接放行
			chain.doFilter(httpServletRequest, httpServletResponse);
		}else{
			//没有登录，进行自动登录
			Cookie[] cookies = httpServletRequest.getCookies();
			//找到name=autoLogin的Cookie
			Cookie cookie = CookieUtils.findCookie(cookies, "autoLogin");
			//判断Cookie
			if (cookie != null) {
				//取出用户名和密码
				String value = cookie.getValue();
				String[] str = value.split("#");
				String username = str[0];
				String password = str[1];
				//封装数据
				User user2 = new User(); 
				user2.setUsername(username);
				user2.setPassword(password);
				//调用service处理数据
				try{
				//UserService userService = new UserServiceImpl();
				UserService userService = (UserService)BeanFactory.getBean("UserService");
				User existUser = userService.login(user2);
				
				//判断existUser是否为空
				if (existUser != null) {
					//自动登录成功
					httpServletRequest.getSession().setAttribute("existUser", existUser);
					chain.doFilter(httpServletRequest, httpServletResponse);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				//直接放行
				chain.doFilter(httpServletRequest, httpServletResponse);
			}
		}
	}

	@Override
	public void destroy() {
	}

}

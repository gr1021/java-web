package com.itheima.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.itheima.store.domain.User;

/**
 * 拦截一些页面的过滤器
 */
public class PrivilegeFilter implements Filter {

    public PrivilegeFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		//进行强转
		HttpServletRequest req = (HttpServletRequest)request;
		User existUser = (User) req.getSession().getAttribute("existUser");
		//判断是否登录
		if (existUser == null) {
			//没有登录
			req.setAttribute("msg", "您还没有登录，没有权限访问，请去的登录");
			req.getRequestDispatcher("/jsp/msg.jsp").forward(req, response);
			return;
		}
		chain.doFilter(req, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

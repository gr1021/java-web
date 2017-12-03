package com.itheima.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的Servlet
 */
public class BaseServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置中文处理问题
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		//接收参数
		String methodName = req.getParameter("method");
		
		if(methodName == null || "".equals(methodName)){
			resp.getWriter().println("method参数为null");
		}
		//获得Class对象
		Class clazz = this.getClass();
		//获得子类的方法
		try {
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//执行方法
			String  path = (String)method.invoke(this, req,resp);
			//判断路径是否为空
			if(path!=null){
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}

}

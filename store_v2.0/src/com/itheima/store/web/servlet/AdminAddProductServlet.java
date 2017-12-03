package com.itheima.store.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itheima.store.domain.Category;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtils;


/**
 * 后台添加商品的Servlet
 */
public class AdminAddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		//创建Product对象  进行封装数据
		Product product = new Product(); 
		//创建磁盘文件工厂
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		//设置缓冲区的大小  如果超出缓冲区大小就会产生临时文件
		diskFileItemFactory.setSizeThreshold(3*1024*1024);
		//设置临时文件存放的路径
		//diskFileItemFactory.setRepository(repository);
		//h获得核心解析类
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		fileUpload.setHeaderEncoding("UTF-8");//设置文件上传的中文乱码问题
		//fileUpload.setFileSizeMax(fileSizeMax);//设置上传文件总大小
		//fileUpload.setSizeMax(sizeMax);//设置单个文件的大小
		//解析request对象 得到一个list集合
		List<FileItem> list = fileUpload.parseRequest(request);
		//将每个部分存到map集合中
		Map<String , String> map = new HashMap<String,String>();
		String fileName = null;
		//遍历集合
		for (FileItem fileItem : list) {
			if (fileItem.isFormField()) {
				//普通项
				String name = fileItem.getFieldName();
				String value = fileItem.getString("UTF-8");//普通项的中文乱码问题
				System.out.println(name+"---"+value);
				map.put(name, value);
			}else{
				//文件上传项
				//获取文件名
				fileName = fileItem.getName();
				System.out.println("文件名："+fileName);
				//获得文件输入流
				InputStream is = fileItem.getInputStream();
				//获得文件上传的路径
				String path = this.getServletContext().getRealPath("/products/1");
				//获得输出流
				OutputStream os = new FileOutputStream(path+"/"+fileName);
				//实现流的对接
				int len = 0;
				byte[] bys = new byte[1024];
				while((len = is.read(bys))!= -1){
					os.write(bys, 0, len);
				}
				//释放资源
				is.close();
				os.close();
			}
		}
		//封装数据
		BeanUtils.populate(product, map);
		//设置参数
		product.setPid(UUIDUtils.getUUID());
		product.setPdate(new Date());
		product.setPflag(0);
		product.setPimage("products/1/"+fileName);
		Category category = new Category();
		category.setCid(map.get("cid"));
		product.setCategory(category);
		
		//调用业务层处理数据
		ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
		productService.save(product);
		//页面跳转
		response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findAll&currPage=1");
		//
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

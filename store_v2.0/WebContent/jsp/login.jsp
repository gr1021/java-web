<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>会员登录</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" />
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	body {
		margin-top: 20px;
		margin: 0 auto;
	}

	.carousel-inner .item img {
		width: 100%;
		height: 300px;
	}

	.container .row div { /* position:relative;
	 	float:left; */
	
	}

	font {
		color: #666;
		font-size: 22px;
		font-weight: normal;
		padding-right: 17px;
	}
</style>

	<script type="text/javascript">
		function changeImg(){
			document.getElementById("img1").src = "/store_v2.0/CheckImgServlet?time="+ new Date().getTime();
		}
	
	</script>
</head>
<body>
	
	<%@ include file="menu.jsp" %>
	
	<div class="container"
		style="width: 100%; height: 460px; background: #FF2C4C url('${pageContext.request.contextPath}/images/loginbg.jpg') no-repeat;">
		<div class="row">
			<div class="col-md-7">
				<!--<img src="${pageContext.request.contextPath}/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
			</div>

			<div class="col-md-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN<br>
					<%
					String msg = (String)request.getAttribute("msg");
					%>
					<%=msg == null ? "" : msg%>
					<div>&nbsp;</div>
					<form action="${pageContext.request.contextPath }/UserServlet" class="form-horizontal"  id="form1" method="post">
						<input type="hidden" name="method" value="login"> 
						
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="username" id="username" value="${cookie.username.value }"
									placeholder="请输入用户名">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-6">
								<input type="password" name="password" class="form-control" id="inputPassword3"
									placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-4">
								<input width="20px" type="text" class="form-control" name="checkImg" id="inputPassword3"
									placeholder="请输入验证码">
							</div>
							<div class="col-sm-4">
								<%-- <img src="${pageContext.request.contextPath}/image/captcha.jhtml" /> --%>
								<img id="img1" alt="验证码" src="/store_v2.0/CheckImgServlet">
								<a href="#" onclick="changeImg()">看不清，换一张</a>
							</div>

						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox" name="autoLogin" value="true"> 自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> 
									<input type="checkbox" name="remember" value="remember"> 记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="登录" name="submit"
									border="0"
									style="background: url('${pageContext.request.contextPath}/images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div style="margin-top: 50px;">
		<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势"
			title="我们的优势" />
	</div>

	<div style="text-align: center; margin-top: 5px;">
		<ul class="list-inline">
			<li><a>关于我们</a></li>
			<li><a>联系我们</a></li>
			<li><a>招贤纳士</a></li>
			<li><a>法律声明</a></li>
			<li><a>友情链接</a></li>
			<li><a target="_blank">支付方式</a></li>
			<li><a target="_blank">配送方式</a></li>
			<li><a>服务声明</a></li>
			<li><a>广告声明</a></li>
		</ul>
	</div>
	<div style="text-align: center; margin-top: 5px; margin-bottom: 20px;">
		Copyright &copy; 2005-2016 传智商城 版权所有</div>
</body>
</html>
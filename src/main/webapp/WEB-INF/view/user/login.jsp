<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!--用百度的静态资源库的cdn安装bootstrap环境-->
		<!-- Bootstrap 核心 CSS 文件 -->
		<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
		<!--font-awesome 核心我CSS 文件-->
		<link href="http://cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
	
		<!-- 在bootstrap.min.js 之前引入 -->
		<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
		<!-- Bootstrap 核心 JavaScript 文件 -->
		<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
		<!--jquery.validate-->
		<script type="text/javascript" src="resource/js/jqueryvalidate/jquery.validate.min.js" ></script>
<!-- 		<script type="text/javascript" src="js/message.js" ></script> -->
		<style type="text/css">
			body{background:url(resource/images/meinv.jpg) no-repeat;background-size: cover;font-size: 16px;}
			.form{background: rgba(255,255,255,0.2);width:400px;margin:100px auto;}
			#login_form{display: block;}
			#register_form{display: none;}
			.fa{display: inline-block;top: 27px;left: 6px;position: relative;color: #ccc;}
			input[type="text"],input[type="password"]{padding-left:26px;}
			.checkbox{padding-left:21px;}
		</style>
	</head>
	<body> 
	<div class="container">
		<div class="form row">
			<form  class="form-horizontal col-sm-offset-3 col-md-offset-3" id="login_form" method="post" action="login">
			 
					
				<h3 class="form-title">请登录您的账号</h3>
				<div class="col-sm-9 col-md-9">
					<div class="form-group">
						<i class="fa fa-user fa-lg"></i>
						<input class="form-control required" type="text" placeholder="Username" name="username" autofocus="autofocus" maxlength="20"/>
					</div>
					<div class="form-group">
					
							<i class="fa fa-lock fa-lg"></i>
							<input class="form-control required" type="password" placeholder="Password" name="password" maxlength="8"/>
				<span style="color:red">${error}</span>
					</div>
				 	<div class="form-group">
					<a href="register">	<input type="button" class="btn btn-success pull-right" value="注册 "/></a>  
					</div>
					
					<div class="form-group">  
						<input type="checkbox" value="1" name="check" >记住密码
						<input type="submit" class="btn btn-success pull-right" value="Login "/>   
					</div>
				</div>
			</form>
		</div>

		<div class="form row">
			<form class="form-horizontal col-sm-offset-3 col-md-offset-3" id="register_form">
				<h3 class="form-title">Login to your account</h3>
				<div class="col-sm-9 col-md-9">
					<div class="form-group">
						<i class="fa fa-user fa-lg"></i>
						<input class="form-control required" type="text" placeholder="Username" name="username" autofocus="autofocus"/>
					</div>
					<div class="form-group">
							<i class="fa fa-lock fa-lg"></i>
							<input class="form-control required" type="password" placeholder="Password" id="register_password" name="password"/>
					</div>
					<div class="form-group">
							<i class="fa fa-check fa-lg"></i>
							<input class="form-control required" type="password" placeholder="Re-type Your Password" name="rpassword"/>
					</div>
					<div class="form-group">
							<i class="fa fa-envelope fa-lg"></i>
							<input class="form-control eamil" type="text" placeholder="Email" name="email"/>
					</div>
					<div class="form-group">
						<input type="submit" class="btn btn-success pull-right" value="Sign Up "/>
						<input type="submit" class="btn btn-info pull-left" id="back_btn" value="Back"/>
					</div>
				</div>
			</form>
		</div>
		</div>
		 
	</body>
</html>
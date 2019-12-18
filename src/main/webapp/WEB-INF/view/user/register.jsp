<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="resource/js/jqueryvalidate/jquery-3.2.1/jquery.js"></script>
<script type="text/javascript" src="resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="resource/js/jqueryvalidate/localization/messages_zh.js"></script>
<script type="text/javascript">
$("#form").validate;
function add(){
	alert("验证前");
	$("#form").valid();
	alert('校验结束')
}
</script>
	<style type="text/css">
		body {
	
				background: url(resource/images/meinv.jpg)
	
		}
		
	</style>
</head>
<body>
<body>
	<nav class="nav fixed-top justify-content-center" > <h2 style="color:#000000">欢迎注册</h2> </nav>
	<div class="container-fulid" style="margin-top:80px;height:600px">
		 <div class="container" >
		 	<div class="row">
		 		<div class="col-md-6 offset-3" style="background:url(/resource/images/login_backup.jpg);">
		 			<form:form modelAttribute="user" max="8" min="2" id="form" action="" method="post" >
						  <div class="form-group">
						    <label >用户名</label>
						    <form:input path="username"  class="form-control" 
						     aria-describedby="emailHelp" remote="/checkname" />
							<form:errors path="username"></form:errors>
						  </div>
						  
						  <div class="form-group">
						    <label>密码</label>
						    <form:password  path="password" class="form-control"  aria-describedby="emailHelp"/>
							<form:errors path="password"></form:errors>
						  </div>
						  <button type="submit" class="btn btn-primary">Submit</button>
						  <input type="button" class="btn btn-primary" onclick="add()" value="提交"/>
						</form:form>
		 		</div>
		 	</div>
		 </div>
	</div>
	<nav class="nav fixed-bottom justify-content-center" > cms 网站 </nav>
</body>
</html>
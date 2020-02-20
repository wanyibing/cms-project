<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark "  >
  <div class="collapse navbar-collapse" id="navbarSupportedContent" style="background:#F0F0F0" >
    
    <ul class="navbar-nav mr-auto">
    	<li class="nav-item">
           <a class="nav-link" href="index"><img src="/resource/images/logo.png"> </a>
      </li> 
    </ul>
    
    <form class="form-inline my-2 my-lg-0" style="margin-right:30%" action="/article/search" method="get" >
    <%--   <input class="form-control mr-sm-2" name="key" type="search" value="${key}" placeholder="查询" aria-label="搜索">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜索</button> --%>
      <input type="search" name="key" value="${key}" placeholder="查询" >
      <button>搜索</button>
    </form>
    
    <div>
    	<ul class="nav">
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/resource/images/guest.jpg"> </li>
    		
    		<c:if test="${sessionScope.loingSessionKey!=null}">
    		<li class="nav-item nav-link">${sessionScope.loingSessionKey.username}</li>
    		<li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	用户信息
		        </a>
		            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
			          <a class="dropdown-item" href="home">进入个人中心</a>
			           <a class="dropdown-item"  href="javascript:void()" onclick="dj()">进入管理中心</a>
			          <a class="dropdown-item" href="#">个人设置</a>
			          <div class="dropdown-divider"></div>
			          <a class="dropdown-item" href="logout">退出</a>
			        </div>
		      </li>
		      </c:if>
		     
		      <c:if test="${sessionScope.loingSessionKey==null}">
			       <li class="nav-item nav-link"><a href="login">登录</a></li>
		      </c:if>
      
    	</ul>
    </div>
  </div>
</nav><!--  头结束 -->
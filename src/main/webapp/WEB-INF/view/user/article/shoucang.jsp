<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- <div class="container-fluid"> -->
	<table class="table" >
		<!-- articlePage -->
		 </style>
 	<style type="text/css">
body { 
	
		background: url(resource/images/123.jpg)
	
		}
		
	</style>
	 
        <tbody><tr>
        	<td><input type="text" name="text" ><input type="button" value="查询"></td>
        </tr>
        	<c:forEach items="${articlePage.list}" var="article">
        		<tr>
        			<td>${article.id}</td>
        			<td>${article.text}</td>
        			<%-- <td>${article.url}</td> --%>
        			<td>${article.created}</td>
        			<%-- <td><fmt:formatDate value="${article.created}" pattern="yyyy年MM月dd日"/></td> --%>
        			 
        			<td width="200px">
        				<input type="button" value="删除"  class="btn btn-danger" onclick="del(${article.id})">
        			</td>
        		</tr>
        	</c:forEach>
        </tbody>
      </table>
      
      <nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item disabled">
		      <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
		    </li>
		   	<c:forEach begin="1" end="${articlePage.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    
		   
		    <li class="page-item">
		      <a class="page-link" href="#">Next</a>
		    </li>
		  </ul>
		</nav>
 
<script>
 function del(id){ 
	if(!confirm("您确认删除么？"))
		return;
	
	$.post('deleteshoucang',{id:id},
			function(data){
				if(data==true){
					alert("刪除成功") 
					$("#workcontent").load("shoucang");
				}else{
					alert("刪除失敗")
				}
				
	},"json"				
	)
}  
 
 function name() {
	
}
 
function gopage(page){
	$("#workcontent").load("shoucang?page="+page);
}
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
	div.mytopnav {
		position:absolute; width:100%; text-align:right;height:600px;
	}
	div.mymes{
		margin-right:80px;
		margin-top:5px;
	}
</style>
<div class="mytopnav">
	<div class="mymes">
		<a href="<%=request.getContextPath() +"/User/login.jsp" %>">登录</a>&nbsp&nbsp
		<a href="<%=request.getContextPath() +"/User/register.jsp" %>">注册</a>&nbsp&nbsp
		<a href="<%=request.getContextPath() +"/index.jsp" %>">返回主页</a>
	</div>
</div>
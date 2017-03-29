<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/Normal/bootstrap_cong.jsp" %>
<script src="<%=request.getContextPath() +"/jquery.js" %>"></script>
<Style type="text/css">
	div.mylogin {
		position:absolute; top:150px; left:40%; width:30%;
	}
</Style>
<title>登录页面</title>
</head>
<body>
<%@ include file="/Normal/nav.jsp" %>
<div class="mylogin">
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-5">
				<input id="username" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-5">
				<input id="password" type="password" class="form-control" />
			</div>
		</div>
		<div class="form-group" id="error_mes" style="display:none">
			<label for="blank" class="col-sm-1 control-label"></label>
			<label for="message" class="col-sm-5 control-label">用户名或密码错误</label>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-5">
				<button type="button" class="btn btn-default" id="submit">登录</button>&nbsp
				<button type="button" class="btn btn-default" id="reset">重置</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>

<script>
	$(document).ready(function(){
		$("#submit").click(function(){
			var username = $("#username").val();
			var password = $("#password").val();
			$.ajax({
				url:"<%=request.getContextPath()+"/User/user_login"%>",
				type:'POST',
				async:false,
				data:{"user.username":username,"username.password":password},
				dataType:'json',
				success:function(res){
					if(res=="success"){
						window.location.href = "<%=request.getContextPath()+"/index.jsp"%>";
					}else{
						$("#error_mes").show();
					}
				},
				error:function(){
					alert("网络异常");
				}
			});
		});
		
		$("#reset").click(function(){
			$("#username").val("");
			$("#password").val("");
			$("#error_mes").hide();
		});
	});
</script>
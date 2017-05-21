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
	div.myregister {
		position:absolute; top:150px; left:30%; width:40%;
		padding-left:9%; padding-top:3%; padding-bottom:3%;
		border-style:dotted;border-width:2px;border-color:#DCDCDC;
		background-color:#FFEFD5;
	}
</Style>
<title>注册页面</title>
</head>
<body>
<%@ include file="/Normal/nav.jsp" %>
<div class="myregister">
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-5">
				<input id="username" type="text" class="form-control"/>
			</div>
			<label id="error_mes" class="col-sm-4 control-label" style="display:none">
				用户名已存在
			</label>
		</div>
		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">姓名</label>
			<div class="col-sm-5">
				<input id="name" type="text" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-5">
				<input id="password" type="password" class="form-control" />
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-5">
				<button id="submit" class="btn btn-default" type="button">注册</button>&nbsp
				<button id="reset" class="btn btn-default" type="button">重置</button>
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
			var name = $("#name").val();
			var password = $("#password").val();
			
			var format = format_userinfo(username, name, password);
			if(!format){
				return;
			}
			
			$.ajax({
				url:"<%=request.getContextPath()+"/User/user_register"%>",
				type:'POST',
				async:false,
				data:{"user.username":username,"user.name":name,"user.password":password},
				dataType:'json',
				success:function(res){
					if(res=="success"){
						window.location.href = "<%=request.getContextPath()+"/Index/index"%>";
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
			$("#name").val("");
			$("#password").val("");
			$("#error_mes").hide();
		});
		
		
	});

	function format_userinfo(username, name, password){
		if(username==""){
			alert("用户名不能为空");
			return false;
		}
		
		if(username.length<4 || username.length>10){
			alert("用户名长度不合法，长度应为4-10位");
			return false;
		}
		
		if(password==""){
			alert("密码不能为空");
			return false;
		}
		
		return true;
	}
</script>
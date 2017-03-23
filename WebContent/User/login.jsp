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
	<s:form action="/User/user_login" method="post" theme="simple" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-5">
				<input name="user.username" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-5">
				<input name="user.password" type="password" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-5">
				<div class="checkbox">
					<label>
						<input type="checkbox"> 请记住我
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-5">
				<s:submit class="btn btn-default" value="登录"></s:submit>&nbsp
				<s:reset class="btn btn-default" value="重置"></s:reset>
			</div>
		</div>
	</s:form>
</div>
</body>
</html>
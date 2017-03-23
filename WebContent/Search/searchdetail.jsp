<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/Normal/bootstrap_cong.jsp" %>
<script src="<%=request.getContextPath() +"/jquery.js" %>"></script>

<style type="text/css">
	div.mycontext {
		position:absolute; top:30%; width:100%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_title {
		position:absolute; left:20%; width:60%; height:150px;
		text-align:center;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_main {
		position:absolute; top:150px; left:20%; width:60%; height:150px;
		text-align:center;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_outline {
		position:absolute; top:150px; left:80%; width:15%; height:150px;
		text-align:center;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_label {
		position:absolute; top:150px; left:5%; width:15%; height:150px;
		text-align:center;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
</style>

<title>判决详情</title>
</head>
<body>
<%@ include file="/Normal/nav.jsp" %>
<div class="mycontext">
	<div class="mycontext_title">
		<h3>标题</h3>
	</div>
	<div class="mycontext_main">
	</div>
	<div class="mycontext_outline"></div>
	<div class="mycontext_label"></div>
</div>
</body>
</html>
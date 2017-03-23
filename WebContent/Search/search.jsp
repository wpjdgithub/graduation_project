<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="/Normal/bootstrap_cong.jsp" %>

<!--可无视-->
<link rel="stylesheet" type="text/css" href="../Paginator/base.css"/>

<!--主要样式-->
<link rel="stylesheet" type="text/css" href="../Paginator/pageGroup.css"/>
<script type="text/javascript" src="../Paginator/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="pageGroup.js"></script>
<title>列表页</title>

<style type="text/css">
	div.mysearchfilter {
		position:absolute; left:5%; top:50%; width:15%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mysearchresult {
		position:absolute; left:22%; top:50%; width:73%; min-height:50%;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		z-index:-1;
	}
	div.mysearchsort {
		float:left; margin-left:5%; width:90%; height:50px;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mysearchres {
		float:left; margin-top:1%; margin-left:5%; width:90%; height:150px;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		text-align:center;
	}
	div.mysearchpage {
		float:right; margin-right:5%; width:30%; height:60px;
	}
</style>

</head>
<body>
<%@ include file="../Normal/topnav.jsp" %>
<div class="mysearchfilter">
	<%@ include file="searchfilter.jsp" %>
</div>
<div class="mysearchresult">
	<div class="mysearchsort">
	<button type="button" class="btn btn-default">默认按钮&#8593</button>
	<button type="button" class="btn btn-default">默认按钮&#8595</button>
	</div>
	<div class="mysearchres">
		<a href="<%=request.getContextPath() +"/Search/searchdetail.jsp" %>">第一篇文书</a><br/>
		<font>法院名称</font>
		<font>判决结果</font>
		<font>判决时间</font>
	</div>
	<div class="mysearchres">
	</div>
	<div class="mysearchres">
	</div>
	<div class="mysearchres">
	</div>
	<div class="mysearchpage">
		<div id="pageGro" class="cb" style="float:left">
			<div class="pageUp">上一页</div>
    		<div class="pageList">
        		<ul>
            		<li>1</li>
            		<li>2</li>
            		<li>3</li>
            		<li>4</li>
            		<li>5</li>
        		</ul>
    		</div>
    		<div class="pageDown">下一页</div>
		</div>
	</div>
</div>
</body>
</html>
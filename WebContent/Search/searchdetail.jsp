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
		position:absolute; left:15%; width:55%; height:150px;
		text-align:center; padding:0 4% 0 4%;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_main {
		position:absolute; top:150px; left:15%; width:55%; height:150px;
		text-align:center;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	
	div.mycontext_relation {
		position:absolute; left:70%; width:15%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_outline {
		width:100%; min-height:100px;
		padding:15px 0 10px 10px;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_law {
		width:100%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		padding:15px 0 0 10px;
	}
	ul.mycontext_law {
		padding:0 0 5px 10px;
		margin:0 10px 10px 10px;
		font-size:12px;
	}
	li.mycontext_law {
		margin:6px 0 0 0;
	}
	div.mycontext_recommend {
		width:100%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		padding:15px 0 0 10px;
	}
	table.mycontext_summary {
		border-collapse:separate; border-spacing:10px 5px;
		font-size:10px;
	}
	
	td.mycontext_summary_left {
		width:55px; vertical-align:top
	}
</style>

<title>判决详情</title>
</head>
<body>
<%@ include file="/Normal/nav.jsp" %>
<div class="mycontext">
	<div class="mycontext_title">
		<div style="height:70%">
			<h3>深圳市迅雷网络技术有限公司与福建网龙计算机网络信息技术有限公司侵害信息网络传播权纠纷再审民事裁定书</h3>
		</div>
		
	</div>
	<div class="mycontext_main">
	</div>
	<div class="mycontext_relation">
	<div class="mycontext_outline">
		<h5><b style="font-size:15px;">基本信息</b></h5>
		<table class="mycontext_summary">
			<tr>
				<td class="mycontext_summary_left">案由:</td>
				<td>知识产权</td>
			</tr>
			<tr>
				<td class="mycontext_summary_left">审理法院:</td>
				<td>柳州铁路运输法院柳州铁路运输法院</td>
			</tr>
			<tr>
				<td class="mycontext_summary_left">案件类型:</td>
				<td>执行案件</td>
			</tr>
		</table>
	</div>
	<div class="mycontext_law">
		<h5><b style="font-size:15px;">相关法律依据</b></h5>
		<ul class="mycontext_law">
			<li class="mycontext_law">中华人民专利法(2008修正) 第56条</li>
			<li class="mycontext_law">中华人民专利法(2008修正) 第57条</li>
			<li class="mycontext_law">中华人民共和国专利法(2008修正) 第58条</li>
			<li class="mycontext_law">中华人民共和国专利法(2008修正) 第59条</li>
			<li class="mycontext_law">中华人民共和国专利法(2008修正) 第56条</li>
		</ul>
	</div>
	<div class="mycontext_recommend">
		<h5><b style="font-size:15px;">相似案例</b></h5>
		<ul class="mycontext_law">
			<li class="mycontext_law">中华人民专利法(2008修正) 第56条</li>
			<li class="mycontext_law">中华人民专利法(2008修正) 第57条</li>
			<li class="mycontext_law">中华人民共和国专利法(2008修正) 第58条</li>
			<li class="mycontext_law">中华人民共和国专利法(2008修正) 第59条</li>
			<li class="mycontext_law">中华人民共和国专利法(2008修正) 第56条</li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>
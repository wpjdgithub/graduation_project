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
		position:absolute; left:15%; top:370px; width:15%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		z-index:-1;
	}
	div.mysearchresult {
		position:absolute; left:32%; top:370px; width:53%; min-height:50%;
		z-index:-1;
	}
	div.mysearchsort {
		float:left; margin-left:1%; width:98%; height:50px;
	}
	div.mysearchres {
		float:left; margin-top:1%; margin-left:1%; width:98%; min-height:150px;
		border-style:solid;border-width:2px 0 0 0;border-color:#DCDCDC;
		padding-left:15px; padding-top:8px;
	}
	div.mysearchpage {
		float:right; margin-right:8%; width:30%; height:60px;
	}
	
	div.mydetailmes {
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		padding-left:15px; padding-top:10px; padding-bottom:10px;
		font-size:12px; margin-bottom:3px;
	}
	div.mymestitle {
		margin-bottom:5px; font-size:15px;
	}
	
	div.mysource {
		margin-bottom:3px; font-size:12px;
	}
	a.detail {
		float:right;
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
		<div class="mymestitle">
			<strong><a>韦永忠滥用职权罪一审刑事判决书</a></strong>
		</div>
		<div class="mysource">
			北京市第二中级人民法院/2014.08.18
			<a class="detail">展开</a>
		</div>
		<div class="mydetailmes">
			<table>
				<tr>
					<td>案由：</td>
					<td></td>
				</tr>
				<tr>
					<td>审理程序：</td>
					<td></td>
				</tr>
				<tr>
					<td>文书类型：</td>
					<td>判决书</td>
				</tr>
				<tr>
					<td>来源：</td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="mysource">
			<strong>核心词汇：</strong>
			按时吃大家撒课程表南京市大参加大
		</div>
	</div>
	<div class="mysearchres">
		<div class="mymestitle">
			<strong><a>韦永忠滥用职权罪一审刑事判决书</a></strong>
		</div>
		<div class="mysource">
			北京市第二中级人民法院/2014.08.18
		</div>
		<div class="mysource">
			<strong>核心词汇：</strong>
			按时吃大家撒课程表南京市大参加大
			<a id="" class="detail">展开</a>
		</div>
		<div class="mydetailmes">
			<table>
				<tr>
					<td>案由：</td>
					<td></td>
				</tr>
				<tr>
					<td>审理程序：</td>
					<td></td>
				</tr>
				<tr>
					<td>文书类型：</td>
					<td>判决书</td>
				</tr>
				<tr>
					<td>来源：</td>
					<td></td>
				</tr>
			</table>
		</div>
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

<script>
	$(document).ready(function(){
		$(".detail").click(function(){
			$(this).closest(".mysource").next().toggle(1000);
		});
	});
</script>
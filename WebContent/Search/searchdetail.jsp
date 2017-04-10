<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/Normal/bootstrap_cong.jsp" %>

<style type="text/css">
	div.mycontext {
		position:absolute; top:30%; width:100%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_title {
		position:absolute; left:15%; width:54%; height:150px;
		text-align:center; padding:0 4% 0 4%;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_main {
		position:absolute; top:150px; left:15%; width:54%; height:auto;
		padding:2% 4% 2% 4%;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	
	div.mycontext_relation {
		position:absolute; left:70%; width:15%; height:auto;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
	}
	div.mycontext_outline {
		width:100%; min-height:100px;
		padding:5px 0 10px 10px;
	}
	div.mycontext_law {
		width:100%; height:auto;
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
		padding:15px 0 0 10px;
	}
	table.mycontext_summary {
		border-collapse:separate; border-spacing:10px 5px;
		font-size:12px;
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
			<h3><s:property value="detail.brief.title"></s:property></h3>
		</div>
		
	</div>
	<div class="mycontext_main">
		<div style="height:70%; text-indent:2em">
			<s:iterator value="detail.context">
				<p>
					<s:iterator value="list">
						<s:if test="%{needExplain==true}">
							<a title="法律宝典" data-container="body" 
								data-toggle="popover" data-placement="right"
           						data-content=&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<s:property value="explanation"></s:property>>
           						<s:property value="value" /></a>
						</s:if>
						<s:else>
							<s:property value="value" />
						</s:else>		
					</s:iterator>
				</p>
			</s:iterator>
		</div>
	</div>
	<div class="mycontext_relation">
	<div class="mycontext_outline">
		<h5><b style="font-size:15px;">基本信息</b></h5>
		<table class="mycontext_summary">
			<tr>
				<td class="mycontext_summary_left">案由:</td>
				<td><s:property value="detail.brief.brief"></s:property></td>
			</tr>
			<tr>
				<td class="mycontext_summary_left">审理法院:</td>
				<td><s:property value="detail.brief.court"></s:property></td>
			</tr>
			<tr>
				<td class="mycontext_summary_left">文书类型:</td>
				<td><s:property value="detail.brief.type_text"></s:property></td>
			</tr>
		</table>
	</div>
	<div class="mycontext_law">
		<h5><b style="font-size:15px;">相关法律依据</b></h5>
		<ul class="mycontext_law">
			<s:iterator value="detail.relatedLaw">
				<li class="mycontext_law">
					<s:property value="title"></s:property>
				</li>
			</s:iterator>
		</ul>
	</div>
	<div class="mycontext_recommend">
		<h5><b style="font-size:15px;">相似案例</b></h5>
		<ul class="mycontext_law">
			<s:iterator value="detail.relatedCase">
				<li class="mycontext_law">
					<s:property value="title"></s:property>
				</li>
			</s:iterator>
		</ul>
	</div>
	</div>
</div>
</body>
</html>

<script>
$(function (){
    $("[data-toggle='popover']").hover(function(){
    	$(this).popover("show");
    },function(){
    	$(this).popover("hide");
    });
});
</script>
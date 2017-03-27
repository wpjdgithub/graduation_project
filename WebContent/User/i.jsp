<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/Normal/bootstrap_cong.jsp" %>
<script src="<%=request.getContextPath() +"/jquery.js" %>"></script>

<style type="text/css">
	div.mytab {
		position:absolute;left:15%;top:20%;width:70%;height:70%;
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		padding:10px 20px 10px 20px;
	}
	
	#basic {
		position:absolute;left:10%; top:20%; width:80%;
	}
	
	div.basic_mes {
		width:70%; height:70px; line-height:70px;
		border-style:dotted;border-width:2px 0 0 0;border-color:#DCDCDC;
		font-size:12px;
	}
	
	div.basic_detail {
		position:absolute; height:100%; width:10%;
	}
	
	div.basic_value {
		position:absolute; left:15%; height:100%; width:20%;
	}
	
	#mycase {
		position:absolute;left:10%; top:20%; width:80%; height:80%;
	}
	
	div.mycase_list {
		width:100%; height:80%; overflow:auto;
		border-style:dotted;border-width:0 0 2px 0;border-color:#DCDCDC;
	}
	
	div.mycase_delete {
		width:100%; height:10%; overflow:auto;
	}
</style>
<title>个人主页</title>
</head>
<body>
<%@ include file="/Normal/nav.jsp" %>
<div class="mytab">
	<ul id="myTab" class="nav nav-tabs">
		<li><a href="#basic" data-toggle="tab">基础信息</a></li>
		<li class="active"><a href="#mycase" data-toggle="tab">个人案例</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade" id="basic">
			<div class="basic_mes">
				<div class="basic_detail">用户名</div>
				<div class="basic_value">wpjd2</div>
			</div>
			<div class="basic_mes">
				<div class="basic_detail">姓名</div>
				<div class="basic_value">王培霁</div>
			</div>
		</div>
		<div class="tab-pane fade in active" id="mycase">
			<div class="mycase_list">
				<table class="table table-striped">
					<tr>
						<td><input type="checkbox"></td>
						<td style="width:80%">案例标题</td>
						<td>上传时间</td>
					</tr>
					<tr>
						<td><input type="checkbox"></td>
						<td>案例标题案例标题案例标题案例标题案例标题案例标题案例标题
							案例标题案例标题案例标题案例标题案例标题案例标题案例标题案例标题
							案例标题案例标题案例标题</td>
						<td>2017/01/04</td>
					</tr>
					<tr>
						<td><input type="checkbox"></td>
						<td>案例标题案例标题案例标题案例标题案例标题案例标题案例标题
							案例标题案例标题案例标题案例标题案例标题案例标题案例标题案例标题
							案例标题案例标题案例标题
							</td>
						<td>2017/01/04</td>
					</tr>
					<tr>
						<td>
							<button type="button" id="upload" class="btn btn-default btn-sm" style="height:30px">
         					 	<span class="glyphicon glyphicon-plus"></span> 添加
        					</button>
        					<s:form namespace="/Case" action="user_uploadcase" 
              					enctype="multipart/form-data" method="post">
        						<input type="file" name="file1" id="file" style="display:none"/>
        						<input type="submit" id="file_submit" style="display:none" />
        					</s:form> 
        				</td>
						<td>
							<input type="text" id="fileName" disabled="disable" 
								class="form-control" style="height:30px;display:none" />
						</td>
						<td>
							<button type="button" id="decide" class="btn btn-default btn-sm" style="height:30px;display:none">
         					 	 上传
        					</button>
        				</td>
					</tr>
				</table>
			</div>
			<div class="mycase_delete">
				<input type="button" class="btn btn-default" value="删除所选" />
			</div>
		</div>
	</div>
</div>
	
<script>
	$(document).ready(function(){
		$("#upload").click(function(){
			$("#file").click();
		});
	});
	
	$(document).ready(function(){
		$("#decide").click(function(){
			$("#file_submit").click();
		});
	});
	
	$(document).ready(function(){
		$("#file").change(function(){
			$("#fileName").show();
			$("#decide").show();
			$("#fileName")[0].value = $("#file")[0].value;
		});
	});
</script>
</body>
</html>
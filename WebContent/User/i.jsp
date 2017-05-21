<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/Normal/bootstrap_cong.jsp" %>
<script src="<%=request.getContextPath() +"/jquery.js" %>"></script>
<%@ include file="/Normal/highChart.jsp" %>

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
	
	div.case_none {
		width:100%; height:80%; overflow:auto; text-align:center;
		padding:3% 8% 0 8%;
	}
	
	#myanalyse {
		position:absolute; top:10%; width:100%; height:80%;
	}
	div.myanalyse_choose {
		position:absolute; width:90%; height:20%; top:10%;
	}
	div.myanalyse_chart1 {
		position:absolute; top:20%; width:45%; height:80%;
		border-style:dotted;border-width:=2px;border-color:#DCDCDC;
	}
	div.myanalyse_chart2 {
		position:absolute; top:20%; width:45%; height:80%; left:50%;
		border-style:dotted;border-width:2px;border-color:#DCDCDC;
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
		<li><a href="#myanalyse" data-toggle="tab">案例解析</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade" id="basic">
			<div class="basic_mes">
				<div class="basic_detail">用户名</div>
				<div class="basic_value"><s:property value="user.username"></s:property></div>
			</div>
			<div class="basic_mes">
				<div class="basic_detail">姓名</div>
				<div class="basic_value"><s:property value="user.name"></s:property></div>
			</div>
		</div>
		<div class="tab-pane fade in active" id="mycase">
			<s:if test="result=='true'">
			<div class="mycase_list">
				<table class="table table-striped">
					<tr>
						<td><input type="checkbox" id="all"></td>
						<td style="width:80%">案例标题</td>
						<td>上传时间</td>
					</tr>
					<s:iterator value="caselist">
						<tr>
							<td><input type="checkbox" class="case_checkbox" id="<s:property value="id"></s:property>" ></td>
							<td id="<s:property value="id"></s:property>" class="case_title"><s:property value="title"></s:property></td>
							<td><s:property value="uploadDate"></s:property></td>
						</tr>
					</s:iterator>
					<tr>
						<td>
							<button type="button" id="upload" class="btn btn-default btn-sm" style="height:30px">
         					 	<span class="glyphicon glyphicon-plus"></span> 添加
        					</button>
        					<s:form namespace="/Case" action="user_uploadcase" 
              					enctype="multipart/form-data" method="post">
        						<input type="file" name="file1" id="file" accept="text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document"  style="display:none"/>
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
				<input id="removeChecked" type="button" class="btn btn-default" value="删除所选" />
			</div>
			</s:if>
			<s:else>
			<div class="case_none">
				<p style="font-size:30px">您没有上传并保存的文书</p>
				<table class="table">
					<tr>
						<td>
							<button type="button" id="upload" class="btn btn-default btn-sm" style="height:30px">
         					 	<span class="glyphicon glyphicon-plus"></span> 上传一篇
        					</button>
        					<s:form namespace="/Case" action="user_uploadcase" 
              					enctype="multipart/form-data" method="post">
        						<input type="file" name="file1" id="file" accept="text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" style="display:none"/>
        						<input type="submit" id="file_submit" style="display:none" />
        					</s:form> 
        				</td>
						<td>
							<input type="text" id="fileName" disabled="disable" 
								class="form-control" style="height:30px" />
						</td>
						<td>
							<button type="button" id="decide" class="btn btn-default btn-sm" style="height:30px;display:none">
         					 	 上传
        					</button>
        				</td>
					</tr>
				</table>
			</div>
			</s:else>
		</div>
		<div class="tab-pane fade" id="myanalyse">
			<div class="myanalyse_choose">
				个人案例选择：
				<s:select list="caselist" listKey="id" listValue="id" id="caselist_choose" onchange="choosecase(this.value)"
					headerKey="0" headerValue="--请选择--"></s:select>
			</div>
			<div class="myanalyse_chart1" id="chart_1">
				
			</div>
			<div class="myanalyse_chart2" id="chart_2"></div>
		</div>
	</div>
</div>
	
<script>
	function choosecase(value){
		alert(value);
	}
	
	$(document).ready(function(){
		$("#upload").click(function(){
			$("#file").click();
		});
		
		$("#decide").click(function(){
			$("#file_submit").click();
		});
		
		$("#file").change(function(){
			$("#fileName").show();
			$("#decide").show();
			$("#fileName")[0].value = $("#file")[0].value;
		});
		
		$(".case_title").click(function(){
			var id = $(this).attr("id");
			window.open("<%=request.getContextPath() +"/Case/case_detail?id=" %>"+id);
		});
		
		$("#all").click(function(){
			if($(this).attr("checked")){
				$(".case_checkbox").each(function(){
					$(this).attr("checked",'true');     
				});
			}else{
				$(".case_checkbox").each(function(){   
					$(this).removeAttr("checked");   
				});
			}
		});
		
		$("#removeChecked").click(function(){
			var chk_value = new Array();
			$('input[class="case_checkbox"]:checked').each(function(){
				chk_value.push($(this).attr("id"));
			});
			alert(chk_value);
			window.location.href = "<%=request.getContextPath()+"/Case/case_remove?id_list="%>"+chk_value;
		});

			$('#chart_1').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '个人案例相似度对比'
		        },
		        xAxis: {
		            categories: [
		                '一月',
		                '二月',
		                '三月',
		                '四月',
		                '五月',
		                '六月',
		                '七月',
		                '八月',
		                '九月',
		                '十月',
		                '十一月',
		                '十二月'
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '相似度'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		            '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: [{
		            name: '案件名称',
		            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
		        }]
		    });
	});
</script>
</body>
</html>
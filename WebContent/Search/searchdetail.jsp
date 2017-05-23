<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/Normal/bootstrap_cong.jsp" %>
<script src="<%=request.getContextPath() +"/jquery.js" %>"></script>
<%@ include file="/Normal/highChart.jsp" %>
<style type="text/css">
	div.mycontext {
		position:absolute; top:7%; width:100%; height:auto;
	}
	div.mytab {
		position:absolute; left:15%;height:50px;
	}
	div.myanalyse_main {
		position:absolute; left:15%; width:70%; height:400px; top:50px;
		text-align:center; padding:0 4% 0 4%; background-color:#FFFFFF;
		border-style:solid;border-width:1px 1px 1px 1px;border-color:#DCDCDC;
	}
	div.myanalyse_chart {
		position:absolute; left:2%; width:40%; height:90%; top:5%;
		border-style:solid;border-width:1px 1px 1px 1px;border-color:#DCDCDC;
	}
	div.myanalyselike_chart {
		position:absolute; left:10%; width:80%; height:90%; top:5%;
	}
	div.myanalyse_table {
		position:absolute; left:45%; width:52%; height:90%; top:5%;
		border-style:solid;border-width:1px 1px 1px 1px;border-color:#DCDCDC;
		padding:5px 10px 10px 10px;
	}
	div.mycontext_title {
		position:absolute; left:15%; width:54%; height:150px; top:50px;
		text-align:center; padding:0 4% 0 4%; background-color:#FFFFFF;
		border-style:solid;border-width:1px 1px 0 1px;border-color:#DCDCDC;
	}
	div.mycontext_main {
		position:absolute; top:200px; left:15%; width:54%; height:auto;
		padding:2% 4% 2% 4%; background-color:#FFFFFF;
		border-style:solid;border-width:1px 1px 1px 1px;border-color:#DCDCDC;
	}
	
	div.mycontext_relation {
		position:absolute; left:70%; width:15%; height:auto; top:50px;
		border-style:solid;border-width:1px;border-color:#DCDCDC;
		background-color:#FFFFFF;
	}
	div.mycontext_outline {
		width:100%; min-height:100px;
		padding:5px 10px 10px 10px;
	}
	div.mycontext_law {
		width:100%; height:auto;
		padding:15px 10px 0 10px;
	}
	
	h5.h5_title {
		font-size:15px; padding-bottom:10px;
		border-style:solid;border-width:0 0 2px 0;border-color:#DCDCDC;
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
		padding:15px 10px 0 10px;
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
	<div class="mytab">
	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#mydetail" data-toggle="tab">案例详情</a></li>
		<li><a href="#myanalyse" data-toggle="tab">判决解析</a></li>
		<li><a href="#myanalyselike" data-toggle="tab">案发解析</a></li>
	</ul>
	</div>
	<div id="myTabContent" class="tab-content">

<div class="tab-pane fade in active" id="mydetail">
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
		<h5 class="h5_title"><b>基本信息</b></h5>
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
		<h5 class="h5_title"><b>相关法律依据</b></h5>
		<ul class="mycontext_law">
			<s:iterator value="detail.relatedLaw">
				<li class="mycontext_law law" style="color:#000000;cursor:pointer;">
					<s:property value="title"></s:property>
				</li>
			</s:iterator>
		</ul>
	</div>
	<div class="mycontext_recommend">
		<h5 class="h5_title"><b>相似案例</b></h5>
		<ul class="mycontext_law">
			<s:iterator value="detail.relatedCase">
				<li class="mycontext_law recase" id="<s:property value="id"></s:property>" >
					<a style="color:#000000;cursor:pointer;"><s:property value="title"></s:property></a>
				</li>
			</s:iterator>
		</ul>
	</div>
	</div>
</div>
<div class="tab-pane fade" id="myanalyse">
	<div class="myanalyse_main">
		<div class="myanalyse_chart">
			<div id="judge_pie" style="width:400px;height:300px"></div>
		</div>
		<div class="myanalyse_table">
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#always_like" data-toggle="tab">极其相似</a></li>
				<li><a href="#sometimes_like" data-toggle="tab">不完全相似</a></li>
				<li><a href="#seldom_like" data-toggle="tab">不相似</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="always_like" style="width:100%;height:350px; overflow:scroll;">
					<table class="table table-striped">
						<tr>
							<td style="width:65%">文书标题</td>
							<td>共同核心词汇</td>
						</tr>
						<s:iterator value="always_like">
							<tr>
								<td id="<s:property value="id"></s:property>" class="case_title">
									<a><s:property value="title"></s:property></a>
								</td>
								<td><s:property value="same_core"></s:property></td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<div class="tab-pane fade" id="sometimes_like" style="width:100%;height:350px; overflow:scroll;">
					<table class="table table-striped">
						<tr>
							<td style="width:65%">文书标题</td>
							<td>共同核心词汇</td>
						</tr>
						<s:iterator value="sometimes_like">
							<tr>
								<td id="<s:property value="id"></s:property>" class="case_title">
									<a><s:property value="title"></s:property></a>
								</td>
								<td><s:property value="same_core"></s:property></td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<div class="tab-pane fade" id="seldom_like" style="width:100%;height:350px; overflow:scroll;">
					<table class="table table-striped">
						<tr>
							<td style="width:65%">文书标题</td>
							<td>共同核心词汇</td>
						</tr>
						<s:iterator value="seldom_like">
							<tr>
								<td id="<s:property value="id"></s:property>" class="case_title">
									<a><s:property value="title"></s:property></a>
								</td>
								<td><s:property value="same_core"></s:property></td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="tab-pane fade" id="myanalyselike">
	<div class="myanalyse_main">
		<div class="myanalyselike_chart" id="like_chart"></div>
	</div>
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
    
    $(".law").click(function(){
    	var law = $(this).html();
    	location.href= "<%=request.getContextPath() +"/Search/search_normal?input=" %>"+law;
    });
    
    $(".recase").click(function(){
    	var id = $(this).attr("id");
    	window.location.href="<%=request.getContextPath() +"/Case/case_detail?id=" %>"+id;
    });

    $(".case_title").click(function(){
		var id = $(this).attr("id");
		window.open("<%=request.getContextPath() +"/Case/case_detail?id=" %>"+id);
	});

   	$('#judge_pie').highcharts({
		credits: {
	        enabled:false
		},
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false
	    },
	    title: {
	        text: '判决详情占比'
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	              enabled: true,
	                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                style: {
	                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                }
	            }
	        }
	    },
	    series: [{
	        type: 'pie',
	        name: '判决结果占比',
	        data: [['极其相似',<s:property value="always"></s:property>],
	               ['不完全相似',<s:property value="sometimes"></s:property>],
	               ['不相似',<s:property value="seldom"></s:property>]]
	    }]
	});
   	
   	$('#like_chart').highcharts({
   		credits: {
	        enabled:false
		},
   		title: {
   	        text: '不同年份的相似案例数量',
   	    },
   	    xAxis: {
   	        categories: ['2003','2004','2005','2006','2007','2008','2009','2010','2011','2012'
   	                  ,'2013','2014','2015','2016']
   	    },
   	    yAxis: {
   	        title: {
   	            text: '案发数量'
   	        },
   	        plotLines: [{
   	            value: 0,
   	            width: 1,
   	            color: '#808080'
   	        }]
   	    },
   	    legend: {
   	        layout: 'vertical',
   	        align: 'right',
   	        verticalAlign: 'middle',
   	        borderWidth: 0
   	    },
   	    series: [{
   	        name: '相似案例',
   	        data: [ <s:property value="year_data"></s:property> ]
   	    }]
   	});
});
</script>
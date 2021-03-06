<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../jquery.js"></script>
<%@ include file="/Normal/bootstrap_cong.jsp" %>
<%@ include file="/Normal/highChart.jsp" %>

<!--可无视-->
<link rel="stylesheet" type="text/css" href="../Paginator/base.css"/>

<!--主要样式-->
<link rel="stylesheet" type="text/css" href="../Paginator/pageGroup.css"/>
<title>列表页</title>

<style type="text/css">

	div.mytab {
		position:absolute; left:10%; top:370px; width:80%; height:auto;
	}
	
	div.mysearchfilter {
		position:absolute; top:70px; width:20%; height:auto;
		border-style:solid;border-width:2px 0 0 0;border-color:#DCDCDC;
		z-index:-1;
	}
	div.mysearchresult {
		position:absolute; left:27%; top:70px; width:70%; min-height:50%;
		z-index:-1;
	}
	div.mysearchsort {
		float:left; margin-left:1%; width:98%; height:50px;
	}
	div.mysearchres {
		float:left; margin-top:1%; margin-left:1%; width:98%; min-height:80px;
		border-style:solid;border-width:2px 0 0 0;border-color:#DCDCDC;
		padding-left:15px; padding-top:8px;
	}
	div.mysearchpage {
		float:right; width:35%; height:60px;
	}
	
	div.mydetailmes {
		border-style:solid;border-width:2px;border-color:#DCDCDC;
		padding-left:15px; padding-top:10px; padding-bottom:10px;
		font-size:12px; margin-bottom:3px;
		display:none;
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
	
	div.list{
		padding-left:15px; display:none;
	}
	div.main-title {
		float:left;
		width:100%; height:auto; 
		margin-bottom:5px;
	}
	div.title {
		margin-bottom:5px;height:30px; background:#498dde; font-size:18px;
	}
	div.panel-body {
		height:auto; padding:0 0 0 0; margin:0 0 0 0; height:auto; font-size:15px;
	}
	
	div.chart_container{
		float:left;min-width:550px;height:350px;border-style:solid;border-width:0 0 2px 2px;border-color:#DCDCDC;
	}
</style>

</head>
<body>
<div class="mytab">

	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#mycase" data-toggle="tab">检查结果</a></li>
		<li><a href="#myanalyse" data-toggle="tab">统计分析</a></li>
	</ul>
	
	<div id="myTabContent" class="tab-content">
	
<div class="tab-pane fade in active" id="mycase">
<div class="mysearchfilter">
	<%@ include file="searchfilter.jsp" %>
</div>
<div class="mysearchresult">
	<div class="mysearchsort">
	<button type="button" id="sort1" class="btn btn-default">法院层级&#8593</button>
	<button type="button" id="sort2" class="btn btn-default">裁判日期&#8595</button>
	</div>
	<div id="resultlist">
	<s:iterator value="#session.pageData">
		<div class="mysearchres">
			<div class="mymestitle">
				<strong>
					<a id="<s:property value="id"></s:property>" class="case_title" style="cursor:pointer;">
						<s:property value="title"></s:property>
					</a>
				</strong>
			</div>
			<div class="mysource">
				<s:property value="court"></s:property>/<s:property value="date"></s:property>
			</div>
			<div class="mysource">
				<strong>核心词汇：</strong>
					<s:property value="core"></s:property>
				<a href="javascript:void(0)" class="detail">展开</a>
			</div>
			<div class="mydetailmes">
				<table>
					<tr>
						<td>案由：</td>
						<td><s:property value="brief"></s:property></td>
					</tr>
					<tr>
						<td>审理程序：</td>
						<td><s:property value="process_judgement"></s:property></td>
					</tr>
					<tr>
						<td>文书类型：</td>
						<td><s:property value="type_text"></s:property></td>
					</tr>
					<tr>
						<td>来源：</td>
						<td><s:property value="source"></s:property></td>
					</tr>
				</table>
			</div>
		</div>
	</s:iterator>
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
</div>

<div class="tab-pane fade" id="myanalyse">
	<div class="chart_container" id="key_container">
		<div id="chart_key" style="min-width:400px;height:300px"></div>
	</div>
	<div class="chart_container">
		<div id="chart_case" style="min-width:400px;height:300px"></div>
	</div>
	<div class="chart_container">
		<div id="chart_court" style="min-width:400px;height:300px"></div>
	</div>
	<div class="chart_container">
		<div id="chart_year" style="min-width:400px;height:300px"></div>
	</div>
	<div class="chart_container">
		<div id="chart_text" style="min-width:400px;height:300px"></div>
	</div>
</div>

</div>
</div>
<%@ include file="../Normal/topnav.jsp" %>
</body>
</html>

<script>
	$(document).ready(function(){
		$("#resultlist").on("click",".detail",function(){
			$(this).closest(".mysource").next().toggle(1000);
			if($(this).text()=="展开"){
				$(this).text("收起");
			}else{
				$(this).text("展开");
			}
		});

		$("#resultlist").on("click",".case_title",function(){
			var id = $(this).attr("id");
			window.open("<%=request.getContextPath() +"/Case/case_detail?id=" %>"+id);
		});
		
		$("#sort1").click(function(){
			var sort = $(this).html();
			if(sort=="法院层级↑"){
				$(this).html("法院层级↓");
				sort_choose(1);
			}else{
				$(this).html("法院层级↑");
				sort_choose(2);
			}
		});
		
		$("#sort2").click(function(){
			var sort = $(this).html();
			if(sort=="裁判日期↓"){
				$(this).html("裁判日期↑");
				sort_choose(3);
			}else{
				$(this).html("裁判日期↓");
				sort_choose(4);
			}
		});
		
		
		$(function(){
			//根据总页数判断，如果小于5页，则显示所有页数，如果大于5页，则显示5页。根据当前点击的页数生成
			
			var pageCount = <s:property value="#session.maxPage"></s:property>;//模拟后台总页数
			//生成分页按钮
			if(pageCount>5){
				page_icon(1,5,0);
			}else{
				page_icon(1,pageCount,0);
			}
			
			//点击分页按钮触发
			$("#pageGro li").live("click",function(){
				if(pageCount > 5){
					var pageNum = parseInt($(this).html());//获取当前页数
					pageGroup(pageNum,pageCount);
				}else{
					$(this).addClass("on");
					$(this).siblings("li").removeClass("on");
				}
				
				var pageNum = $(this).html();
				page_choose(pageNum);
			});
			
			//点击上一页触发
			$("#pageGro .pageUp").click(function(){
				if(pageCount > 5){
					var pageNum = parseInt($("#pageGro li.on").html());//获取当前页
					pageUp(pageNum,pageCount);
				}else{
					var index = $("#pageGro ul li.on").index();//获取当前页
					if(index > 0){
						$("#pageGro li").removeClass("on");//清除所有选中
						$("#pageGro ul li").eq(index-1).addClass("on");//选中上一页
					}
				}
				
				var pageNum = parseInt($("#pageGro li.on").html());
				page_choose(pageNum);
			});
			
			//点击下一页触发
			$("#pageGro .pageDown").click(function(){
				if(pageCount > 5){
					var pageNum = parseInt($("#pageGro li.on").html());//获取当前页
					pageDown(pageNum,pageCount);
				}else{
					var index = $("#pageGro ul li.on").index();//获取当前页
					if(index+1 < pageCount){
						$("#pageGro li").removeClass("on");//清除所有选中
						$("#pageGro ul li").eq(index+1).addClass("on");//选中上一页
					}
				}
				
				var pageNum = parseInt($("#pageGro li.on").html());
				page_choose(pageNum);
			});
		});

		//点击跳转页面
		function pageGroup(pageNum,pageCount){
			switch(pageNum){
				case 1:
					page_icon(1,5,0);
				break;
				case 2:
					page_icon(1,5,1);
				break;
				case pageCount-1:
					page_icon(pageCount-4,pageCount,3);
				break;
				case pageCount:
					page_icon(pageCount-4,pageCount,4);
				break;
				default:
					page_icon(pageNum-2,pageNum+2,2);
				break;
			}
		}

		//根据当前选中页生成页面点击按钮
		function page_icon(page,count,eq){
			var ul_html = "";
			for(var i=page; i<=count; i++){
				ul_html += "<li>"+i+"</li>";
			}
			$("#pageGro ul").html(ul_html);
			$("#pageGro ul li").eq(eq).addClass("on");
		}

		//上一页
		function pageUp(pageNum,pageCount){
			switch(pageNum){
				case 1:
				break;
				case 2:
					page_icon(1,5,0);
				break;
				case pageCount-1:
					page_icon(pageCount-4,pageCount,2);
				break;
				case pageCount:
					page_icon(pageCount-4,pageCount,3);
				break;
				default:
					page_icon(pageNum-2,pageNum+2,1);
				break;
			}

		}

		//下一页
		function pageDown(pageNum,pageCount){
			switch(pageNum){
				case 1:
					page_icon(1,5,1);
				break;
				case 2:
					page_icon(1,5,2);
				break;
				case pageCount-1:
					page_icon(pageCount-4,pageCount,4);
				break;
				case pageCount:
				break;
				default:
					page_icon(pageNum-2,pageNum+2,3);
				break;
			}
		}	
		

//依据获取的信息内容调整当前页面的结果集

		function sort_choose(sort){
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath() +"/CaseManage/case_sort" %>",
				data:{"sortType":sort},
				dataType:"json",
				success:function(data){
					show_case(data);
				},
				error:function(){
					alert("网络异常，请稍后再试");
				}
			});
			
			var pageCount = <s:property value="#session.maxPage"></s:property>;
			if(pageCount>5){
				page_icon(1,5,0);
			}else{
				page_icon(1,pageCount,0);
			}
		}

		function page_choose(pageNum){
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath() +"/CaseManage/case_page" %>",
				data:{"pageNum":pageNum},
				dataType:"json",
				success:function(data){
					show_case(data);
				},
				error:function(){
					alert("网络异常，请稍后再试");
				}
			});
		}
		
		function show_case(data){
			var obj = eval('(' + data + ')');
			$("#resultlist").html("");
			$.each(obj,function(i, value){
				var context = "<div class='mysearchres'><div class='mymestitle'><strong><a id="+value.id+" class='case_title'>";
				context = context + value.title + "</a></strong></div><div class='mysource'>";
				context = context + value.court + "/"+ value.date +"</div><div class='mysource'><strong>核心词汇："+value.core+"</strong>";
				context = context + "<a href='javascript:void(0)' class='detail'>展开</a></div><div class='mydetailmes'><table><tr>";
				context = context + "<td>案由：</td><td>"+value.brief+"</td></tr><tr><td>审理程序：</td><td>"+value.process_judgement+"</td></tr>";
				context = context + "<tr><td>文书类型：</td><td>"+value.type_text+"</td></tr><tr><td>来源：</td><td>"+value.source+"</td></tr></table></div></div>";
				$("#resultlist").append(context);
			});
			$("#resultlist").append("");
		}

		$('#chart_key').highcharts({
			credits: {
	             enabled:false
			},
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '关键字占比'
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
	            name: '关键字信息占比',
	            data: <s:property value='#session.key_data'></s:property>
	        }]
	    });

		$('#chart_case').highcharts({
			credits: {
	             enabled:false
			},
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '案由占比'
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
	            name: '案由信息占比',
	            data: <s:property value='#session.case_data'></s:property>
	        }]
	    });

		$('#chart_court').highcharts({
			credits: {
	             enabled:false
			},
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '法院层级占比'
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
	            name: '法院层级信息占比',
	            data: <s:property value='#session.court_data'></s:property>
	        }]
	    });

		$('#chart_year').highcharts({
			credits: {
	             enabled:false
			},
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '判决时间占比'
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
	            name: '判决时间信息占比',
	            data: <s:property value='#session.year_data'></s:property>
	        }]
	    });

		$('#chart_text').highcharts({
			credits: {
	             enabled:false
			},
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '文本占比'
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
	            name: '文本信息占比',
	            data: <s:property value='#session.text_data'></s:property>
	        }]
	    });
		
	});

</script>

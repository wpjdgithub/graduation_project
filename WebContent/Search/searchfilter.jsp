<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="main-title" id="1/">
	<div class="title">
		<a id="7" style="color:#FFF;z-index:-1">
           <span class="glyphicon glyphicon-chevron-right right" style="color:#FFF;"></span>
        </a>
        <a style="color:#FFF;">按关键字筛选</a>
    </div>
    <div id="1-l" class="list">
    </div>
</div>

<script>
	$(document).ready(function(){
		$(".main-title").on("click",".glyphicon-chevron-right",function(){
			var element = $(this);
			element.toggleClass("glyphicon-chevron-down");
			element.toggleClass("glyphicon-chevron-right");
			var pathid = element.parent().attr("id");
			$.ajax({
				type:'post',
				url:"<%=request.getContextPath() +"/CaseManage/case_filter" %>",
				data:{"filterId":pathid},
				dataType:"json",
				success:function(data){
					filter_show(data,element);
				},
				error:function(){
					alert("网络异常，请稍后再试");
				}
			});
		});
		
		$(".main-title").on("click",".glyphicon-chevron-down",function(){
			$(this).toggleClass("glyphicon-chevron-down");
			$(this).toggleClass("glyphicon-chevron-right");
			$(this).parent().parent().next().hide();
		});
		
		$(".main-title").on("click",".glyphicon-arrow-right",function(){
			var input = $(this).parent().attr("id");
			location.href= "<%=request.getContextPath() +"/Search/search_normal?input=" %>"+input;
		});
		
	});

	function filter_show(data, element){
		var obj = eval('(' + data + ')');
		var id = element.parent().attr("id");
		element.parent().parent().next().html("");
		$.each(obj,function(i,value){
			if(value.hasChild==false){
				var context = "<div class='panel-body' id='"+value.id+"'><span class='glyphicon glyphicon-arrow-right'></span>"+value.name+"&nbsp("+value.num+")"+"</div>";
				element.parent().parent().next().append(context);
			}else{
				var context = "<div class='panel-body' id='"+value.id+"'>";
				context = context + "<h4 class='title-1'><a id='"+value.id+"'>";
				context = context + "<span class='glyphicon glyphicon-chevron-right right'></span>";
				context = context + value.name+"&nbsp&nbsp&nbsp&nbsp("+value.num+")";
				context = context + " </a></h4><div id='"+value.id+"-l' class='list'></div></div>";
				element.parent().parent().next().append(context);
			}
		});
		element.parent().parent().next().show();
	}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main-title" id="0/">
	<div class="title">
		<a id="0" style="color:#FFF;z-index:-1">
           <span class="glyphicon glyphicon-chevron-right right" style="color:#FFF;"></span>
        </a>
        <a style="color:#FFF;">按关键字筛选</a>
    </div>
    <div id="0-l" class="list">
    	<s:iterator value="l1">
    		<div class='panel-body' id="<s:property value="id"></s:property>">
    			<span class='glyphicon glyphicon-arrow-right'></span>
    			<a class='path'><s:property value="name"></s:property>(<s:property value="num"></s:property>)</a>
    		</div>
    	</s:iterator>
    </div>
</div>
<div class="main-title" id="1/">
	<div class="title">
		<a id="1" style="color:#FFF;z-index:-1">
           <span class="glyphicon glyphicon-chevron-right right" style="color:#FFF;"></span>
        </a>
        <a style="color:#FFF;">按案由筛选</a>
    </div>
    <div id="1-l" class="list">
    	<s:iterator value="l2">
    		<div class='panel-body' id="<s:property value="id"></s:property>">
    			<span class='glyphicon glyphicon-arrow-right'></span>
    			<a class='path'><s:property value="name"></s:property>(<s:property value="num"></s:property>)</a>
    		</div>
    	</s:iterator>
    </div>
</div>
<div class="main-title" id="2/">
	<div class="title">
		<a id="2" style="color:#FFF;z-index:-1">
           <span class="glyphicon glyphicon-chevron-right right" style="color:#FFF;"></span>
        </a>
        <a style="color:#FFF;">按法院层级筛选</a>
    </div>
    <div id="2-l" class="list">
    	<s:iterator value="l3">
    		<div class='panel-body' id="<s:property value="id"></s:property>">
    			<span class='glyphicon glyphicon-arrow-right'></span>
    			<a class='path'><s:property value="name"></s:property>(<s:property value="num"></s:property>)</a>
    		</div>
    	</s:iterator>
    </div>
</div>
<div class="main-title" id="3/">
	<div class="title">
		<a id="3" style="color:#FFF;z-index:-1">
           <span class="glyphicon glyphicon-chevron-right right" style="color:#FFF;"></span>
        </a>
        <a style="color:#FFF;">按年份筛选</a>
    </div>
    <div id="3-l" class="list">
    	<s:iterator value="l4">
    		<div class='panel-body' id="<s:property value="id"></s:property>">
    			<span class='glyphicon glyphicon-arrow-right'></span>
    			<a class='path'><s:property value="name"></s:property>(<s:property value="num"></s:property>)</a>
    		</div>
    	</s:iterator>
    </div>
</div>
<div class="main-title" id="4/">
	<div class="title">
		<a id="4" style="color:#FFF;z-index:-1">
           <span class="glyphicon glyphicon-chevron-right right" style="color:#FFF;"></span>
        </a>
        <a style="color:#FFF;">按文书类型筛选</a>
    </div>
    <div id="4-l" class="list">
    	<s:iterator value="l5">
    		<div class='panel-body' id="<s:property value="id"></s:property>">
    			<span class='glyphicon glyphicon-arrow-right'></span>
    			<a class='path'><s:property value="name"></s:property>(<s:property value="num"></s:property>)</a>
    		</div>
    	</s:iterator>
    </div>
</div>

<script>
	$(document).ready(function(){
		$(".main-title").on("click",".glyphicon-chevron-right",function(){
			var element = $(this);
			element.toggleClass("glyphicon-chevron-down");
			element.toggleClass("glyphicon-chevron-right");
			$(this).parent().parent().next().show();
		});
		
		$(".main-title").on("click",".glyphicon-chevron-down",function(){
			$(this).toggleClass("glyphicon-chevron-down");
			$(this).toggleClass("glyphicon-chevron-right");
			$(this).parent().parent().next().hide();
		});
		
		$(".main-title").on("click",".path",function(){
			var input = $(this).parent().attr("id");
			location.href= "<%=request.getContextPath() +"/Search/search_filter?input=" %>"+input;
		});
		
	});

</script>

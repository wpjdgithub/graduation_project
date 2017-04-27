<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
	div.mytopnav {
		position:absolute; width:100%; text-align:right;
		z-index:-1;
	}
	div.mymes{
		position:absolute; left:85%;top:5px;
	}
	div.user_opt{
		position:absolute; left:85%;top:30px; display:none; width:auto; height:auto;
		border-style:solid;border-width:1px;border-color:#DCDCDC;
		padding:3px 2px 3px 2px;
	}
	div.mysearch {
		position:absolute; width:100%; height:auto; top:150px; text-align:center;
	}
	div.mysearchkind {
		position:absolute; width:100%; height:auto; top:50px;
		font-weight:bold; font-size:20px;
		text-align:center;
	}
	div.mysearchdetail {
		position:absolute; width:100%; height:auto; top:150px;
		padding-left:300px; padding-right:300px;
		font-weight:bold; font-size:20px;
	}
	div.mysearch1 {
		float:left; width:88%;
	}
	div.mysubmit {
		float:left; width:12%;
	}
	div.mysearchtable {
		float:left; width:70%; height:auto;
		font-size:15px;
		border-style:solid;border-width:1px;border-color:#DCDCDC;
		display:none; z-index:9999;
		background:white;
	}
	td.left {text-align:left;}
	td.right {text-align:right;width:100px;}
	input.mysearchinput {
		width:170px; height:25px;
	}
	select.mysearchselect {
		width:170px; height:25px; font-size:10px;
	}
</style>

</head>
<body>
<div class="mytopnav">
	<div class="mymes">
		<s:if test="#session.username==null">
			<a href="<%=request.getContextPath() +"/User/login.jsp" %>">登录</a>&nbsp&nbsp
			<a href="<%=request.getContextPath() +"/User/register.jsp" %>">注册</a>&nbsp&nbsp
		</s:if>
		<s:else>
			<a href="<%=request.getContextPath() +"/User/user_mes" %>" id="user_name">
				<s:property value="#session.username"></s:property>
			</a>&nbsp&nbsp
		</s:else>
		<a href="<%=request.getContextPath() +"/index.jsp" %>">返回主页</a>
	</div>
	
	<div class="mysearch">
		<div class="mysearchkind">
			<a>首页</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<a>刑事案件1</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<a>民事案件</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<a>行政案件</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<a>赔偿案件</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<a>执行案件</a>
		</div>
		<div class="mysearchdetail">
			<div class="mysearch1">
				<div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" id="detailbutton">高级检索</button>
                    </span>
                    <input id="text_input" type="text" class="form-control" >
                </div>
            </div>
			<div class="mysubmit">
				<button type="button" class="btn btn-default" style="width:100%" id="do_search">搜索</button>
			</div>
			<div class="mysearchtable" id="detailtable">
				<%@ include file="/Normal/searchtable.html" %>
			</div>
		</div>
	</div>
</div>

<div class="user_opt">
	<a>注销账户</a>
</div>

<script type="text/javascript">
$(document).ready(function(){
	$("#detailbutton").click(function(){
		$("#detailtable").toggle();
	});
	
	$("#do_search").click(function(){
		var input = $("#text_input").val();
		location.href= "<%=request.getContextPath() +"/Search/search_normal?input=" %>"+input;
	});

	$("#user_name").hover(function(){
		$("div.user_opt").show();
	},function(){
		
	});

	$("div").click(function(){
		if($(this).hasClass("user_opt")){
			$("div.user_opt").show();
		}else{
			$("div.user_opt").hide();
		}
	});

	$("div.user_opt").hover(function(){
		$(this).css("background", "#005AB5");
		$(this).find("a").css("color","#FFFFFF");
	},function(){
		$(this).css("background", "#FFFFFF");
		$(this).find("a").css("color","#005AB5");
	});

	$("div.user_opt").click(function(){
		$.ajax({
			type:'post',
			url:"<%=request.getContextPath() +"/User/user_logout" %>",
			success:function(data){
				window.location.reload();
			},
			error:function(){
				alert("网络异常，请稍后再试");
			}
		});
	});
	
	$("body").keypress(function (e) {
		var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
		if (keyCode == 13){
			$("#do_search").click();
		}
	}); 
});


</script>
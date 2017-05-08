<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
	div.mytopnav {
		position:absolute; width:100%; text-align:right;
	}
	div.mymes{
		position:absolute; left:85%;top:5px;
	}
	div.user_opt{
		position:absolute; left:85%;top:30px; display:none; 
		border-style:solid;border-width:1px;border-color:#DCDCDC;
	}
</style>
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
		<a href="<%=request.getContextPath() +"/Index/index" %>">返回主页</a>
	</div>
</div>

<div class="user_opt">
	<a>注销账户</a>
</div>

<script type="text/javascript">
$(document).ready(function(){


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

	
});


</script>
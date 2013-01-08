<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>网页头部内容</title>
	<style type="text/css">
		/* The CSS Code for the menu starts here */
		#menu {
			font-family: 宋体;
			font-weight: bold;
			text-transform: uppercase;
			margin: 0 0 0 0;
			padding: 0;
			list-style-type: none;
			background-color: #eee;
			font-size: 13px;
			height: 40px;
			border-top: 2px solid #eee;
			border-bottom: 2px solid #ccc;
		}
		#menu li {
			float: left;
			margin: 0;				
		}
		#menu li a {
			text-decoration: none;
			display: block;
			padding: 0 20px;
			line-height: 40px;
			color: #666;
		}
		#menu li a:hover, #menu li.active a {
			background-color: #f5f5f5;
			border-bottom: 2px solid #DDD;
			color: #999;
		}
		#menu_wrapper ul {margin-left: 12px;}
		#menu_wrapper {padding: 0 16px 0 0; background: url(./images/grey.png) no-repeat right;}
		#menu_wrapper div {float: left; height: 44px; width: 12px; background: url(./images/grey.png) no-repeat left;}
	</style>
</head>
<body>
	<!-- 灰色导航条 -->
		<div id="menu_wrapper" class="grey">
			<div class="left"></div>
			<ul id="menu">
				<li class="active"><a href="<c:url value='/'/>">首页</a></li>
				<li><a href="<c:url value='/game'/>">html5游戏</a></li>
				<li><a href="<c:url value='/navigation'/>">实用导航</a></li>
				<li><a href="<c:url value='/picture'/>">精美图片</a></li>
				<li><a href="<c:url value='/leaveWord'/>">在线留言</a></li>
			</ul>
		</div>
</body>
</html>
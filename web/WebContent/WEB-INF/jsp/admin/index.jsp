<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache"> 
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="expires" content="0">   
	<title>网站后台管理</title>
	
	<style type="text/css">
		#leftCnt {
			float: left;
			padding: 0px;
			margin: 0px;
			background: #FFF;
			border: 5px solid #666;
			width: 200px; 
			height: 98%;
		}
			
		#rightCnt {
			float: left;
			padding: 0px;
			margin: 0;
			margin-left:-5px;
			background: #FFF;
			border: 5px solid #666;
			width: 80%; 
			height: 98%;
		}
    </style>	
   </head>

	<body>
		<div id="main" style="width: 100%;height: 100%;">
        	<div id="leftCnt">
        		<ul><c:url value=""/>
        			<li><a href="#" class="treeI">常规管理</a></li>
        			<li><a href="<c:url value='/link/list'/>" target="mainIframe" class="treeIII">链接</a></li>
        			<li><a href="#" class="treeIII">板块</a></li>
        			<li><a href="<c:url value='/user/list'/>" target="mainIframe" class="treeIII">会员</a></li>
        			<li><a href="#" class="treeI">网站管理</a></li>
        			<li><a href="#" class="treeIII">点击统计</a></li>
        			<li><a href="<c:url value="/file/browse?dir=/logs"/>" target="mainIframe" class="treeIII">系统日志</a></li>
        			<li><a href="<c:url value="/file/browse?dir=/"/>" target="mainIframe" class="treeIII">文件管理</a></li>
        			<li><a href="<c:url value="/iSite/list"/>" target="mainIframe" class="treeIII">我的网址</a></li>
        			<li><a href="<c:url value="/html/catalog.html"/>" target="mainIframe" class="treeIII">功能目录</a></li>
        		</ul>
        	</div>
        	<div id="rightCnt">	
        		<iframe name="mainIframe" src="<c:url value='/admin/default'/>" scrolling="auto" frameborder="0" width="100%" height="100%"></iframe>
       		</div>
    	</div>
	</body>
</html>
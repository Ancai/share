<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>默认页面</title>
</head>
<body>
	<p align="center">欢迎管理员:${adminKey}，今天是：${serverDate }</p>
	<h3 align="center">在线人数：${onLineUsers }</h3>
	
</body>
</html>
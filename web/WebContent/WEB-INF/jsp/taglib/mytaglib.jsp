<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cc" uri="/mytaglib"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>自定义标签</title>
</head>
<body>
	<H3>用户的信息</H3>
	<cc:showUserInfo user="${user}"></cc:showUserInfo>
	
	<H3>遍历用户的信息</H3>
	<table border="1">
		<tr>
			<td>编号</td>
			<td>用户名</td>
			<td>密码</td>
			<td>昵称</td>
			<td>出生日期</td>
			<td>性别</td>
			<td>邮箱</td>
			<td>状态</td>
			<td>注册时间</td>
		</tr>
	<cc:repeater var="user" items="${users}">
		<tr>
			<td>${user.id }</td>
			<td>${user.username }</td>
			<td>${user.password }</td>
			<td>${user.nicename }</td>
			<td>${user.birthday }</td>
			<td>${user.sex }</td>
			<td>${user.email }</td>
			<td>${user.status }</td>
			<td>${user.registDt }</td>
		</tr>
	</cc:repeater>
	</table>
</body>
</html>

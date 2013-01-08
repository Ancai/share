<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ISite: EDIT</title>
</head>
<body>
	<div style="text-align: center">
		<h3>添加ISite</h3>
		<form action="/Share/user/save" method="post">
			<label>名称：</label><input type="text" name="name"><br>
			<label>网址：</label><input type="text" name="url"><br>
			<label>分类：</label><input type="text" name="urlType"><br>
			<label>注册日期：</label><input type="text"  name="nicename" disabled="disabled"><br>
			<label>用户编号：</label><input type="text"  name="userId"><br>			        
			<input type="submit" value="提交"><input type="reset" value="重置">
		</form>
	</div>
</body>
</html>

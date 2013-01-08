<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
</head>
<body>
	<div style="text-align: center">
		<h3>管理员登录:<f:formatDate value="${serverDate }" pattern="yyyy-MM-dd HH:mm:ss"/></h3>
		<form action="<c:url value='/admin/checkLogin'/>"  method="post">
			帐     户：<input type="text" name="adminName"><br>
			密     码：<input type="password" name="adminPass"><br>
			<input type="submit" value="登录"><input type="button" value="注册">
		</form>
		
	</div>
</body>
</html>
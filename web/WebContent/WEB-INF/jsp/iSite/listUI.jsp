<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ISite: LIST</title>
</head>
<body>
	<table border="1" class="datagrid">
		<tr>
			<th>编号</th>
			<th>名称</th>
			<th>网址</th>
			<th>注册时间</th>
			<th>分类</th>
			<th>用户</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pageData.list }" var="iSite" varStatus="status">
			<tr>
				<td>${iSite.id }</td>
				<td>${iSite.name}</td>
				<td>${iSite.url }</td>
				<td>${iSite.updated}</td>
				<td>${iSite.urlType }</td>
				<td>${iSite.userId }</td>
				<td>
					<a href="<c:url value='/iSite/edit/${iSite.id }'/>">修改</a> </td>
			</tr>
		</c:forEach>
	</table>
	<!-- 分页代码 -->
	${ pageData.label }
</body>
</html>

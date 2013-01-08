<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>链接列表</title>
</head>
<body>
		<p><a href="<c:url value='/link/add'/>">新增</a></p>
	<table border="1" class="datagrid">
		<tr>
			<th>编号</th>
			<th>名称</th>
			<th>地址</th>
			<th>图片路径</th>
			<th>描述</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pageData.list }" var="link" varStatus="status">
			<tr>
				<td>${link.id }</td>
				<td>${link.name}</td>
				<td>${link.url }</td>
				<td>${link.image }</td>
				<td>${link.description}</td>
				<td><a href="<c:url value='/link/del?id=${link.id}'/>">删除</a>，
					<a href="<c:url value='/link/edit/${link.id }'/>">修改</a> </td>
			</tr>
		</c:forEach>
	</table>
	<!-- 分页代码 -->
	${ pageData.label }
</body>
</html>
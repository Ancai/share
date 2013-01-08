<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
</head>
<body>
		<p><a href="<c:url value='/user/add'/>">新增</a></p>
	<table border="1" class="datagrid">
		<tr>
			<th>编号</th>
			<th>用户名</th>
			<th>邮箱</th>
			<th>密码</th>
			<th>注册时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pageData.list }" var="user" varStatus="status">
			<tr>
				<td>${user.id }</td>
				<td>${user.username}</td>
				<td>${user.email }</td>
				<td>XXXXXXXXX</td>
				<td>${user.registDt}</td>
				<td><a href="<c:url value='/user/del?id=${user.id}'/>" onclick="return confirm('你确定要删除吗？')">删除</a>，
					<a href="<c:url value='/user/edit/${user.id }'/>">修改</a> </td>
			</tr>
		</c:forEach>
	</table>
	<!-- 分页代码 -->
	${ pageData.label }
</body>
</html>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统日志</title>
<script type="text/javascript">
$(function() {
	
});
</script>
</head>
<body>
	<form id="fileForm" action="<c:url value='/file/upload'/>" method="post" enctype="multipart/form-data">
		上传目录：<input type="text" name="upDir"></input>
		<input type="file" name="file" ></input>
		<input type="submit" value="上传"><input type="reset" value="重置">
	</form>
	<table border="1" class="datagrid">
		<tr id="head">
			<th>名称</th>
			<th>大小(KB)</th>
			<th>类型</th>
			<th>日期</th>
			<th>操作</th>
		</tr>
		<tr><td colspan="5"><a href="javascript:window.history.go(-1)" style="width: 100%;">.. .. ..</a></td></tr>
		<c:forEach items="${pageData.list }" var="file" varStatus="status">
		<tr>
			<td>${file.name}</td>
			<td>${file.size }</td>
			<c:if test="${file.fileType == 'FILE'}">	
			<td>文件</td>
			<td><f:formatDate value="${file.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><a href="<c:url value='/file/del?dir=${file.webPath}'/>" onclick="return confirm('确定要删除吗？')">删除</a>，
				<a href="<c:url value='/file/read?dir=${file.webPath}&KeepThis=true&TB_iframe=true&height=500&width=900'/>" class="thickbox" rel="test" title="查看">查看</a>, 
				<a href="<c:url value='/file/down?dir=${file.webPath}'/>"  title="下载">下载</a>
			</td>
			</c:if>
			<c:if test="${file.fileType == 'DIRECTORY'}">	
			<td>目录</td>
			<td><f:formatDate value="${file.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><a href="<c:url value='/file/del?dir=${file.webPath}'/>" onclick="return confirm('确定要删除吗？')">删除</a>，
				<a href="<c:url value='/file/browse?dir=${file.webPath}'/>" title="浏览">浏览</a> 
			</td>
			</c:if>
		</tr>
		</c:forEach>
	</table>
	<!-- 分页代码 -->
	${ pageData.label }	
</body>
</html>
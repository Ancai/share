<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName()+ ":" + request.getServerPort() + path;
	request.setAttribute("basePath", basePath);
%>
<link rel="shortcut icon" href="${basePath}/images/favicon.ico">
<link rel="icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
<link href="<c:url value='/css/common.css'/>" rel="stylesheet" type="text/css" />
<script src="<c:url value='/js/common.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<link href="<c:url value='/js/thickbox/css/thickbox.css'/>" rel="stylesheet" type="text/css" />
<script src="<c:url value='/js/thickbox/javascript/thickbox.js'/>" type="text/javascript"></script>
	
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户：添加</title>
<script type="text/javascript" src="<c:url value='/js/My97DatePicker/WdatePicker.js'/>"></script>
</head>
<body>
	<div style="text-align: center">
		<h3>添加用户</h3>
		<form action="/Share/user/save" method="post">
			用户名：<input type="text" name="username"><br>
			密     码：<input type="password" name="password"><br>
			确认密码：<input type="password" name="password2"><br>
			昵     称：<input type="text"  name="nicename"><br>
			性     别：男：<input type="radio" name="sex" value="1">&nbsp;&nbsp;
				         女：<input type="radio" name="sex" value="0"><br>
			出生日期：<input type="text" class="Wdate" onClick="WdatePicker()" name="birthday"><br>
			邮     箱：	 <input type="text" name="email"><br>        
			<input type="submit" value="提交"><input type="reset" value="重置">
		</form>
	</div>
</body>
</html>
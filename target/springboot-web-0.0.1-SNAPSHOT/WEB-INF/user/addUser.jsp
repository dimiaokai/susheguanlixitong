<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>新增用户</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/addUser.action" method="post">
		<table border="1px" width="25%" align="center">
			<tr align="center">
				<td colspan="2"><h3>用户新增</h3></td>
			</tr>
			<tr align="center">
				<td>姓名:</td>
				<td><input name="name" type="text"/></td>
			</tr >
			<tr align="center">
				<td>年龄:</td>
				<td><input name="age" type="text"/></td>
			</tr>
			<tr align="center">
				<td>性别:</td>
				<td><input name="sex" type="text"/></td>
			</tr>
			<tr align="center">
				<td colspan="2"><input type="submit" value="提交"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
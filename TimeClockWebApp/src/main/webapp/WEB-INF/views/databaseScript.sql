<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Home Page</h3>

	This is Simple Time Clock web application using jsp,servlet &amp; Jdbc.
	<br>
	<br>
	<b>It includes the following functions:</b>
	<ul>
		<li>Login</li>
		<li>Storing employee information in cookies</li>
		<li>Time Clock Entries</li>
		<li>Create Time Clock Entry</li>
		<li>Edit Time Clock Entry</li>
		<li>Delete Time Clock Entry</li>
	</ul>

	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>
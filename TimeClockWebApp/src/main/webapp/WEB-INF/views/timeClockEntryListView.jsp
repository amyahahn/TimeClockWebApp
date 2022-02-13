<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Time Clock Entries</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Time Clock Entries</h3>

	<p style="color: red;">${errorString}</p>

	<table border="1" cellpadding="5" cellspacing="1">
		<tr>
			<th>Code</th>
			<th>Name</th>
			<th>Price</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<c:forEach items="${timeClockEntryList}" var="timeClockEntry">
			<tr>
				<td>${timeClockEntry.code}</td>
				<td>${timeClockEntry.name}</td>
				<td>${timeClockEntry.price}</td>
				<td><a href="editTimeClockEntry?code=${timeClockEntry.code}">Edit</a></td>
				<td><a href="deleteTimeClockEntry?code=${timeClockEntry.code}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>

	<a href="createTimeClockEntry">Create Time Clock Entry</a>

	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>
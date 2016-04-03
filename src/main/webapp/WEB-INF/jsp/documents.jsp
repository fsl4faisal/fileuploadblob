<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h2>Document Manager</h2>

	<h3>Add new document</h3>

	<form:form method="post" action="save.html" commandName="document"
		enctype="multipart/form-data">
		<form:errors path="*" cssClass="error" />
		<table>
			<tr>
				<td><form:label path="name">Name</form:label></td>
				<td><form:input path="name" /></td>
			</tr>

			<tr>
				<td><form:label path="description">Description</form:label></td>
				<td><form:input path="description" /></td>
			</tr>

			<tr>
				<td><form:label path="content">Document</form:label></td>
				<td><input type="file" name="file" id="file" /></td>
			</tr>

			<tr>
				<td><input type="submit" value="Add Document" /></td>
			</tr>
		</table>
	</form:form>

	<br />
	<h3>Document List</h3>

	<c:if test="${!empty documentList}">
		<table class="data">
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${documentList}" var="document">
				<tr>
					<td width="100px">${document.name}</td>
					<td width="100px">${document.description}</td>
					<td width="20px"><img
						src="${pageContext.request.contextPath}/view/${document.id}.html"
						height="100" width="100"><a
						href="${pageContext.request.contextPath}/download/${document.id}.html">Download
							this document</a> <a
						href="${pageContext.request.contextPath}/remove/${document.id}.html">Delete
							this document</a></td>
				</tr>
			</c:forEach>

		</table>


	</c:if>



</body>
</html>
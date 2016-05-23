<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>

<html>
<head>
<title> Effort Tracker </title>
<sb:head/>
</head>

<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<h3 class="text-center text-muted">
				Project Effort Tracker
			</h3>
		</div>
		<div class="col-md-3">
		<h4 class="text-center text-muted">
			  Welcome <%=session.getAttribute("loggedInUser")%>
			<a href="logout" class="btn btn-link btn-default" type="button">Logout</a>
		</h4>
		</div>
	
	</div>
</div>
<hr size=16>
</body>

</html>

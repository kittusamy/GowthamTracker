<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>

<html>
<head>
<sb:head />
</head>
<s:include value="../header.jsp"></s:include>
<body>
	<div class="container-fluid">
		<h4>
			<s:actionmessage theme="bootstrap" class="text-left text-muted" />
			<s:actionerror theme="bootstrap" class="text-left text-muted" />
		</h4>
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h3 class="text-left text-muted">Admin Home Page</h3>
			</div>
		</div>
		<div class="row">
			<br>
			<br>
			<br>
			<div class="col-md-4 col-md-offset-2">

				<a href="viewUser" class="btn btn-block btn-primary" 
				type="button">Add / Update User</a> 
					
				<a href="viewProject" class="btn btn-info btn-block"
					type="button">Add / Update Project</a>

			</div>
		</div>
	</div>
</body>

</html>

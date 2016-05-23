<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>

<html>
<head> <sb:head/>  </head>
<s:include value="HomeHeader.jsp"></s:include>
<body>

<div class="container-fluid">
	<div class="row">
	<br>
	<br>
	<br>
	<br>
		<div class="col-md-4 col-md-offset-6">
		<h5><s:actionerror theme="bootstrap" class="text-left text-muted"/></h5>
		<div class="panel panel-info">
		   <div class = "panel-body">
			<s:form action="authenticate" theme="bootstrap">
				<div class="form-group required">
				<s:textfield label="User name" maxlength="9" name="username"/>
				</div>
				<div class="form-group required">
				<s:password label="Password" maxlength="20" name="password"/>
				</div>			
				<div class="row">
				<div class="col-md-6">
					 <s:submit value="Submit" class="btn btn-success btn-block"/>
				</div>
				 <div class="col-md-6">
					 <s:reset value= "Reset" class="btn btn-warning btn-block"/>
				</div>
				</div>
			</s:form>
			</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>



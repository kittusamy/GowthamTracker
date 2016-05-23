<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head />
<sb:head />
</head>
<s:include value="../header.jsp"></s:include>
<body>

	<div class="container-fluid">
		<h4>
			<s:actionmessage theme="bootstrap" class="text-left text-muted" />
			<s:actionerror theme="bootstrap" class="text-left text-muted" />
		</h4>
		<div class="col-md-6 col-md-offset-2 panel panel-info">
			<div class="panel-heading">
				<h4 class="text-left text-muted">Reset new password</h4>
			</div>
			<div class="panel-body">
				<s:form action="resetPassword" theme="bootstrap">
					<div class="form-group readonly disabled">
						<s:textfield label="User ID / Name" name="userIdName"
							readonly="true" />
					</div>
					<div class="form-group required">
						<s:textfield label="New password" maxlength="20" name="password1" />
					</div>
					<div class="form-group required">
						<s:password label="Re-type new password" maxlength="20"
							name="password2" />
					</div>
					<br>
					<div class="row">
						<div class="col-md-6">
							<s:submit value="Reset Password"
								class="btn btn-success btn-block" />
						</div>
						<div class="col-md-6">
							<s:reset value="Reset" class="btn btn-warning btn-block" />
						</div>
					</div>
				</s:form>
			</div>
		</div>

	</div>
</body>

</html>

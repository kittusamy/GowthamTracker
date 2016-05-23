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
		<div class="col-md-6 col-md-offset-2 panel panel-warning">
			<div class = "panel-heading">
		     <h4 class="text-left text-muted">Edit User</h4>
		   </div>
		   <div class = "panel-body">
				<s:form action="updateUser" theme="bootstrap">
					
					<div class="form-group readonly disabled ">
						<s:textfield label="User id" maxlength="6" name="userid" readonly="true" />
					</div>
					<div class="form-group required">
						<s:textfield label="Full name" name="fullname" maxlength="20" />
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-6">
								<s:select label="Role" list="roleMap" name="role" value="role"
									emptyOption="false" />
							</div>
							<div class="col-md-6">
								<s:radio label="Status" name="status" labelposition="inline"
									list="#{'true':'Enable','false':'Disable'}" value = "status"/>
							</div>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-4">
							<s:submit value="Update" class="btn btn-success btn-block" />
						</div>
						<div class="col-md-4">
							<s:reset value="Reset" class="btn btn-warning btn-block" />
						</div>
						<div class="col-md-4">
							<a href="viewUser" class="btn btn-info btn-block" type="button">Cancel</a>
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</div>
</body>

</html>

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
		<div class="row">
	
			<div class="col-md-6 col-md-offset-1">
				<h3 class="text-left text-muted">Add / Update User</h3>
			</div>

			<div class="col-md-2">
				<a href="addUser" class="btn btn-success btn-sm btn-block"
					type="button">Add New User </a>
			</div>
			<div class="col-md-2">
				<a href="adminHome" class="btn btn-info btn-sm btn-block"
					type="button"> Admin Home </a>
			</div>
		</div>
		<h4>
			<s:actionmessage theme="bootstrap" class="text-left text-muted" />
			<s:actionerror theme="bootstrap" class="text-left text-muted" />
		</h4>
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<table class="table table-condensed ">
					<thead>
						<tr class="active" >
							<th class="text-center text-muted" >#</th>
							<th class="text-center text-muted" >User id</th>
							<th class="text-center text-muted" >Full name</th>
							<th class="text-center text-muted" >Role</th>
							<th class="text-center text-muted" >Actions</th>
						</tr>
					</thead>


					<tbody>
						<s:if test="%{userList.size>0}">
							<s:iterator value="userList" status="rowstatus">
								<tr
									class="<s:if test="#rowstatus.odd == true ">warning</s:if><s:else>info</s:else>">
									<s:set var="isEnabledFlag">
										<s:property value="isEnabled" />
									</s:set>
									<td><s:property value="#rowstatus.count" /></td>
									<td class="text-center"><s:property value="userId" /></td>
									<td class="text-center"><s:property value="fullName" /></td>
									<td class="text-center"><s:property value="role" /></td>
									
									<td>
										<div class="col-md-3">
											<a href="resetUserId/<s:property value="userId"/>"
												class="btn btn-danger btn-xs btn-block" type="button">Reset
												password</a>
										</div>
										<div class="col-md-3">
										<a href="enableUserId/<s:property value="userId"/>"
												class="<s:if test="#isEnabledFlag == 'true' ">btn btn-success btn-xs btn-block disabled</s:if>
										<s:else>btn btn-success btn-xs btn-block</s:else>"
												type="button">Enable</a>
										</div>
										<div class="col-md-3">
										<a href="disableUserId/<s:property value="userId"/>"
												class="<s:if test="#isEnabledFlag == 'true' ">btn btn-warning btn-xs btn-block </s:if>
										<s:else>btn btn-warning btn-xs btn-block disabled</s:else>"
												type="button">Disable</a>
										</div>
										<div class="col-md-3">
											<a href="editUserId/<s:property value="userId"/>"
												class="btn btn-info btn-xs btn-block" type="button">Edit
											</a>
										</div>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td colspan="5">
									<h5 class="text-center text-muted">No data found</h5>
								</td>
							</tr>
						</s:else>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>

</html>

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
			<div class="col-md-8">
				<h2 class="text-left text-muted">Add / Update Project</h2>
			</div>
			<div class="col-md-2">
				<a href="addProject" class="btn btn-success btn-sm btn-block"
					type="button">Add New Project </a>
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
			<div class="col-md-12">
				<table class="table table-hover table-condensed">
					<thead>
						<tr class=active>
							<th class="text-center text-muted">#</th>
							<th class="text-center text-muted">Project name</th>
							<th class="text-center text-muted">Estimated efforts</th>
							<th class="text-center text-muted">Start date</th>
							<th class="text-center text-muted">End date</th>
							<th class="text-center text-muted">Actions</th>
						</tr>
					</thead>


					<tbody>
						<s:if test="%{projectList.size>0}">
							<s:iterator value="projectList" status="rowstatus">
								<tr
									class="<s:if test="#rowstatus.odd == true ">warning</s:if><s:else>info</s:else>">
									<s:set var="isEnabledFlag">
										<s:property value="isEnabled" />
									</s:set>
									<td class="text-center"><s:property value="#rowstatus.count" /></td>
									<td class="text-center"><s:property value="projectNum" /> - <s:property
											value="projectName" /></td>
									<td class="text-center"><s:property value="totalEstEffort" /></td>
									<td class="text-center"><s:date name="startDate" format="E dd/MM/yyyy" /></td>
									<td class="text-center"><s:date name="endDate" format="E dd/MM/yyyy" /></td>
									
									<td>
										<div class="col-md-3">
											<a href="viewProjectId/<s:property value="ProjectId"/>"
												class="btn btn-primary btn-xs btn-block" type="button">View Details
											</a>
										</div>
										<div class="col-md-2">
											<a href="enableProjectId/<s:property value="ProjectId"/>"
												class="<s:if test="#isEnabledFlag == 'true' ">btn btn-success btn-xs btn-block disabled</s:if>
										<s:else>btn btn-success btn-xs btn-block</s:else>"
												type="button">Enable</a>
										</div>
										<div class="col-md-2">
											<a href="disableProjectId/<s:property value="ProjectId"/>"
												class="<s:if test="#isEnabledFlag == 'true' ">btn btn-warning btn-xs btn-block </s:if>
										<s:else>btn btn-warning btn-xs btn-block disabled</s:else>"
												type="button">Disable</a>
										</div>

										<div class="col-md-2">
											<a href="editProjectId/<s:property value="ProjectId"/>"
												class="btn btn-info btn-xs btn-block" type="button">Edit
											</a>
										</div>
										<div class="col-md-3">
											<a href="deleteProjectId/<s:property value="ProjectId"/>"
												class="btn btn-danger btn-xs btn-block" type="button">Delete</a>
										</div>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td colspan="7">
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

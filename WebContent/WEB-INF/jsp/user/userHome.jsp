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
		<div class="col-md-8 panel panel-warning">
			<div class="panel-body">
				<div class="row">
					<div class="col-md-12">
						<table class="table table-condensed">
							<thead>
								<tr class=active>
									<th class="text-center text-muted">#</th>
									<th class="text-center text-muted">Project ID - Name</th>
									<th class="text-center text-muted">Type</th>
									<th class="text-center text-muted">Date</th>
									<th class="text-center text-muted">Efforts</th>
									<th class="text-center text-muted">Actions</th>
								</tr>
							</thead>


							<tbody>
								<s:if test="%{effortsUserList.size>0}">
									<s:iterator value="effortsUserList" status="rowstatus">

										<tr
											class="<s:if test="#rowstatus.odd == true ">warning</s:if><s:else>info</s:else>">
											<s:set var="isEnabledFlag">
												<s:property value="projectVo.isEnabled" />
											</s:set>
											<td class="text-center"><s:property
													value="#rowstatus.count" /></td>
											<td
												class="<s:if test="#isEnabledFlag == 'true' ">text-center</s:if>
										<s:else>text-center text-muted</s:else>"><s:property
													value="projectVo.projectNum" /> - <s:property
													value="projectVo.projectName" /></td>
											<td
												class="<s:if test="#isEnabledFlag == 'true' ">text-center</s:if>
										<s:else>text-center text-muted</s:else>"><s:property
													value="effortType" /></td>
											<td
												class="<s:if test="#isEnabledFlag == 'true' ">text-center</s:if>
										<s:else>text-center  text-muted</s:else>"><s:date
													name="effortDate" format="E dd/MM/yyyy" /></td>
											<td
												class="<s:if test="#isEnabledFlag == 'true' ">text-center</s:if>
										<s:else>text-center text-muted</s:else>"><s:property
													value="effortHrs" /></td>
											<td>

												<div class="col-md-6">
													<s:if test="#isEnabledFlag == 'true'">
														<a href="editEffortId/<s:property value="effortId"/>"
															class="btn btn-info btn-xs btn-block type="button">Edit</a>
													</s:if>
													<s:else>
														<a href="#" class="btn btn-info btn-xs btn-block disabled"
															type="button">Edit</a>
													</s:else>


												</div>
												<div class="col-md-6">
													<s:if test="#isEnabledFlag == 'true'">
														<a href="deleteEffortId/<s:property value="effortId"/>"
															class="btn btn-danger btn-xs btn-block type="button">Delete</a>
													</s:if>
													<s:else>
														<a href="#"
															class="btn btn-danger btn-xs btn-block disabled"
															type="button">Delete</a>
													</s:else>
												</div>

											</td>
										</tr>
									</s:iterator>
								</s:if>
								<s:else>
									<tr>
										<td colspan="6">
											<h5 class="text-center text-muted">No data found</h5>
										</td>
									</tr>
								</s:else>

							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-1"></div>
		<div class="col-md-4 panel panel-success">
			<div class="panel-body">
				<div class="row">
					<h4 class=""></h4>
				</div>
				<div class="row">

					<s:form action="saveEfforts" theme="bootstrap">
						<s:set var="isEditFlag">
							<s:property value="editFlag" />
						</s:set>
						<s:hidden name="effortsId"></s:hidden>
						<div class="form-group readonly disabled">
							<s:textfield label="User ID / Name" name="userIdName"
								readonly="true" />
						</div>
						<div class="form-group">
							<s:select list="projectMap" name="projectId" value="projectId"
								label="Project ID / Name" headerKey="-1"
								headerValue="Select Project..." emptyOption="false" />
						</div>
						<div class="form-group ">
							<s:select list="effortTypeMap" name="effortType"
								value="effortType" label="Effort Type" headerKey="-1"
								headerValue="Select Effort Type..." emptyOption="false" />

						</div>
						<div class="row">
							<div class="col-md-4 form-group">
								<label for="effortDate">Date</label>
								<sx:datetimepicker id="effortDate" name="effortDate"
									displayFormat="dd-MM-yyyy" />
							</div>
							<div class="col-md-8 form-group">
								<s:textfield label="Actual Efforts" name="actualEffort"
									maxlength="2"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>
						</div>
						<br>
						<s:if test="#isEditFlag == 'true' ">
							<div class="col-md-6">
								<s:submit value="Update" class="btn btn-success btn-block" />
							</div>
							<div class="col-md-6">
								<a href="userHome" class="btn btn-warning btn-block"
									type="button">Cancel</a>
							</div>
						</s:if>
						<s:else>
							<s:submit value="Save" class="btn btn-success btn-block" />
						</s:else>
					</s:form>
				</div>
			</div>
		</div>
	</div>
</body>

</html>

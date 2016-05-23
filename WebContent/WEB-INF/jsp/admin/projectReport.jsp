<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<title>Effort Tracker</title>
<sx:head />
<sb:head />
<script type="text/javascript">
	function doMath() {
		// Capture the entered values of two input boxes
		var designeffort = document.getElementById('designeffort').value;
		var buildeffort = document.getElementById('buildeffort').value;
		var siteffort = document.getElementById('siteffort').value;
		var uateffort = document.getElementById('uateffort').value;
		var impleffort = document.getElementById('impleffort').value;
		var sum = parseInt(designeffort) + parseInt(buildeffort)
				+ parseInt(siteffort) + parseInt(uateffort)
				+ parseInt(impleffort);
		document.getElementById("esteffort").value = sum;

	}
	function removeZero(effortElement) {
		if (document.getElementById(effortElement).value == 0)
			document.getElementById(effortElement).value = "";
	}

	function addZero(effortElement) {
		if (document.getElementById(effortElement).value == "")
			document.getElementById(effortElement).value = 0;
	}
</script>
</head>
<s:include value="../header.jsp"></s:include>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4">
				<h3 class="text-left text-muted">
					<s:property value="projectname" />
					-
					<s:property value="projectnum" />
				</h3>
			</div>

			<div class="col-md-2">
				<a href="editProjectIdView/<s:property value="projectid"/>"
					class="btn btn-info btn-sm btn-block" type="button">Edit this project</a>
			</div>
			<div class="col-md-2">
				<a href="viewProject" class="btn btn-warning btn-sm btn-block"
					type="button"> View all project </a>
			</div>
			<div class="col-md-2">
				<a href="adminHome" class="btn btn-primary btn-sm btn-block"
					type="button"> Admin Home </a>
			</div>
			<div class="col-md-2">
				<a href="downloadExl" class="btn btn-success btn-sm btn-block"
					type="button"> Download as Excel </a>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 panel panel-info">
				<div class="panel-body">

					<div class="row">

						<s:form action="projectReport" theme="bootstrap">
								<div class=" col-md-3 form-group">
									<s:select label="User id" headerValue="Select user.."
										headerKey="-1" list="useridList" name="filterUserid"
										value="filterUserid" emptyOption="false" />
								</div>
								<div class="col-md-3 form-group ">
									<s:select list="effortTypeMap" name="filterEffortType"
										value="filterEffortType" label="Effort Type" headerKey="null"
										headerValue="Select Effort Type..." emptyOption="false" />
								</div>
							<div class="col-md-2 form-group">
								<label for="filterStartDate">Start date</label>
								<sx:datetimepicker id="filterStartDate" name="filterStartDate"
									displayFormat="dd-MM-yyyy" />
							</div>
							<div class="col-md-2 form-group">
								<label for="filterEndDate">End date</label>
								<sx:datetimepicker id="filterEndDate" name="filterEndDate"
									displayFormat="dd-MM-yyyy" />
							</div>
							<div class="col-md-2" ><br>
							<s:submit value="Filter" class="btn btn-success btn-md btn-block" />
							</div>
						</s:form>

					</div>
					<div class="row">
						<div class="col-md-12">
							<table class="table table-condensed">
								<thead>
									<tr class=active>
										<th class="text-center text-muted">#</th>
										<th class="text-center text-muted">User ID - Name</th>
										<th class="text-center text-muted">Type</th>
										<th class="text-center text-muted">Date</th>
										<th class="text-center text-muted">Efforts</th>
										<th class="text-center text-muted">Actions</th>
									</tr>
								</thead>


								<tbody>
									<s:if test="%{effortsProjectList.size>0}">
										<s:iterator value="effortsProjectList" status="rowstatus">

											<tr
												class="<s:if test="#rowstatus.odd == true ">warning</s:if><s:else>info</s:else>">
												<s:set var="isEnabledFlag">
													<s:property value="userVo.isEnabled" />
												</s:set>
												<td class="text-center"><s:property
														value="#rowstatus.count" /></td>
												<td
													class="<s:if test="#isEnabledFlag == 'true' ">text-center</s:if>
										<s:else>text-center text-muted</s:else>"><s:property
														value="userVo.userId" /> - <s:property
														value="userVo.fullName" /></td>
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
													<div class="col-md-12">
														<a href="deleteEffortByAdmin/<s:property value="effortId"/>"
															class="btn btn-danger btn-xs btn-block type="button">Delete</a>
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
					<div class="row"></div>
				</div>

			</div>
			<div class="col-md-4 panel panel-warning">
				<div class="panel-body">
					<div class="row">
						<div class="col-md-6">
							<h4 class="text-left text-muted">
								<u>Phase Type</u>
							</h4>
						</div>
						<div class="col-md-3">
							<h4 class="text-left text-muted">
								<u>Estimated</u>
							</h4>
						</div>

						<div class="col-md-3">
							<h4 class="text-left text-muted">
								<u>Actual</u>
							</h4>
						</div>
					</div>
					<div class="row">
						<s:form theme="bootstrap">
							<div class="col-md-6">
								<h4 class="text-left text-muted">Total Efforts</h4>
							</div>
							<div class="col-md-3">
								<s:textfield id="esteffort" name="esteffort" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>

							<div class="col-md-3">
								<s:textfield id="actualeffort" name="actualeffort" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>
						</s:form>
					</div>
					<div class="row">
						<s:form theme="bootstrap">
							<div class="col-md-6">
								<h4 class="text-left text-muted">Design phase</h4>
							</div>
							<div class="col-md-3">
								<s:textfield id="designeffort" name="designeffort" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>

							<div class="col-md-3">
								<s:textfield id="designeffortAct" name="designeffortAct"
									maxlength="4" readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>
						</s:form>
					</div>
					<div class="row">
						<s:form theme="bootstrap">
							<div class="col-md-6">
								<h4 class="text-left text-muted">Build phase</h4>
							</div>
							<div class="col-md-3">
								<s:textfield id="buildeffort" name="buildeffort" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>

							<div class="col-md-3">
								<s:textfield id="buildeffortAct" name="buildeffortAct"
									maxlength="4" readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>
						</s:form>
					</div>
					<div class="row">
						<s:form theme="bootstrap">
							<div class="col-md-6">
								<h4 class="text-left text-muted">SIT phase</h4>
							</div>
							<div class="col-md-3">
								<s:textfield id="siteffort" name="siteffort" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>

							<div class="col-md-3">
								<s:textfield id="siteffortAct" name="siteffortAct" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>
						</s:form>
					</div>
					<div class="row">
						<s:form theme="bootstrap">
							<div class="col-md-6">
								<h4 class="text-left text-muted">UAT phase</h4>
							</div>
							<div class="col-md-3">
								<s:textfield id="uateffort" name="uateffort" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>

							<div class="col-md-3">
								<s:textfield id="uateffortAct" name="uateffortAct" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>
						</s:form>
					</div>
					<div class="row">
						<s:form theme="bootstrap">
							<div class="col-md-6">
								<h4 class="text-left text-muted">Implementation phase</h4>
							</div>
							<div class="col-md-3">
								<s:textfield id="impleffort" name="impleffort" maxlength="4"
									readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>

							<div class="col-md-3">
								<s:textfield id="impleffortAct" name="impleffortAct"
									maxlength="4" readonly="true"
									onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
							</div>
						</s:form>
					</div>

				</div>
			</div>

		</div>
	</div>

</body>

</html>

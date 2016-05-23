<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head />
<sb:head />
<script type="text/javascript">
    function doMath()
    {
        // Capture the entered values of two input boxes
        var designeffort = document.getElementById('designeffort').value;
        var buildeffort = document.getElementById('buildeffort').value;
        var siteffort = document.getElementById('siteffort').value;
		var uateffort = document.getElementById('uateffort').value;
        var impleffort = document.getElementById('impleffort').value;
        var sum = parseInt(designeffort) + parseInt(buildeffort)+ parseInt(siteffort) + parseInt(uateffort)+ parseInt(impleffort);
        document.getElementById("esteffort").value = sum;
   
    }
    function removeZero(effortElement){
    	if(document.getElementById(effortElement).value == 0)
    	 document.getElementById(effortElement).value  = ""; 
    }
    
    function addZero(effortElement){
    	if(document.getElementById(effortElement).value == "")
    	 document.getElementById(effortElement).value  = 0; 
    }
</script>
</head>
<s:include value="../header.jsp"></s:include>
<body>

	<div class="container-fluid">
		<div class="col-md-8 col-md-offset-2 panel panel-info">
			<div class = "panel-heading">
		     <h4 class="text-left text-muted">Add New Project</h4>
		   </div>
		   <div class = "panel-body">
				<s:form action="saveProject" theme="bootstrap">
					<div class="form-group required">
						<s:textfield label="Project Number" name="projectnum" maxlength="20"/>
					</div>
					<div class="form-group required">
						<s:textfield label="Project Name" name="projectname" maxlength="30"/>
					</div>
					<div class="form-group">
						<s:textfield label="Total Estimated Efforts in hours"  id="esteffort" name="esteffort" maxlength="4" readonly="true" 
						onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
					</div>
					<div class="form-group">
					<div class="row">
					<div class="col-md-2">
						<s:textfield label="Design phase"  id="designeffort" name="designeffort" maxlength="4" 
						onfocus="removeZero('designeffort');" onblur="addZero('designeffort')" onchange="doMath();" 
						onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
					</div>
					<div class="col-md-2">
						<s:textfield label="Build phase"  id="buildeffort" name="buildeffort" maxlength="4" 
						onfocus="removeZero('buildeffort');" onblur="addZero('buildeffort')"  onchange="doMath();" 
						onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
					</div>
					<div class="col-md-2">
						<s:textfield label="SIT phase" id="siteffort" name="siteffort" maxlength="4" 
						onfocus="removeZero('siteffort');" onblur="addZero('siteffort')" onchange="doMath();" 
						onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
					</div>
					<div class="col-md-2">
						<s:textfield label="UAT phase" id="uateffort" name="uateffort" maxlength="4" 
						 onfocus="removeZero('uateffort');" onblur="addZero('uateffort')" onchange="doMath();"
						 onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
					</div>
					<div class="col-md-3">
						<s:textfield label="Implementation phase" id="impleffort"  name="impleffort" maxlength="4" 
						onfocus="removeZero('impleffort');" onblur="addZero('impleffort')" onchange="doMath();" 
						onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
					</div>
					</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label for="startDate">Start Date</label>
								<sx:datetimepicker name="startdate"
									displayFormat="dd-MM-yyyy" onchange="doMath();" onclick="doMath();"/>
							</div>
							<div class="col-md-4">
								<label for="startDate">End Date</label>
								<sx:datetimepicker name="enddate"
									displayFormat="dd-MM-yyyy" onchange="doMath();" onclick="doMath();"/>
							</div>
							<div>
							<s:radio label="Status" name="status" labelposition="inline" list="#{'true':'Enable','false':'Disable'}" value = "status" />
		
							</div>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-4">
							<s:submit value="Save" class="btn btn-success btn-block" onclick="doMath();"/>
						</div>
						<div class="col-md-4">
							<s:reset value="Reset" class="btn btn-warning btn-block"  onclick="doMath();"/>
						</div>
						<div class="col-md-4">
							<a href="viewProject" class="btn btn-info btn-block" type="button">Cancel</a>
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</div>
</body>

</html>

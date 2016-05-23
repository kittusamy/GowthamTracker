package com.struts.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.hibernate.HibernateException;

import com.hibernate.dao.BaseDAO;
import com.hibernate.vo.TempVO;
import com.hibernate.vo.EffortsVO;
import com.hibernate.vo.ProjectVO;
import com.struts.util.ApplicationConstant;
import com.struts.util.ApplicationUtil;

public class ProjectAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private List<ProjectVO> projectList;
	private List<EffortsVO> effortsProjectList;
	private List<Integer> useridList;
	private Map effortTypeMap;
	private String months[] = {
		      "Jan", "Feb", "Mar", "Apr",
		      "May", "Jun", "Jul", "Aug",
		      "Sep", "Oct", "Nov", "Dec"};
	private ProjectVO projectVO = new ProjectVO();
	private EffortsVO effortsVO = new EffortsVO();

	private String projectnum;
	private String projectname;
	private String viewPath;
	private String ContentDisposition;
	private InputStream excelStream;

	private Integer esteffort;
	private Integer designeffort;
	private Integer buildeffort;
	private Integer siteffort;
	private Integer uateffort;
	private Integer impleffort;

	private Integer actualeffort;
	private Integer designeffortAct;
	private Integer buildeffortAct;
	private Integer siteffortAct;
	private Integer uateffortAct;
	private Integer impleffortAct;

	private Date startdate;
	private Date enddate;
	private Boolean status;
	private Integer projectid;
	private Integer effortsId;

	private Integer filterUserid;
	private String filterEffortType;
	private Date filterStartDate;
	private Date filterEndDate;
	

	public String viewProject() {
		try {
			projectList = new ArrayList<ProjectVO>();
			projectList = BaseDAO.retrieve(ApplicationConstant.PROJECTVO, null, true, true,
					ApplicationConstant.UPDATED_DATE);
			return SUCCESS;
		} catch (HibernateException e) {
			addActionError("Error in Retriving values: Please contact technical Support ");
			return INPUT;
		}
	}

		
	public String addProject() {
		setDefaultEfforts();
		status = false;
		return SUCCESS;

	}

	public String saveProject() {

		if (validateProject()) {
			setDefaultEfforts();
			addActionError("*Mandatory fields are required");
			return INPUT;
		} else {
			setFormToDB();
			projectVO.setIsActive(true);
			projectVO.setCreatedDate(new Date());
			projectVO.setCreatedBy(getText("admin.username"));
			try {
				projectVO = (ProjectVO) BaseDAO.save(projectVO);
				addActionMessage("Save Success : " + projectVO.getProjectNum() + "-" + projectVO.getProjectName()
						+ " is saved succesfully");
				return SUCCESS;

			} catch (HibernateException e) {
				addActionError("Save Failed : Please contact technical Support");
				setDefaultEfforts();
				return INPUT;
			}
		}
	}

	// Edit existing Project button action
	public String editProjectIdView() {
		sessionAttributes.put("ProjectId", projectid);
		sessionAttributes.put("ProjectPath", "projectReport");
		return SUCCESS;
	}

	// Edit existing Project button action
	public String editProjectId() {
		sessionAttributes.put("ProjectId", projectid);
		sessionAttributes.put("ProjectPath", "editView");
		return SUCCESS;
	}

	// Edit existing Project button action opens Edit page with required fields.
	public String editProject() {
		if (sessionAttributes.containsKey("ProjectId")) {
			projectVO = (ProjectVO) BaseDAO.retrieveForValue((Integer) sessionAttributes.get("ProjectId"),
					ApplicationConstant.PROJECTVO);
		}
		sessionAttributes.put("ProjectVO", projectVO);
		setDefaultEfforts();
		setDBtoForm();
		return SUCCESS;
	}

	// Update existing Project button action.
	public String updateProject() {
		if (validateProject()) {
			addActionError("*Mandatory fields are required");
			return INPUT;
		} else {

			if (sessionAttributes.containsKey("ProjectVO")) {
				projectVO = (ProjectVO) sessionAttributes.get("ProjectVO");
			}
			if (sessionAttributes.containsKey("ProjectPath")) {
				viewPath = (String) sessionAttributes.get("ProjectPath");
			}
			setFormToDB();
			try {
				projectVO = (ProjectVO) BaseDAO.merge(projectVO);
				addActionMessage("Update Success :" + projectVO.getProjectNum() + " - " + projectVO.getProjectName()
						+ "is updated succesfully");
				if (null != viewPath && "projectReport".equalsIgnoreCase(viewPath)) {
					return "reportView";
				} else {
					return SUCCESS;
				}
			} catch (HibernateException e) {
				addActionError("Update Failed : Please contact technical Support");
				return INPUT;
			}

		}

	}

	// Validate project form
	private boolean validateProject() {
		boolean flag = false;
		if (projectnum == null || projectnum.trim().equals("")) {
			addFieldError("projectnum", "The project number is required");
			flag = true;
		}
		if (projectname == null || projectname.trim().equals("")) {
			addFieldError("projectname", "The project name is required");
			flag = true;
		}
		return flag;
	}

	// Set project Id for the click action
	public String setProjectID() {
		sessionAttributes.put("ProjectId", projectid);
		return SUCCESS;
	}
		
	// Delete existing Project button action
	public String deleteProjectId() {
		sessionAttributes.put("ProjectId", projectid);
		return SUCCESS;
	}
	
	// soft delete existing Project in database.
	public String deleteProject() {
		if (sessionAttributes.containsKey("ProjectId")) {
			Integer projectId = (Integer) sessionAttributes.get("ProjectId");
			projectVO = (ProjectVO) BaseDAO.retrieveForValue(projectId, ApplicationConstant.PROJECTVO);
			projectVO.setIsActive(false);
			projectVO.setIsEnabled(false);
			projectVO = (ProjectVO) BaseDAO.merge(projectVO);
			addActionMessage("Delete Success: " + projectId + " deleted succesfully");
		} else {
			addActionError("Delete Failed:  Please contact technical Support");
		}
		return SUCCESS;
	}

	// Enable project for existing project.
	public String enableProject() {
		if (sessionAttributes.containsKey("ProjectId")) {
			projectid = (Integer) sessionAttributes.get("ProjectId");
		}
		if (BaseDAO.projectEnableDisable(projectid, true)) {
			addActionMessage("Project enabled successfully");
		} else {
			addActionError("Enable Failed: Please contact technical Support");
		}

		return SUCCESS;
	}

	// Disable Project for existing project.
	public String disableProject() {
		if (sessionAttributes.containsKey("ProjectId")) {
			projectid = (Integer) sessionAttributes.get("ProjectId");
		}
		if (BaseDAO.projectEnableDisable(projectid, false)) {
			addActionMessage("Project disabled successfully");
		} else {
			addActionError("Disable Failed: Please contact technical Support");
		}
		return SUCCESS;
	}

	// view project Report.
	public String projectReport() {
		setDefaultEfforts();
		if (sessionAttributes.containsKey("ProjectId")) {
			projectid = (Integer) sessionAttributes.get("ProjectId");
			projectVO = (ProjectVO) BaseDAO.retrieveForValue(projectid, ApplicationConstant.PROJECTVO);
			useridList = BaseDAO.getAllUserforProject(projectid);
			setDBtoForm();
			setActualEfforts(projectVO);
			setEffortsProjectList(new ArrayList<EffortsVO>());
			setEffortsProjectList(BaseDAO.retrieveEffortsForProject(projectVO, filterUserid, filterEffortType, filterStartDate, filterEndDate));
		}
		return SUCCESS;
	}
	
	// Download Report
	public String downloadExcel(){
		setDefaultEfforts();
		if (sessionAttributes.containsKey("ProjectId")) {
			projectid = (Integer) sessionAttributes.get("ProjectId");
			projectVO = (ProjectVO) BaseDAO.retrieveForValue(projectid, ApplicationConstant.PROJECTVO);
			setActualEfforts(projectVO);
			setEffortsProjectList(new ArrayList<EffortsVO>());
			setEffortsProjectList(BaseDAO.retrieveEffortsForProject(projectVO, null, null, null, null));
		}
		
		 setContentDisposition("attachment; filename=\"" +projectVO.getProjectName() + ".xls\"");
			 
		 
		    try{
		        HSSFWorkbook hwb=new HSSFWorkbook();
		        HSSFSheet sheet =  hwb.createSheet(String.valueOf(projectVO.getProjectId()));
		        sheet.autoSizeColumn((short)14);

		        //////You can repeat this part using for or while to create multiple rows//////
		        //Set Header Font
		        HSSFFont headerFont = hwb.createFont();
		        headerFont.setBoldweight(headerFont.BOLDWEIGHT_NORMAL);
		        headerFont.setFontHeightInPoints((short) 14);

		        //Set Header Style
		        CellStyle headerStyle = hwb.createCellStyle();
		        headerStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
		        headerStyle.setAlignment(headerStyle.ALIGN_CENTER);
		        headerStyle.setFont(headerFont);
		        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        
		        HSSFFont contentFont = hwb.createFont();
		        contentFont.setBoldweight(contentFont.BOLDWEIGHT_NORMAL);
		        contentFont.setFontHeightInPoints((short) 9);

		        //Set Header Style
		        CellStyle ContentStyle = hwb.createCellStyle();
		        ContentStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
		        ContentStyle.setAlignment(ContentStyle.ALIGN_CENTER);
		        ContentStyle.setFont(contentFont);
		        ContentStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        ContentStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        ContentStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        ContentStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        
		        HSSFFont titleFont = hwb.createFont();
		        titleFont.setBoldweight(titleFont.BOLDWEIGHT_BOLD);
		        titleFont.setFontHeightInPoints((short) 10);

		        //Set Header Style
		        CellStyle titleStyle = hwb.createCellStyle();
		        titleStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
		        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		        titleStyle.setAlignment(ContentStyle.ALIGN_CENTER);
		        titleStyle.setFont(titleFont);
		        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        titleStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        titleStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        
		        
		        int rowCount= 0;
		        Row header = sheet.createRow(0);
		        Row header1 = sheet.createRow(1);
		        Cell cell0;//= header.createCell(0);
		        Cell cell1;
		        for(int j = 0;j < 12; j++) {
		            cell0 = header.createCell(j);
		            cell1 = header1.createCell(j);
		            if(j == 0) {
		                cell0.setCellValue("Efforts detail report for project : " +projectVO.getProjectNum()+" - "+projectVO.getProjectName());
		            }
		            cell1.setCellStyle(headerStyle);
		            cell0.setCellStyle(headerStyle);
		        }
		        sheet.addMergedRegion(new CellRangeAddress(rowCount, 1, 0, 11));
		        cell0 = header.createCell(12);
		        cell0.setCellValue("Report Date :");
		        cell0.setCellStyle(ContentStyle);
	            cell1 = header1.createCell(12);
	            cell1.setCellValue("Report Time :");
	            cell1.setCellStyle(ContentStyle);
	            
	            cell0 = header.createCell(14);
	            GregorianCalendar gcalendar = new GregorianCalendar();
		        cell0.setCellValue(months[gcalendar.get(Calendar.MONTH)]+" " + gcalendar.get(Calendar.DATE) + " "+gcalendar.get(Calendar.YEAR));
		        cell0.setCellStyle(ContentStyle);
		        cell0 = header.createCell(15);
		        cell0.setCellStyle(ContentStyle);
		        
	            cell1 = header1.createCell(14);
	            cell1.setCellValue(gcalendar.get(Calendar.HOUR) + ":" +gcalendar.get(Calendar.MINUTE) + ":"+gcalendar.get(Calendar.SECOND));
	            cell1.setCellStyle(ContentStyle);
	            cell1 = header1.createCell(15);
	            cell1.setCellStyle(ContentStyle);
	            
	            sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 13));
	            sheet.addMergedRegion(new CellRangeAddress(1, 1, 12, 13));
	            sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 15));
	            sheet.addMergedRegion(new CellRangeAddress(1, 1, 14, 15));
	            
		        Row row4 = sheet.createRow(4);
		        Cell cell40 = row4.createCell(1);
		        cell40.setCellValue("Efforts : Phase");
		        cell40.setCellStyle(titleStyle);
		        cell40 = row4.createCell(2);
		        cell40.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));
		        
		        Cell cell41 = row4.createCell(3);
		        cell41.setCellValue("Design phase");
		        cell41.setCellStyle(titleStyle);
		        cell41 = row4.createCell(4);
		        cell41.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(4, 4, 3, 4));
		        
		        Cell cell42 = row4.createCell(5);
		        cell42.setCellValue("Build phase");
		        cell42.setCellStyle(titleStyle);
		        cell42 = row4.createCell(6);
		        cell42.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 6));
		        
		        Cell cell43 = row4.createCell(7);
		        cell43.setCellValue("SIT phase");
		        cell43.setCellStyle(titleStyle);
		        cell43 = row4.createCell(8);
		        cell43.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(4, 4, 7, 8));
		        
		        Cell cell44 = row4.createCell(9);
		        cell44.setCellValue("UAT phase");
		        cell44.setCellStyle(titleStyle);
		        cell44 = row4.createCell(10);
		        cell44.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(4, 4, 9, 10));
		        
		        Cell cell45 = row4.createCell(11);
		        cell45.setCellValue("Implementation");
		        cell45.setCellStyle(titleStyle);
		        cell45 = row4.createCell(12);
		        cell45.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(4, 4, 11, 12));
		        
		        Cell cell46 = row4.createCell(13);
		        cell46.setCellValue("Total Efforts");
		        cell46.setCellStyle(titleStyle);
		        cell46 = row4.createCell(14);
		        cell46.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(4, 4, 13, 14));
		        
		        Row row5 = sheet.createRow(5);
		        cell40 = row5.createCell(1);
		        cell40.setCellValue("Estimated");
		        cell40.setCellStyle(titleStyle);
		        cell40 = row5.createCell(2);
		        cell40.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(5,5, 1, 2));
		        
		        cell41 = row5.createCell(3);
		        cell41.setCellValue(projectVO.getDesignEffort());
		        cell41.setCellStyle(ContentStyle);
		        cell41 = row5.createCell(4);
		        cell41.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(5,5, 3, 4));
		        
		        cell42 = row5.createCell(5);
		        cell42.setCellValue(projectVO.getBuildEffort());
		        cell42.setCellStyle(ContentStyle);
		        cell42 = row5.createCell(6);
		        cell42.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(5,5, 5, 6));
		        
		        cell43 = row5.createCell(7);
		        cell43.setCellValue(projectVO.getUatEffort());
		        cell43.setCellStyle(ContentStyle);
		        cell43 = row5.createCell(8);
		        cell43.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(5,5, 7, 8));
		        
		        cell44 = row5.createCell(9);
		        cell44.setCellValue(projectVO.getSitEffort());
		        cell44.setCellStyle(ContentStyle);
		        cell44 = row5.createCell(10);
		        cell44.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(5,5, 9, 10));
		        
		        cell45 = row5.createCell(11);
		        cell45.setCellValue(projectVO.getImplEffort());
		        cell45.setCellStyle(ContentStyle);
		        cell45 = row5.createCell(12);
		        cell45.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(5,5, 11, 12));
		        
		        cell46 = row5.createCell(13);
		        cell46.setCellValue(projectVO.getTotalEstEffort());
		        cell46.setCellStyle(titleStyle);
		        cell46 = row5.createCell(14);
		        cell46.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(5,5, 13, 14));
		        
		        Row row6 = sheet.createRow(6);
		        cell40 = row6.createCell(1);
		        cell40.setCellValue("Actual");
		        cell40.setCellStyle(titleStyle);
		        cell40 = row6.createCell(2);
		        cell40.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(6,6, 1, 2));
		        
		        cell41 = row6.createCell(3);
		        cell41.setCellValue(designeffortAct);
		        cell41.setCellStyle(ContentStyle);
		        cell41 = row6.createCell(4);
		        cell41.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(6,6, 3, 4));
		        
		        cell42 = row6.createCell(5);
		        cell42.setCellValue(buildeffortAct);
		        cell42.setCellStyle(ContentStyle);
		        cell42 = row6.createCell(6);
		        cell42.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(6,6, 5, 6));
		        
		        cell43 = row6.createCell(7);
		        cell43.setCellValue(siteffortAct);
		        cell43.setCellStyle(ContentStyle);
		        cell43 = row6.createCell(8);
		        cell43.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(6,6, 7, 8));
		        
		        cell44 = row6.createCell(9);
		        cell44.setCellValue(uateffortAct);
		        cell44.setCellStyle(ContentStyle);
		        cell44 = row6.createCell(10);
		        cell44.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(6,6, 9, 10));
		        
		        cell45 = row6.createCell(11);
		        cell45.setCellValue(impleffortAct);
		        cell45.setCellStyle(ContentStyle);
		        cell45 = row6.createCell(12);
		        cell45.setCellStyle(ContentStyle);
		        sheet.addMergedRegion(new CellRangeAddress(6,6, 11, 12));
		        
		        cell46 = row6.createCell(13);
		        cell46.setCellValue(actualeffort);
		        cell46.setCellStyle(titleStyle);
		        cell46 = row6.createCell(14);
		        cell46.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(6,6, 13, 14));
		        
		        
		        
		        Row row9 = sheet.createRow(9);
		        cell40 = row9.createCell(2);
		        cell40.setCellValue("#");
		        cell40.setCellStyle(titleStyle);
		        cell40 = row9.createCell(3);
		        cell40.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(9,9,2,3));
		        
		        cell41 = row9.createCell(4);
		        cell41.setCellValue("User ID");
		        cell41.setCellStyle(titleStyle);
		        cell41 = row9.createCell(5);
		        cell41.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(9,9,4,5));
		        
		        cell42 = row9.createCell(6);
		        cell42.setCellValue("User Name");
		        cell42.setCellStyle(titleStyle);
		        cell42 = row9.createCell(7);
		        cell42.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(9,9,6,7));
		        
		        cell43 = row9.createCell(8);
		        cell43.setCellValue("Phase type");
		        cell43.setCellStyle(titleStyle);
		        cell43 = row9.createCell(9);
		        cell43.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(9,9,8,9));
		        
		        cell44 = row9.createCell(10);
		        cell44.setCellValue("Date");
		        cell44.setCellStyle(titleStyle);
		        cell44 = row9.createCell(11);
		        cell44.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(9,9,10,11));
		        
		        cell45 = row9.createCell(12);
		        cell45.setCellValue("Effort entered");
		        cell45.setCellStyle(titleStyle);
		        cell45 = row9.createCell(13);
		        cell45.setCellStyle(titleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(9,9,12,13));
		 
		        int nextRow =10;
		        int startingNo = 1; 
		        
		        for(EffortsVO effortsVO: effortsProjectList){
		        	
		        	  Row row = sheet.createRow(nextRow);
				        cell40 = row.createCell(2);
				        cell40.setCellValue(startingNo);
				        cell40.setCellStyle(ContentStyle);
				        cell40 = row.createCell(3);
				        cell40.setCellStyle(ContentStyle);
				        sheet.addMergedRegion(new CellRangeAddress(nextRow,nextRow,2,3));
				        
				        cell41 = row.createCell(4);
				        cell41.setCellValue(effortsVO.getUserVo().getUserId());
				        cell41.setCellStyle(ContentStyle);
				        cell41 = row.createCell(5);
				        cell41.setCellStyle(ContentStyle);
				        sheet.addMergedRegion(new CellRangeAddress(nextRow,nextRow,4,5));
				        
				        cell42 = row.createCell(6);
				        cell42.setCellValue(effortsVO.getUserVo().getFullName());
				        cell42.setCellStyle(ContentStyle);
				        cell42 = row.createCell(7);
				        cell42.setCellStyle(ContentStyle);
				        sheet.addMergedRegion(new CellRangeAddress(nextRow,nextRow,6,7));
				        
				        cell43 = row.createCell(8);
				        cell43.setCellValue(effortsVO.getEffortType());
				        cell43.setCellStyle(ContentStyle);
				        cell43 = row.createCell(9);
				        cell43.setCellStyle(ContentStyle);
				        sheet.addMergedRegion(new CellRangeAddress(nextRow,nextRow,8,9));
				        
				        cell44 = row.createCell(10);
				        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				        cell44.setCellValue(sdf.format(effortsVO.getEffortDate()));
				        cell44.setCellStyle(ContentStyle);
				        cell44 = row.createCell(11);
				        cell44.setCellStyle(ContentStyle);
				        sheet.addMergedRegion(new CellRangeAddress(nextRow,nextRow,10,11));
				        
				        cell45 = row.createCell(12);
				        cell45.setCellValue(effortsVO.getEffortHrs());
				        cell45.setCellStyle(ContentStyle);
				        cell45 = row.createCell(13);
				        cell45.setCellStyle(ContentStyle);
				        sheet.addMergedRegion(new CellRangeAddress(nextRow,nextRow,12,13));
				        startingNo++;
				        nextRow++;
		        }
		        
		        
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        hwb.write(baos);
		        setExcelStream(new ByteArrayInputStream(baos.toByteArray()));
		    }catch(Exception e){
		        System.out.println(e.getMessage());
		    }
		
		
		
		return SUCCESS;
	}



	
	
	// Set Effort id for click action
	public String setEffortID() {
		sessionAttributes.put("EffortsId", effortsId);
		return SUCCESS;
	}

	public String deleteEffortAdmin() {
		if (sessionAttributes.containsKey("EffortsId")) {
			effortsId = (Integer) sessionAttributes.get("EffortsId");
			BaseDAO.delete(effortsId, ApplicationConstant.EFFORTSVO);
			addActionMessage("Delete Success");
		}
		return SUCCESS;
	}

	// set DB to form variables.
	private void setDBtoForm() {
		projectid = projectVO.getProjectId();
		projectnum = projectVO.getProjectNum();
		projectname = projectVO.getProjectName();
		esteffort = projectVO.getTotalEstEffort();
		buildeffort = projectVO.getBuildEffort();
		siteffort = projectVO.getSitEffort();
		uateffort = projectVO.getUatEffort();
		impleffort = projectVO.getImplEffort();
		designeffort = projectVO.getDesignEffort();
		startdate = projectVO.getStartDate();
		enddate = projectVO.getEndDate();
		status = projectVO.getIsEnabled();
	}

	// set Form to DB variables.
	private void setFormToDB() {
		projectVO.setProjectId(projectid);
		projectVO.setProjectNum(projectnum);
		projectVO.setProjectName(projectname);
		projectVO.setTotalEstEffort(esteffort);
		projectVO.setDesignEffort(designeffort);
		projectVO.setBuildEffort(buildeffort);
		projectVO.setSitEffort(siteffort);
		projectVO.setUatEffort(uateffort);
		projectVO.setImplEffort(impleffort);
		projectVO.setStartDate(startdate);
		projectVO.setEndDate(enddate);
		projectVO.setIsEnabled(status);
		projectVO.setUpdatedDate(new Date());
		projectVO.setUpdatedBy(getText("admin.username"));
	}

	private void setActualEfforts(ProjectVO projectVO) {
		List<TempVO> resultList = BaseDAO.getActualEfforts(projectVO);
		actualeffort = 0;
		for (TempVO actualEffortsVO : resultList) {
			String effortType = actualEffortsVO.getStringValue();
			int efforts = actualEffortsVO.getLongValue().intValue();

			if ("DSG".equals(effortType)) {
				actualeffort = actualeffort + efforts;
				designeffortAct = efforts;
			} else if ("BLD".equals(effortType)){
				actualeffort = actualeffort + efforts;
				buildeffortAct = efforts;
			} else if ("SIT".equals(effortType)){
				actualeffort = actualeffort + efforts;
				siteffortAct = efforts;
			} else if ("UAT".equals(effortType)){
				actualeffort = actualeffort + efforts;
				uateffortAct = efforts;
			} else if ("IMP".equals(effortType)){
				actualeffort = actualeffort + efforts;
				impleffortAct = efforts;
			} else {
				break;
			}

		}

	}

	private void setDefaultEfforts() {
		esteffort = 0;
		designeffort = 0;
		buildeffort = 0;
		siteffort = 0;
		uateffort = 0;
		impleffort = 0;
		actualeffort = 0;
		designeffortAct = 0;
		buildeffortAct = 0;
		siteffortAct = 0;
		uateffortAct = 0;
		impleffortAct = 0;
	}

	public Map getEffortTypeMap() {
		return ApplicationUtil.getEffortTypeMap();
	}
	
	public void setEffortTypeMap(Map effortTypeMap) {
		this.effortTypeMap = effortTypeMap;
	}

	public String getProjectnum() {
		return projectnum;
	}

	public void setProjectnum(String projectnum) {
		this.projectnum = projectnum;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public Integer getEsteffort() {
		return esteffort;
	}

	public void setEsteffort(Integer esteffort) {
		this.esteffort = esteffort;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public ProjectVO getProjectVO() {
		return projectVO;
	}

	public void setProjectVO(ProjectVO projectVO) {
		this.projectVO = projectVO;
	}

	public Integer getDesigneffort() {
		return designeffort;
	}

	public void setDesigneffort(Integer designeffort) {
		this.designeffort = designeffort;
	}

	public Integer getBuildeffort() {
		return buildeffort;
	}

	public void setBuildeffort(Integer buildeffort) {
		this.buildeffort = buildeffort;
	}

	public Integer getSiteffort() {
		return siteffort;
	}

	public void setSiteffort(Integer siteffort) {
		this.siteffort = siteffort;
	}

	public Integer getUateffort() {
		return uateffort;
	}

	public void setUateffort(Integer uateffort) {
		this.uateffort = uateffort;
	}

	public Integer getImpleffort() {
		return impleffort;
	}

	public void setImpleffort(Integer impleffort) {
		this.impleffort = impleffort;
	}

	public void setProjectList(List<ProjectVO> projectList) {
		this.projectList = projectList;
	}

	public List<ProjectVO> getProjectList() {
		return projectList;
	}

	public EffortsVO getEffortsVO() {
		return effortsVO;
	}

	public void setEffortsVO(EffortsVO effortsVO) {
		this.effortsVO = effortsVO;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}


	public List<Integer> getUseridList() {
		return useridList;
	}

	public void setUseridList(List<Integer> useridList) {
		this.useridList = useridList;
	}

	public List<EffortsVO> getEffortsProjectList() {
		return effortsProjectList;
	}

	public void setEffortsProjectList(List<EffortsVO> effortsProjectList) {
		this.effortsProjectList = effortsProjectList;
	}

	public Integer getEffortsId() {
		return effortsId;
	}

	public void setEffortsId(Integer effortsId) {
		this.effortsId = effortsId;
	}

	public Integer getDesigneffortAct() {
		return designeffortAct;
	}

	public void setDesigneffortAct(Integer designeffortAct) {
		this.designeffortAct = designeffortAct;
	}

	public Integer getBuildeffortAct() {
		return buildeffortAct;
	}

	public void setBuildeffortAct(Integer buildeffortAct) {
		this.buildeffortAct = buildeffortAct;
	}

	public Integer getSiteffortAct() {
		return siteffortAct;
	}

	public void setSiteffortAct(Integer siteffortAct) {
		this.siteffortAct = siteffortAct;
	}

	public Integer getUateffortAct() {
		return uateffortAct;
	}

	public void setUateffortAct(Integer uateffortAct) {
		this.uateffortAct = uateffortAct;
	}

	public Integer getImpleffortAct() {
		return impleffortAct;
	}

	public void setImpleffortAct(Integer impleffortAct) {
		this.impleffortAct = impleffortAct;
	}

	public Integer getActualeffort() {
		return actualeffort;
	}

	public void setActualeffort(Integer actualeffort) {
		this.actualeffort = actualeffort;
	}

	public Integer getFilterUserid() {
		return filterUserid;
	}

	public void setFilterUserid(Integer filterUserid) {
		this.filterUserid = filterUserid;
	}

	public String getFilterEffortType() {
		return filterEffortType;
	}

	public void setFilterEffortType(String filterEffortType) {
		this.filterEffortType = filterEffortType;
	}

	public Date getFilterStartDate() {
		return filterStartDate;
	}

	public void setFilterStartDate(Date filterStartDate) {
		this.filterStartDate = filterStartDate;
	}

	public Date getFilterEndDate() {
		return filterEndDate;
	}

	public void setFilterEndDate(Date filterEndDate) {
		this.filterEndDate = filterEndDate;
	}

	public String getContentDisposition() {
		return ContentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		ContentDisposition = contentDisposition;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

}

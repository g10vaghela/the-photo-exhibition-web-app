package com.photoexhibition.portlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.portlet.search.criteria.util.SearchContainerUtil;
import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.ChildViewerLikeInfoService;
import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.model.ChildViewerLikeInfo;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.search.criteria.ViewerInfoSearchCriteria;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.CommonUtil;
import com.photoexhibition.service.util.GeneralConfigurationUtil;
import com.photoexhibition.service.vo.ChildLikeVo;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
			"com.liferay.portlet.header-portlet-css=/css/custom.css",
			"add-process-action-success-action=false",
			"javax.portlet.init-param.add-process-action-success-action=false",
			"javax.portlet.display-name=Manage Viewer Portlet",
			"com.liferay.portlet.private-request-attributes=false",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/viewer_management/view.jsp",
			"javax.portlet.name=" + ControllerPortletKeys.VIEWER_MANAGEMENT,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ManageViewerControllerPortlet extends MVCPortlet{

	private static final Log log = LogFactoryUtil.getLog(ManageViewerControllerPortlet.class);
	private ViewerInfoService viewerInfoService = BeanLocalServiceUtil.getViewerInfoService();
	private ChildInfoService childInfoService = BeanLocalServiceUtil.getChildInfoService();
	private ChildViewerLikeInfoService childViewerLikeInfoService = BeanLocalServiceUtil.getChildViewerLikeInfoService();
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String viewerId = renderRequest.getParameter("viewerId");
		String mobileNumber = renderRequest.getParameter("contectNumber");
		int totalCount = 0;
		ThemeDisplay themeDisplay = CommonUtil.getThemeDisplay(renderRequest);
		ViewerInfoSearchCriteria searchCriteria = new ViewerInfoSearchCriteria();
		List<ViewerInfo> viewerInfoList = new ArrayList<>();
		if(themeDisplay.isSignedIn()){
			SearchContainerUtil.setPaginationParameters(renderRequest, searchCriteria);
			if(Validator.isNotNull(viewerId)){
				searchCriteria.setViewerId(Long.parseLong(viewerId));
			} else if(Validator.isNotNull(mobileNumber)){
				searchCriteria.setMobileNumber(mobileNumber);
			}
			viewerInfoList = viewerInfoService.getViewerInfoBySearchCriteria(searchCriteria);
			totalCount = viewerInfoService.countViewerInfoBySearchCriteria(searchCriteria);
			SearchContainerUtil.setCommonRenderParameter(renderRequest, searchCriteria, totalCount);
		} else {
			log.error("User not signed in");
		}
		renderRequest.setAttribute("viewerInfoList", viewerInfoList);
		renderRequest.setAttribute("searchCriteria", searchCriteria);
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name="addViewer")
	public void addViewer(ActionRequest actionRequest, ActionResponse actionResponse){
		String mobileNumber = actionRequest.getParameter("contactNo");
		try {
			if(Validator.isNotNull(mobileNumber)){
				ViewerInfo viewerInfo1 = viewerInfoService.getViewerInfoByMobileNumber(mobileNumber);
				if(Validator.isNull(viewerInfo1)){
					ViewerInfo viewerInfo = new ViewerInfo();
					viewerInfo.setMobileNumber(mobileNumber);
					viewerInfo.setDeviceNumber(UUID.randomUUID().toString());
					if(Boolean.parseBoolean(GeneralConfigurationUtil.isOtpServiceOn().getValue())){
						String otpString = CommonUtil.generateOtp();
						viewerInfo.setOtp(otpString);
						if(CommonUtil.sendOtp(viewerInfo)){
							SessionMessages.add(actionRequest, "otp-sent-successfully");;
						} else {
							SessionErrors.add(actionRequest, "otp-sending-error");
						}
					} else {
						SessionErrors.add(actionRequest, "otp-service-off-error");
					}
					viewerInfoService.save(viewerInfo);
					actionRequest.setAttribute("viewerId", viewerInfo.getViewerId());
					SessionMessages.add(actionRequest, "viewer-saved-successfully");
				} else {
					SessionErrors.add(actionRequest, "mobile-number-registered-error");
				}
			}
		} catch (Exception e) {
			log.error(e);
			SessionErrors.add(actionRequest, "error-in-viewer-save");
		}
	}
	
	@ProcessAction(name="activeViewer")
	public void activeViewer(ActionRequest actionRequest, ActionResponse actionResponse){
		String viewerId = actionRequest.getParameter("viewerId");
		try {
			if(Validator.isNotNull(viewerId)){
				ViewerInfo viewerInfo = viewerInfoService.getViewerInfoById(Long.parseLong(viewerId));
				viewerInfo.setOtp("");
				viewerInfo.setOtpVerified(true);
				viewerInfoService.saveOrUpdate(viewerInfo);
				SessionMessages.add(actionRequest, "viewer-actived-successfully");
			}
		} catch (Exception e) {
			log.error(e);
			SessionErrors.add(actionRequest, "error-in-viewer-activation");
		}
	}
	
	@ProcessAction(name="viewerDetails")
	public void openViewerLikeScreen(ActionRequest actionRequest, ActionResponse actionResponse){
		String viewerId = actionRequest.getParameter("viewerId");
		if(Validator.isNotNull(viewerId)){
			ViewerInfo viewerInfo = viewerInfoService.getViewerInfoById(Long.parseLong(viewerId));
			List<ChildInfo> childInfoList = childInfoService.getChildInfoList();
			List<ChildLikeVo> childLikeVoList = new ArrayList<>();
			int totalLikes = 0;
			for (ChildInfo childInfo : childInfoList) {
				ChildViewerLikeInfo childViewerLike = childViewerLikeInfoService.getChildViewerLikeInfoByChildAndViewerId(childInfo.getChildId(), viewerInfo.getViewerId());
				ChildLikeVo childLikeVo = new ChildLikeVo();
				childLikeVo.setChildId(childInfo.getChildId());
				childLikeVo.setChildName(childInfo.getFullName());
				if(Validator.isNotNull(childViewerLike)){
					childLikeVo.setLike(true);
					totalLikes++;
				} else {
					childLikeVo.setLike(false);
				}
				childLikeVoList.add(childLikeVo);
			}
			int minimumLikes = GeneralConfigurationUtil.getMinimumLikeRequired();
			actionRequest.setAttribute("viewerInfo", viewerInfo);
			actionRequest.setAttribute("totalLikes", totalLikes);
			actionRequest.setAttribute("isLikeCounted", totalLikes >= minimumLikes);
			actionRequest.setAttribute("minLike", minimumLikes);
			actionRequest.setAttribute("childLikeVoList", childLikeVoList);
			actionResponse.setRenderParameter("mvcPath", "/viewer_management/viewer-details-page.jsp");
		}
	}
	
	@ProcessAction(name="saveViewerLike")
	public void saveViewerLike(ActionRequest actionRequest, ActionResponse actionResponse){
		System.out.println("ManageViewerControllerPortlet.saveViewerLike()");
		List<ChildInfo> childInfoList = childInfoService.getChildInfoList();
		String viewerId = (String) actionRequest.getParameter("viewerId");
		ViewerInfo viewerInfo = viewerInfoService.getViewerInfoById(Long.parseLong(viewerId));
		if(Validator.isNotNull(viewerInfo)){
			for (ChildInfo childInfo : childInfoList) {
				String childId = (String)actionRequest.getParameter("child-"+childInfo.getChildId());
				if(Validator.isNotNull(childId) && Boolean.parseBoolean(childId)){
					ChildViewerLikeInfo childViewerLikeInfo = childViewerLikeInfoService.getChildViewerLikeInfoByChildAndViewerId(childInfo.getChildId(), Long.parseLong(viewerId));
					System.out.println("===>> "+childViewerLikeInfo);
					if(Validator.isNull(childViewerLikeInfo)){
						System.out.println(" no like found");
						ChildViewerLikeInfo childViewerLikeInfo2 = new ChildViewerLikeInfo();
						childViewerLikeInfo2.setChildInfo(childInfo);
						childViewerLikeInfo2.setViewerInfo(viewerInfo);
						childViewerLikeInfo2.setLike(true);
						childViewerLikeInfoService.saveOrUpdate(childViewerLikeInfo2);
					}
				}
			}
		}
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		List<ViewerInfo> viewerInfoList = viewerInfoService.getViewerList();
		if(Validator.isNotNull(viewerInfoList)){
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			HSSFSheet hssfSheet = hssfWorkbook.createSheet("Sales Report");			
			HSSFCellStyle style = hssfWorkbook.createCellStyle();

			HSSFPalette palette = hssfWorkbook.getCustomPalette();			
			// get the color which most closely matches the color you want to use
			HSSFColor myColor = palette.findSimilarColor(12,76,163);
			
			style.setFillForegroundColor(myColor.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFFont font = hssfWorkbook.createFont();
			font.setColor(HSSFColor.WHITE.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
			int rowCount = 0;
			Cell cell;
			Row row = hssfSheet.createRow(rowCount++);
			cell = row.createCell(0);
			cell.setCellValue("Viwer Id");
			cell.setCellStyle(style);
			
			cell = row.createCell(1);
			cell.setCellValue("Mobile Number");
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue("Total Like");
			cell.setCellStyle(style);
			
			for (ViewerInfo viewerInfo : viewerInfoList) {
				int totalLike = childViewerLikeInfoService.countTotalLikeByViewerId(viewerInfo.getViewerId());
				row = hssfSheet.createRow(rowCount++);
				cell = row.createCell(0);
				cell.setCellValue(viewerInfo.getViewerId());
				cell = row.createCell(1);
				cell.setCellValue(viewerInfo.getMobileNumber());
				cell = row.createCell(2);
				cell.setCellValue(totalLike);
			}
			
			resourceResponse.setContentType("application/vnd.ms-excel");
			resourceResponse.addProperty("Content-Disposition", "attachment;filename=viewer_Details.xls");
			OutputStream fileOut = resourceResponse.getPortletOutputStream();
			hssfWorkbook.write(fileOut);
			fileOut.close();
		}
		super.serveResource(resourceRequest, resourceResponse);
	}
}
package com.photoexhibition.portlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.portlet.constants.FileConstant;
import com.photoexhibition.portlet.constants.MessageConstant;
import com.photoexhibition.portlet.search.criteria.util.SearchContainerUtil;
import com.photoexhibition.portlet.validator.AdvertiseInfoValidator;
import com.photoexhibition.service.AdvertiseInfoService;
import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.search.criteria.AdvertiseInfoSearchChiteria;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.CommonUtil;
import com.photoexhibition.service.util.FileAndFolderHandler;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
			"com.liferay.portlet.header-portlet-css=/css/custom.css",
			"add-process-action-success-action=false",
			"javax.portlet.init-param.add-process-action-success-action=false",
			"javax.portlet.display-name=Advertisement Portlet",
			"com.liferay.portlet.private-request-attributes=false",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/advertise/view.jsp",
			"javax.portlet.name=" + ControllerPortletKeys.ADVERTISE_CONTROLLER,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class AdvertiseControllerPortlet extends MVCPortlet{
	
	private static Log log = LogFactoryUtil.getLog(AdvertiseControllerPortlet.class);
	private static long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	private static AdvertiseInfoService advertiseInfoService = BeanLocalServiceUtil.getAdvertiseInfoService();
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		log.debug("START :: AdvertiseControllerPortlet.render()");
		String advertiseId = renderRequest.getParameter("advertiseId");
		String advertiseName = renderRequest.getParameter("advertiseName");
		String advertiseStatus = renderRequest.getParameter("advertiseStatus");
		log.info("advertiseId : "+advertiseId);
		log.info("advertiseName : "+advertiseName);
		log.info("advertiseStatus : "+advertiseStatus);
		List<AdvertiseInfo> advertiseInfoList = new ArrayList<>();
		int totalCount = 0;
		ThemeDisplay themeDisplay = CommonUtil.getThemeDisplay(renderRequest);
		AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria = new AdvertiseInfoSearchChiteria();
		if(themeDisplay.isSignedIn()){
			if(Validator.isNotNull(advertiseId)
				|| Validator.isNotNull(advertiseName)){
				SearchContainerUtil.setPaginationParameters(renderRequest, advertiseInfoSearchChiteria);
				if(Validator.isNotNull(advertiseId)){
					advertiseInfoSearchChiteria.setAdvertiseId(Long.parseLong(advertiseId));
				} else if(Validator.isNotNull(advertiseName)){
					advertiseInfoSearchChiteria.setAdvertiseName(advertiseName);
				}
			}
			if(Validator.isNull(advertiseStatus)){
				advertiseStatus = "0";
			}
			advertiseInfoSearchChiteria.setStatus(Integer.parseInt(advertiseStatus));
			advertiseInfoList = advertiseInfoService.getAdvertiseInfoList(advertiseInfoSearchChiteria);
			totalCount = advertiseInfoService.getAdvertiseInfoCount(advertiseInfoSearchChiteria);
			SearchContainerUtil.setCommonRenderParameter(renderRequest, advertiseInfoSearchChiteria, totalCount);
		} else {
			log.info("User longin not found");
		}
		renderRequest.setAttribute("searchCriteria", advertiseInfoSearchChiteria);
		renderRequest.setAttribute("advertiseInfoList", advertiseInfoList);
		log.debug("END :: AdvertiseControllerPortlet.render()");
		super.render(renderRequest, renderResponse);
	}
	@ProcessAction(name="updateAdvertise")
	public void updateAdvertise(ActionRequest actionRequest, ActionResponse actionResponse){
		log.debug("START :: AdvertiseControllerPortlet.updateAdvertise()");
		String advertiseId = actionRequest.getParameter("advertiseId");
		String advertiseName = actionRequest.getParameter("advertiseName");
		String isAdvertisementActive = actionRequest.getParameter("isAdvertisementActive");
		try {
			if(Validator.isNotNull(advertiseId)){
				AdvertiseInfo advertiseInfo = advertiseInfoService.getAdvertiseInfoById(Long.parseLong(advertiseId));
				if(Validator.isNotNull(advertiseName)){
					advertiseInfo.setAdvertiseName(advertiseName);
				}
				if(Validator.isNotNull(isAdvertisementActive)){
					advertiseInfo.setActiveStatus(true);
				} else {
					advertiseInfo.setActiveStatus(false);
				}
				advertiseInfoService.saveOrUpdate(advertiseInfo);
				SessionMessages.add(actionRequest, MessageConstant.ADVERTISEMENT_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
			} else {
				log.error("Error: null advertise id found");
				SessionErrors.add(actionRequest, MessageConstant.ADVERTISEMENT_SAVE_OR_UPDATE_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error :: "+e);
			SessionErrors.add(actionRequest, MessageConstant.ADVERTISEMENT_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.debug("END :: AdvertiseControllerPortlet.updateAdvertise()");
	}
	@ProcessAction(name="addAdvertisement")
	public void addAdvertisement(ActionRequest actionRequest, ActionResponse actionResponse){
		log.debug("START :: AdvertiseControllerPortlet.addAdvertisement()");
		try {
			String advertiseName = actionRequest.getParameter("advertiseName");
			String isAdvertisementActive = actionRequest.getParameter("isAdvertisementActive");
			String photoOrientation = actionRequest.getParameter("orientation");
			File photoFile = FileAndFolderHandler.getUploadedFile(actionRequest);
			System.out.println("advertiseName  ::"+advertiseName);
			System.out.println("isAdvertisementActive  ::"+isAdvertisementActive);
			System.out.println("photoOrientation  ::"+photoOrientation);
			System.out.println("photoFile  ::"+photoFile.getName());
			if(AdvertiseInfoValidator.isValidAdvertisement(advertiseName, photoOrientation)){
				AdvertiseInfo advertiseInfo = new AdvertiseInfo();
				advertiseInfo.setAdvertiseName(advertiseName);
				advertiseInfo.setOrientation(Integer.parseInt(photoOrientation));
				if(Validator.isNotNull(isAdvertisementActive)){
					advertiseInfo.setActiveStatus(true);
				} else {
					advertiseInfo.setActiveStatus(false);
				}
				advertiseInfo = advertiseInfoService.save(advertiseInfo);
				if(Validator.isNotNull(advertiseInfo)) {
					uploadFileForAdvertisement(actionRequest, advertiseInfo);
					advertiseInfoService.saveOrUpdate(advertiseInfo);
					SessionMessages.add(actionRequest, MessageConstant.ADVERTISEMENT_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
				} else {
					SessionErrors.add(actionRequest, MessageConstant.ADVERTISEMENT_SAVE_OR_UPDATE_ERROR_MESSAGE);
				}
			} else {
				SessionErrors.add(actionRequest, MessageConstant.SERVER_SIDE_VALIDATION_FAIL_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : "+e);
			e.printStackTrace();
		}
		log.debug("END :: AdvertiseControllerPortlet.addAdvertisement()");
	}	

	private void uploadFileForAdvertisement(ActionRequest actionRequest, AdvertiseInfo advertiseInfo) {
		File file = FileAndFolderHandler.getUploadedFile(actionRequest);
		ThemeDisplay themeDisplay = CommonUtil.getThemeDisplay(actionRequest);
		log.info("File to upload :: "+file.getName());
		if(Validator.isNotNull(file)){
			DLFolder homeDLFolder = null;
			try {
				homeDLFolder = FileAndFolderHandler.getDLFolder(FileConstant.ADVERTISE_HOME_FOLDER_NAME, themeDisplay, parentFolderId);	
			} catch (Exception e) {
				log.info("Home Folder Not exist, Now going to create new one");
				homeDLFolder = FileAndFolderHandler.createDLFolder(actionRequest, themeDisplay, FileConstant.ADVERTISE_HOME_FOLDER_NAME, "Folder "+FileConstant.ADVERTISE_HOME_FOLDER_NAME,parentFolderId);
				log.info("Home Folder created : "+homeDLFolder);
			}
			log.info("Home folder :: "+homeDLFolder);
			DLFolder folder = null;
			try {
				folder = FileAndFolderHandler.getDLFolder(String.valueOf(advertiseInfo.getAdvertiseId()), themeDisplay, parentFolderId);
			} catch (Exception e) {
				log.info("Folder not found, Now creating new one");
				folder = FileAndFolderHandler.createDLFolder(actionRequest, themeDisplay, String.valueOf(advertiseInfo.getAdvertiseId()), "Folder of Advertisement:"+advertiseInfo.getAdvertiseId(),homeDLFolder.getFolderId());
				log.info("Folder Created ::"+folder);
			}						
			log.info("Folder ::"+folder);
			
			try {
				DLFileEntry uploadedFile = FileAndFolderHandler.fileUploadByDL(themeDisplay, actionRequest, folder, file);
				String photoLink = null;
				if(Validator.isNotNull(uploadedFile)){
					photoLink = FileAndFolderHandler.getPhotoDLFileLink(themeDisplay, uploadedFile);
				}
				advertiseInfo.setAdvertisePhotoUrl(photoLink);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			advertiseInfo.setAdvertisePhotoUrl(null);
			SessionErrors.add(actionRequest, MessageConstant.ADVERTISEMENT_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}	
	}
	
	@ProcessAction(name="updateAdvertisement")
	public void updateAdvertisement(ActionRequest actionRequest, ActionResponse actionResponse){
		log.debug("START :: AdvertiseControllerPortlet.updateAdvertisement()");
		
		log.debug("END :: AdvertiseControllerPortlet.updateAdvertisement()");
	}
	
	@ProcessAction(name="searchAdvertisement")
	public void searchAdvertisement(ActionRequest actionRequest, ActionResponse actionResponse){
		log.debug("START :: AdvertiseControllerPortlet.searchAdvertisement()");
		String advertiseId = actionRequest.getParameter("advertiseId");
		String advertiseName = actionRequest.getParameter("advertiseName");
		String advertiseStatus = actionRequest.getParameter("advertiseStatus");
		
		System.out.println("advertiseName "+advertiseName);
		System.out.println("advertiseId "+advertiseId);
		System.out.println("advertiseStatus "+advertiseStatus);
		log.debug("END :: AdvertiseControllerPortlet.searchAdvertisement()");
	}
	

	
}

package com.photoexhibition.service.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;

public class FileAndFolderHandler {
	private static final Log log = LogFactoryUtil.getLog(FileAndFolderHandler.class);

	public static Folder createFolder(RenderRequest renderRequest, ThemeDisplay themeDisplay, String name,
			String description, long parentFolderId) {
		long repositoryId = themeDisplay.getScopeGroupId();// repository id is
															// same as groupId
		//long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID; // or
																			// 0
		Folder folder = null;
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFolder.class.getName(), renderRequest);
			folder = DLAppServiceUtil.addFolder(repositoryId, parentFolderId, name, description, serviceContext);
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

		return folder;
	}

	public static DLFolder createDLFolder(PortletRequest portletRequest, ThemeDisplay themeDisplay, String folderName,
			String description,long parentFolderId) {
		System.out.println("FileAndFolderHandler.createDLFolder()");
		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getScopeGroupId();
		long repositoryId = themeDisplay.getScopeGroupId();// repository id is
															// same as groupId
		boolean mountPoint = false; // mountPoint which is a boolean specifying
									// whether the folder is a facade for
									// mounting a third-party repository
		//long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID; // or
																			// 0
		boolean hidden = false; // true if you want to hidden the folder
		DLFolder dlFolder = null;
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFolder.class.getName(), portletRequest);
			dlFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, mountPoint, parentFolderId, folderName,
					description, hidden, serviceContext);
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		return dlFolder;
	}

	public static DLFileEntry fileUploadByDL(ThemeDisplay themeDisplay, PortletRequest portletRequest,DLFolder folder, File file) {
		System.out.println("Exist=>" + file.exists());
		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getScopeGroupId();
		long repositoryId = themeDisplay.getScopeGroupId();
		String mimeType = MimeTypesUtil.getContentType(file);
		String title = file.getName();
		String description = "This file is added via programatically";
		String changeLog = "hi";
		boolean isFileUploaded = false;
		try {
			/*DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(),parentFolderId, folderName);
			long fileEntryTypeId = dlFolder.getDefaultFileEntryTypeId();*/
			long fileEntryTypeId = folder.getDefaultFileEntryTypeId();
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					portletRequest);
			InputStream is = new FileInputStream(file);
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(userId, groupId, repositoryId,
					folder.getFolderId(), file.getName(), mimeType, title, description, changeLog, fileEntryTypeId,
					null, file, is, file.getTotalSpace(), serviceContext);

			// Change mode of Draft to Approved
			dlFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(userId, dlFileEntry.getFileEntryId(), file.getName(),
					MimeTypesUtil.getContentType(file), title, description, "Draft to save", true,
					dlFileEntry.getFileEntryTypeId(), null, file, null, file.getTotalSpace(), serviceContext);
			return dlFileEntry;
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
		return null;
	}

	public static boolean fileUploadByApp(ThemeDisplay themeDisplay, PortletRequest portletRequest, Folder folder, File file) {
		System.out.println("Exist=>" + file.exists());
		long repositoryId = themeDisplay.getScopeGroupId();
		String mimeType = MimeTypesUtil.getContentType(file);
		String title = file.getName();
		String description = "This file is added via programatically";
		String changeLog = "hi";
		//Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		boolean isFileUploaded = false;
		try {
			//Folder folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), parentFolderId, folderName);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					portletRequest);
			InputStream is = new FileInputStream(file);
			DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), file.getName(), mimeType, title,
					description, changeLog, is, file.getTotalSpace(), serviceContext);
			isFileUploaded = true;
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
		return isFileUploaded;
	}

	public void getAllDLFolder(long groupId, long parentFolderId, String folderName) {
		try {
			List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getDLFolders(0,
					DLFolderLocalServiceUtil.getDLFoldersCount());
			for (DLFolder folder : dlFolders) {
				System.out.println("DLFolder Id >> " + folder.getFolderId());
				System.out.println("DLFolder Name >>" + folder.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------Finish-----------------------------------");
	}

	// DLApp deals with folder not DLFolders
	// This will provide more specific results associated with repository Id
	public void getAllFolder(ThemeDisplay themeDisplay,long parentFolderId, String name) {
		long repositoryId = themeDisplay.getScopeGroupId();
		try {
			List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId,
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			for (Folder folder : folders) {
				System.out.println("Folder Id >> " + folder.getFolderId());
				System.out.println("Folder Name >>" + folder.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DLFolder getDLFolder(String folderName, ThemeDisplay themeDisplay, long parentFolderId) throws PortalException {
		System.out.println("FileAndFolderHandler.getDLFolder()");
		long groupId = themeDisplay.getScopeGroupId();
		System.out.println("groupId ::"+groupId);
		//Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		DLFolder dir = DLFolderLocalServiceUtil.getFolder(groupId, parentFolderId, folderName);
			System.out.println("Folder Id==>" + dir.getFolderId());
		return dir;
	}

	public static Folder getFolder(String folderName, ThemeDisplay themeDisplay) {
		Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		Folder dir = null;
		try {
			dir = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), parentFolderId, folderName);
			System.out.println("Folder Id==>" + dir.getFolderId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dir;
	}

	public static String getPhotoFileLink(ThemeDisplay themeDisplay, String folderName) {
		long repositoryId = themeDisplay.getScopeGroupId();
		Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		try {
			Folder folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), parentFolderId, folderName);
			List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
			String url = null;
			for (FileEntry file : fileEntries) {
				url = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/"
						+ themeDisplay.getScopeGroupId() + "/" + file.getFolderId() + "/" + file.getTitle();
				System.out.println("Link=>" + url);
			}
			return url;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Get All Files which are even in draft
	public static String getPhotoDLFileLink(ThemeDisplay themeDisplay, DLFileEntry file) {

		try {
			//DLFolderConstants.DEFAULT_PARENT_FOLDER_ID
/*			Folder folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(),
					parentFolderId, folderName);
			List<DLFileEntry> dlFileEntries = DLFileEntryLocalServiceUtil.getFileEntries(themeDisplay.getScopeGroupId(),
					folder.getFolderId());
			for (DLFileEntry file : dlFileEntries) {*/
			String url = null;	
			url = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/"
						+ themeDisplay.getScopeGroupId() + "/" + file.getFolderId() + "/" + file.getTitle();
				System.out.println("DL Link=>" + url);
			/*}*/
			return url;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File getUploadedFile(ActionRequest request){	
		System.out.println("FileAndFolderHandler.getUploadedFile()");
		String fileInputName = request.getParameter("fileUpload");
		File file = null;
		try {
			UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
			String mimeType = uploadPortletRequest.getContentType();
			file = uploadPortletRequest.getFile("fileUpload");
			//String mT = MimeTypesUtil.getContentType(file);
			//log.info("Mime Type :: " + mimeType + " MT : " + mT);
			if(uploadPortletRequest.getFileNames("fileUpload").length > 0){
				if(uploadPortletRequest.getFileNames("fileUpload")[0] != ""){
					/*if (!MimeTypesUtil.isWebImage(mT) ){
						System.out.println("File uploading error");
					}*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return file;
	}
}

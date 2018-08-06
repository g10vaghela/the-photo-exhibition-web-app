package com.photoexhibition.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

public class CommonUtil {
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat yyyy_MM_dd_sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static ThemeDisplay getThemeDisplay(PortletRequest request) {
		if (null == request) {
			throw new IllegalArgumentException("request is null");
		}
		return (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	}
	
	public static String displayFormattedDateWithoutDash(Date date) {
		String displayData = "";
		if(date != null) {
			if (date instanceof Date) {
				displayData = String.valueOf(sdf.format(date));
			}
		} 
		return displayData;
	}
	
	public static String displayYYY_MM_DD_Format(Date date) {
		String displayData = "";
		if(date != null) {
			if (date instanceof Date) {
				displayData = String.valueOf(sdf.format(date));
			}
		} 
		return displayData;
	}
}

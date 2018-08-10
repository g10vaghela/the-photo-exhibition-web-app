package com.photoexhibition.rest.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.photoexhibition.service.util.GeneralConfigurationUtil;

public class LocationHandler {
	private static Log log = LogFactoryUtil.getLog(LocationHandler.class);
	
	public static boolean isViewerInRangeOfContest(double viewerLongitude, double viewerLatitude, double validDistance){
		boolean isViewerInRange = false;
		try {
			JSONObject contestLatLog = JSONFactoryUtil.createJSONObject(GeneralConfigurationUtil.getContestLocationLatLong());
			String contestLongitude = contestLatLog.getString("longitude");
			String contestLatitude = contestLatLog.getString("latitude");
			log.info("contestLatitude ::"+contestLatitude);
			log.info("contestLongitude ::"+contestLongitude);
			double distance = distance(Double.parseDouble(contestLatitude), Double.parseDouble(contestLongitude), viewerLatitude, viewerLongitude, 0, 0);
			log.info("distance in meter : "+distance);
			log.info("validDistance in meter : "+validDistance);
			if(distance < validDistance){
				isViewerInRange = true;
			}
		} catch (Exception e) {
			log.error("Error :: "+e);
			e.printStackTrace();
		}
		return isViewerInRange;
	}
	
	private static double distance(double contestLatitude, double contestLongitude, double viewerLatitude, 
	        double viewerLongitude, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(viewerLatitude - contestLatitude);
	    double lonDistance = Math.toRadians(viewerLongitude - contestLongitude);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(contestLatitude)) * Math.cos(Math.toRadians(viewerLatitude))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
}

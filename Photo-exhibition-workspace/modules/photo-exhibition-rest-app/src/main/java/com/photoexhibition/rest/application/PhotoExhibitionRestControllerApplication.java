package com.photoexhibition.rest.application;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.springframework.http.HttpStatus;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.photoexhibition.rest.constant.RestConstants;
import com.photoexhibition.rest.util.ContestUtil;
import com.photoexhibition.rest.util.LikeHandler;
import com.photoexhibition.rest.util.OtpHandler;
import com.photoexhibition.rest.util.ViewerDataHandler;
import com.photoexhibition.rest.util.ViewerLoginHandler;

/**
 * @author jiten
 */
@ApplicationPath("/contest")
@Component(immediate = true, service = Application.class)
public class PhotoExhibitionRestControllerApplication extends Application {
	private static final Log log = LogFactoryUtil.getLog(PhotoExhibitionRestControllerApplication.class);
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@GET
	@Produces("text/plain")
	public String working() {
		return "It works!";
	}

	@GET
	@Path("/morning")
	@Produces("text/plain")
	public String hello() {
		return "Good morning!";
	}

	@GET
	@Path("/morning/{name}")
	@Produces("text/plain")
	public String morning(
		@PathParam("name") String name,
		@QueryParam("drink") String drink) {

		String greeting = "Good Morning " + name;

		if (drink != null) {
			greeting += ". Would you like some " + drink + "?";
		}

		return greeting;
	}

	/**
	 * Checking whether contest open or not
	 * @return
	 * Response Json strucure
	{
	    "data": [
	        {
	            "isContestOpen": true,
	            "message": ""
	        }
	    ],
	    "statusCode": 200
	}
	 */
	@GET
	@Path("/iscontestopen")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isContestOpen() {
		JSONObject responseJsonObject = JSONFactoryUtil.createJSONObject();
		responseJsonObject.put(RestConstants.STATUS_CODE, HttpStatus.OK.value());
		responseJsonObject.put(RestConstants.DATA, ContestUtil.checkContestOpen());
		return Response.status(HttpStatus.OK.value()).entity(responseJsonObject.toString()).build();
	}
	
	/**
	 * Login service for viewer
	 * Request Json Structure
	{
		"mobileNumber":"1234567980",
		"deviceNumber":"6325DDd45"
	}
	 * @param viewerInfo
	 * @return
	 */
	@GET
	@Path("/viewerlogin")
	@Produces(MediaType.APPLICATION_JSON)	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response viewerLogin(@QueryParam("viewerInfo") String viewerInfo) {
		log.debug("viewerInfo :: "+viewerInfo);
		JSONObject responseJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			JSONObject viewerInfoJsonObject = (JSONObject)JSONFactoryUtil.createJSONObject(viewerInfo);
			String mobileNumber = viewerInfoJsonObject.getString(RestConstants.MOBILE_NUMBER);
			String deviceNumber = viewerInfoJsonObject.getString(RestConstants.DEVICE_NUMBER);
			log.info("Mobile Number ::" + mobileNumber);
			log.info("deviceNumber ::" + deviceNumber);
			responseJsonObject.put(RestConstants.STATUS_CODE, HttpStatus.OK.value());
			responseJsonObject.put(RestConstants.DATA, ViewerLoginHandler.handleViewerLogin(mobileNumber, deviceNumber));
		} catch (JSONException e) {
			log.error("error :: "+e);
			e.printStackTrace();
		}
		return Response.status(HttpStatus.OK.value()).entity(responseJsonObject.toString()).build();
	}
	
	/**
	 * Verify otp for mobile mobile authentication
	 * Request JSON Structure
	{
		"viewerId":362544,
		"otpString":"362512"
	}
	 * @param userInfo
	 * @return
	 */
	@GET
	@Path("/verifyotp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verifyOtp(@QueryParam("viewerInfo") String userInfo){
		log.debug("START :: PhotoExhibitionRestControllerApplication.verifyOtp()");
		log.debug("userInfo :"+userInfo);
		JSONObject responseJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			JSONObject viewerInfoJsonObject = (JSONObject)JSONFactoryUtil.createJSONObject(userInfo);
			long viewerId = viewerInfoJsonObject.getLong(RestConstants.VIEWER_ID);
			String otpString = viewerInfoJsonObject.getString(RestConstants.OTP_STRING);
			log.info("Viewer Id :"+viewerId);
			log.info("otpString :"+otpString);
			responseJsonObject.put(RestConstants.STATUS_CODE, HttpStatus.OK.value());
			responseJsonObject.put(RestConstants.DATA, OtpHandler.verifyOtp(viewerId, otpString));
		} catch (Exception e) {
			log.error("Error ::"+e);
			e.printStackTrace();
		}
		log.debug("END :: PhotoExhibitionRestControllerApplication.verifyOtp()");
		return Response.status(HttpStatus.OK.value()).entity(responseJsonObject.toString()).build();
	}
	
	/**
	 * Adding like to child photo by viewer
	 * Request JSON Structure
		{
			"childId":123,
			"viewerId":362544,
			"isLike":true
		}
	 * @param likeInfo
	 * @return
	 */
	@GET
	@Path("/addlike")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLike(@QueryParam("likeInfo") String likeInfo) {
		log.debug("START :: PhotoExhibitionRestControllerApplication.addLike()");
		log.debug("likeInfo ::"+likeInfo);
		JSONObject responseJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			JSONObject likeInfoJson = JSONFactoryUtil.createJSONObject(likeInfo);
			long childId = likeInfoJson.getLong(RestConstants.CHILD_ID);
			long viewerId = likeInfoJson.getLong(RestConstants.VIEWER_ID);
			boolean isLiked = likeInfoJson.getBoolean(RestConstants.IS_LIKE);
			log.info("childId ::"+childId);
			log.info("viewerId ::"+viewerId);
			log.info("isLiked ::"+isLiked);
			responseJsonObject.put(RestConstants.STATUS_CODE, HttpStatus.OK.value());
			responseJsonObject.put(RestConstants.DATA,LikeHandler.manageLike(childId, viewerId, isLiked));
		} catch (Exception e) {
			log.error("Error ::"+e);
			e.printStackTrace();
		}
		log.debug("END :: PhotoExhibitionRestControllerApplication.addLike()");
		return Response.status(HttpStatus.OK.value()).entity(responseJsonObject.toString()).build();
	}
	/**
	 * Getting viewer data after otp verification
	 * param JSON structure
	 * {
	 * 	viewerId:123
	 * }
	 * @param viewerInfo
	 * @return
	 */
	@GET
	@Path("/get-data-after-otp-verification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDataAfterOtpVerification(@QueryParam("viewerInfo") String viewerInfo) {
		log.debug("START :: PhotoExhibitionRestControllerApplication.getDataAfterOtpVerification()");
		JSONObject responseJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			JSONObject viewerJsonObject = JSONFactoryUtil.createJSONObject(viewerInfo);
			long viewerId = viewerJsonObject.getLong(RestConstants.VIEWER_ID);
			log.info("viewerId :: "+viewerId);
			responseJsonObject.put(RestConstants.STATUS_CODE, HttpStatus.OK.value());
			responseJsonObject.put(RestConstants.DATA, ViewerDataHandler.getViewerData(viewerId));	
		} catch (Exception e) {
			log.error("Error ::"+e);
			e.printStackTrace();
		}
		log.debug("END :: PhotoExhibitionRestControllerApplication.getDataAfterOtpVerification()");
		return Response.status(HttpStatus.OK.value()).entity(responseJsonObject.toString()).build();
	}
}
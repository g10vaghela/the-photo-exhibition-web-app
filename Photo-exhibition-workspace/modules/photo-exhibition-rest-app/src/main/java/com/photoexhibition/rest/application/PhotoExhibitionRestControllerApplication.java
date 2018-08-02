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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.osgi.service.component.annotations.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.photoexhibition.rest.util.ContestUtil;

/**
 * @author jiten
 */
@ApplicationPath("/contest")
@Component(immediate = true, service = Application.class)
public class PhotoExhibitionRestControllerApplication extends Application {

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
	
/*	@GET
	@Path("/iscontestopen")
	@Consumes(MediaType.APPLICATION_JSON)
	public  String isContestOpen() {
		return ContestUtil.checkContestOpen().toJSONString();
	}*/

	@GET
	@Path("/iscontestopen")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response isContestOpen() {
		//System.out.println("PhotoExhibitionRestControllerApplication.isContestOpen1()");
		JSONObject responseJsonObject = JSONFactoryUtil.createJSONObject();
		responseJsonObject.put("statusCode", HttpStatus.OK.value());
		responseJsonObject.put("data", ContestUtil.checkContestOpen());
		return Response.status(HttpStatus.OK.value()).entity(responseJsonObject.toString()).build();
	}
}
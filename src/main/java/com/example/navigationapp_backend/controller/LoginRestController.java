package com.example.navigationapp_backend.controller;



import com.example.navigationapp_backend.dto.LoginRequestDto;
import com.example.navigationapp_backend.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginRestController {

	@Inject
	LoginService loginService;

	@Context
	UriInfo uriInfo;

	@Inject
	Logger logger;
	
	@POST
	@Path("log_in")
	public Response login(LoginRequestDto loginRequestDto) throws Exception{
		logger.info("Login request");
		boolean success = true;
		return Response.status(Response.Status.OK).header(AUTHORIZATION,"Bearer "+loginService.login(loginRequestDto,uriInfo)).entity(success).build();
	}
}

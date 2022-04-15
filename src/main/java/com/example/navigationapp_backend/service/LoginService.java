package com.example.navigationapp_backend.service;

import com.example.navigationapp_backend.ax.ApplicationState;
import com.example.navigationapp_backend.dto.LoginRequestDto;
import com.example.navigationapp_backend.entity.User;
import com.example.navigationapp_backend.repository.ICrudRepository;
import com.example.navigationapp_backend.repository.RepositoryQualifier;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class LoginService {

	@Inject
	@RepositoryQualifier(RepositoryQualifier.RepositoryQualifierType.USERREPOSITORY)
	private ICrudRepository<User> repository;

	@Inject
	private EncryptionService encryptionService;

	@Inject
	private ApplicationState applicationState;
	
	public String login(LoginRequestDto loginRequestDto, UriInfo uriInfo) throws WebApplicationException{
		User user = repository.getById(loginRequestDto.getAisId());
		if(user==null){
			throw new WebApplicationException(
					Response
							.status(Response.Status.BAD_REQUEST)
							.type(MediaType.TEXT_PLAIN)
							.entity("Invalid ais Id, try to log in again.")
							.build()
			);
		}
		boolean match =  encryptionService.checkPasswordMatch(loginRequestDto.getPassword(),user.getEncryptedPassword(),user.getSalt());
		if(!match){
			throw new WebApplicationException(
					Response
							.status(Response.Status.BAD_REQUEST)
							.type(MediaType.TEXT_PLAIN)
							.entity("Invalid password, try to log in again.")
							.build()
			);
		}
		applicationState.setAisId(loginRequestDto.getAisId());
		String token = encryptionService.generateToken(loginRequestDto.getAisId(),uriInfo);
		return token;
	}
}

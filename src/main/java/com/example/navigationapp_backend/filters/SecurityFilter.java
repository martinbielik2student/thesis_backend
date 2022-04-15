package com.example.navigationapp_backend.filters;

import com.example.navigationapp_backend.annotations.Security;
import com.example.navigationapp_backend.ax.ApplicationState;
import com.example.navigationapp_backend.service.EncryptionService;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Provider
@Priority(Priorities.AUTHORIZATION)
@Security
public class SecurityFilter implements ContainerRequestFilter {

    private final String BEARER="Bearer ";

    @Inject
    ApplicationState state;

    @Inject
    EncryptionService encryptionService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(AUTHORIZATION);
        if (authorizationHeader==null || !authorizationHeader.startsWith(BEARER)){
            throw new NotAuthorizedException("Authorization header not present");
        }
        String passedToken = authorizationHeader.substring(BEARER.length()).trim();
        Long userId = state.getAisId();

        try {
            Key key = encryptionService.generateKey(userId.toString());
            Jwts.parser().setSigningKey(key).parseClaimsJws(passedToken);
            SecurityContext securityContext = containerRequestContext.getSecurityContext();
            containerRequestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return ()-> Jwts.parser().setSigningKey(key).parseClaimsJws(passedToken).getBody().getSubject();
                }

                @Override
                public boolean isUserInRole(String s) {
                    return false;
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public String getAuthenticationScheme() {
                    return null;
                }
            });
        }catch (Exception e){
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

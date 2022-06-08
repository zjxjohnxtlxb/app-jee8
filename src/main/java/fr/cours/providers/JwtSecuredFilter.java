package fr.cours.providers;

import fr.cours.bean.JWTBean;
import fr.cours.conf.JwtSecured;
import fr.cours.utils.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

@Provider
@JwtSecured
@Priority(Priorities.AUTHENTICATION)
public class JwtSecuredFilter implements ContainerRequestFilter {
    private final static Logger logger = Logger.getLogger(JwtSecuredFilter.class.getName());
    @Inject
    private JWTBean jwtBean;

    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        logger.info(token);
        try {

            // Validate the token
            Key key = Helper.getJwtKey();
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    logger.info(claims.getBody().getSubject());
                    return () -> claims.getBody().getSubject();
                }

                @Override
                public boolean isUserInRole(String role) {
                    logger.info(claims.getBody().get("role", String.class));
                    return role.equals(claims.getBody().get("role", String.class));
                }

                @Override
                public boolean isSecure() {
                    try {
                        logger.info(jwtBean.getCurrentUserToken(claims.getBody().getId()).getToken());
                        logger.info(claims.getBody().getExpiration().toString());
                        logger.info(Date.from(Instant.now()).toString());
                        return jwtBean.getCurrentUserToken(claims.getBody().getId()).getToken().equals(token) && claims.getBody().getExpiration().compareTo(Date.from(Instant.now())) > 0;
                    } catch (Exception e){
                        return false;
                    }
                 }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });

        } catch (Exception e) {
            System.out.println(e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("please login !").build());
        }

    }

}
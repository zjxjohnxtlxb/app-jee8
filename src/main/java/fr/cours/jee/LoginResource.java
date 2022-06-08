package fr.cours.jee;

import fr.cours.bean.JWTBean;
import fr.cours.bean.UserBean;
import fr.cours.conf.JwtSecured;
import fr.cours.ressource.JWT;
import fr.cours.ressource.User;
import fr.cours.utils.Helper;
import io.jsonwebtoken.Jwts;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Path("user")
public class LoginResource {
    @Inject
    private UserBean userBean;

    @Inject
    private JWTBean jwtBean;

    @Context
    private UriInfo uriInfo;
    @Context
    SecurityContext securityContext;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response addUser(@NotNull User user) {
        if (user.getPassword() == null) {
            return Response.ok("password should not be empty").build();
        }
        try {
            if (userBean.addUser(user)) {
                return Response.ok(userBean.getCurrentUserByEmail(user.getEmail())).build();
            } else {
                return Response.ok("The email address is not available").build();
            }
        } catch (Exception e) {
            return Response.ok("Error, please check the information entered").build();
        }

    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response loginUser(User user) {
        if (userBean.checkPasswordUser(user)) {
            user = userBean.getCurrentUserByEmail(user.getEmail());
            JWT token = new JWT(user, tokenBuilder(user));
            if (jwtBean.getCurrentUserToken(user.getId().toString()) != null) {
                jwtBean.updateToken(token);
            } else {
                jwtBean.addToken(token);
            }
            return Response.ok(user + "\nlogin success !").header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken()).build();
        } else {
            return Response.ok("password or email isn't correct").build();
        }
    }

    @PUT
    @JwtSecured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("updatePwd")
    public Response updatePasswordUser(@NotNull User user) {
        if (unauthenticatedJwt()) {
            return unauthenticatedResponse();
        }
        if (user.getEmail() == null) {
            user.setEmail(securityContext.getUserPrincipal().getName());
        }
        if (userBean.updatePasswordUser(user)) {
            var updateUser = userBean.getCurrentUserByEmail(user.getEmail());
            return Response.ok("updated ! " + updateUser).build();
        } else {
            return Response.ok("no exist user !").build();
        }
    }

    @GET
    @JwtSecured
    @Produces(MediaType.TEXT_PLAIN)
    @Path("logout")
    public Response logoutUser() {
        if (unauthenticatedJwt()) {
            return unauthenticatedResponse();
        }
        var user = userBean.getCurrentUserByEmail(securityContext.getUserPrincipal().getName());
        if (user != null) {
            jwtBean.deleteToken(user);
            return Response.ok(user + "\nlogin out success !").build();
        } else {
            return Response.ok("no exist user !").build();
        }
    }

    @GET
    @JwtSecured
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login")
    public Response autoLoginUser() {
        if (unauthenticatedJwt()) {
            return unauthenticatedResponse();
        }
        var user = userBean.getCurrentUserByEmail(securityContext.getUserPrincipal().getName());
        if (user != null) {
            return Response.ok(user + "\nauto login in success !").build();
        } else {
            return Response.ok("no exist user !").build();
        }
    }

    private String tokenBuilder(User user) {

        Key key = Helper.getJwtKey();
        return Jwts.builder()
                .setSubject(user.getEmail()).setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setId(user.getId().toString())
                .claim("role", "user")
                .setExpiration(Date.from(Instant.now().plusSeconds(120L)))
                .signWith(key)
                .compact();
    }

    private boolean unauthenticatedJwt() {
        return !(securityContext.isSecure() && securityContext.isUserInRole("user")) && (jwtBean.getCurrentUserToken(userBean.getCurrentUserByEmail(securityContext.getUserPrincipal().getName()).getId().toString()) == null);
    }

    private Response unauthenticatedResponse() {
        return Response.status(Response.Status.UNAUTHORIZED).entity("unrecognized login information or login expired & Please login !").build();
    }
}

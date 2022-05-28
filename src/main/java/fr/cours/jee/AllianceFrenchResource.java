package fr.cours.jee;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cours.bean.AllianceFrenchBean;
import fr.cours.conf.JwtSecured;
import fr.cours.ressource.AllianceFrench;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("allianceFrench")
public class AllianceFrenchResource {
    @Inject
    private AllianceFrenchBean allianceFrenchBean;

    @Context
    private UriInfo uriInfo;
    @Context
    SecurityContext securityContext;

    @POST
    @JwtSecured
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response addAllianceFrench(@NotNull AllianceFrench allianceFrench) {
        if (unauthenticatedJwt()) {
            return unauthenticatedResponse();
        }

        try {
            if (allianceFrenchBean.addAllianceFrench(allianceFrench)) {
                return Response.ok(allianceFrenchBean.getAllianceFrenchByNom(allianceFrench.getNom())).build();
            } else {
                return Response.ok("Error, please check the information entered").build();
            }
        } catch (Exception e) {
            return Response.ok("Error, please check the information entered").build();
        }
    }

    @PUT
    @JwtSecured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("update")
    public Response updateAllianceFrench(@NotNull AllianceFrench allianceFrench) {
        if (unauthenticatedJwt()) {
            return unauthenticatedResponse();
        }

        if(allianceFrench.getNom() == null) {
            return Response.ok("please input nom !").build();
        }
        if (allianceFrenchBean.updateAllianceFrench(allianceFrench)) {
            var updateAllianceFrench = allianceFrenchBean.getAllianceFrenchByNom(allianceFrench.getNom());
            return Response.ok("updated ! " + updateAllianceFrench).build();
        } else {
            return Response.ok("no exist allianceFrench !").build();
        }
    }

    @DELETE
    @JwtSecured
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete/{nom}")
    public Response deleteAllianceFrench(@PathParam("nom") String nom) {
        if (unauthenticatedJwt()) {
            return unauthenticatedResponse();
        }

        if (allianceFrenchBean.deleteAllianceFrench(nom)) {
            return Response.ok("delete success ! " + nom).build();
        } else {
            return Response.ok("no exist allianceFrench !").build();
        }
    }

    @GET
    @JwtSecured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("filter/{column}")
    public Response filter(@PathParam("column") String column) throws IOException {
        if (unauthenticatedJwt()) {
            return unauthenticatedResponse();
        }
        List aFList = switch (column) {
            case "pays" -> allianceFrenchBean.getAllianceFrenchGroupByPays();
            case "zonegeo" -> allianceFrenchBean.getAllianceFrenchGroupByZonegeo();
            default -> {
                var list = new ArrayList();
                list.add("error column");
                yield list;
            }
        };
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, aFList);
        var result = new String(out.toByteArray());
        return Response.ok(result).build();
    }

    private boolean unauthenticatedJwt() {
        return !securityContext.isSecure() && !securityContext.isUserInRole("user");
    }

    private Response unauthenticatedResponse() {
        return Response.status(Response.Status.UNAUTHORIZED).entity("unrecognized login information or login expired & Please login !").build();
    }
}

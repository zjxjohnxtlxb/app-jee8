/*
 * @Date: 2022-05-19 12:08:03
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 20:42:19
 * @FilePath: /app-jee8/src/main/java/fr/cours/jee/AmongousseResource.java
 */
package fr.cours.jee;

import fr.cours.bean.CrewBean;
import fr.cours.ressource.CrewMember;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("amangousse")
public class AmongousseResource {
    @Inject
    private CrewBean crewBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembers() {
        return Response.ok(crewBean.getCrewMembers()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("demo/{name}")
    public Response getMemberDemo(@PathParam("name") String name) {
        List cmlist = crewBean.getCrewMemberDemo(name);
        if (cmlist.size() != 0) {
            return Response.ok(cmlist).build();
        } else {
            return Response.ok("no exist element !").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("members/{name}")
    public Response getMember(@PathParam("name") String name) {
        List cmlist = crewBean.getCrewMemberList(name);
        if (cmlist.size() != 0) {
            return Response.ok(cmlist).build();
        } else {
            return Response.ok("no exist element !").build();
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMember(@NotNull CrewMember crewMember) {
        if (crewBean.addMember(crewMember)) {
            return Response.ok(crewBean.getCrewMembers().size()).build();
        } else {
            return Response.ok("double element !").build();
        }
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{name}")
    public Response deleteMember(@PathParam("name") String name) {
        if (crewBean.deleteMember(name)) {
            return Response.ok(crewBean.getCrewMembers().size()).build();
        } else {
            return Response.ok("no exist element !").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response putMembers(@NotNull CrewMember crewMember) {
        if (crewBean.updateMember(crewMember)) {
            return Response.ok("updated ! " + crewBean.getCrewMembers().size()).build();
        } else {
            return Response.ok("no exist element !").build();
        }
    }

}

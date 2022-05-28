/*
 * @Date: 2022-05-19 09:11:24
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 12:46:37
 * @FilePath: /app-jee8/src/main/java/fr/cours/jee/SampleResource.java
 */
package fr.cours.jee;

import fr.cours.ressource.CrewMember;
import fr.cours.ressource.TestJsonRessource;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("sample")
public class SampleResource {
    @Inject
    @ConfigProperty(name = "message")
    private String message;

    private static List<CrewMember> crewMembers = new ArrayList<>(Arrays.asList(new CrewMember("test1", "test1"),
            new CrewMember("test2", "test2"), new CrewMember("test3", "test3")));

    @GET
    public Response message() {
        return Response.ok(message).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("titans")
    public Response getTitans() {
        // src: https://attaque-des-titans.fandom.com/fr/wiki/Titans
        String titans[] = { "Titan Colossal", "Titan Cuirassé", "Titan Assaillant", "Titan Féminin", "Titan Mâchoire",
                "Titan Bestial",
                "Titan Charrette",
                "Titan Marteau d'Armes", "Titan Originel" };
        return Response.ok(titans).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("test")
    public Response testPost(TestJsonRessource testJsonRessource) {
        if (testJsonRessource.getName() == null) {
            return Response.status(403).build();
        }
        return Response.ok("Posted " + testJsonRessource.getName() + ". Age: " + testJsonRessource.getAge()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("amangousse")
    public Response getAmangousse() {
        return Response.ok(crewMembers).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("amangousse")
    public Response postAmangousse(CrewMember crewMember) {
        if (crewMember.getName() == null) {
            return Response.status(403).build();
        }
        crewMembers.add(crewMember);
        
        return Response.ok("Posted " + crewMember.getName() + ". Age: " + crewMember.getJob()).build();
    }

    @DELETE
    @Path("amangousse/{name}")
    public Response deleteAmangousse(@PathParam("name") String name) {
        List<CrewMember> toDolist =  crewMembers.stream().filter(s->s.getName().equals(name)).collect(Collectors.toList());
        if (toDolist.size() == 0) {
            return Response.status(403).build();
        }
        crewMembers.removeAll(toDolist);
        return Response.ok("Deleted " + name + "in crewMembers").build();
 
    }
}
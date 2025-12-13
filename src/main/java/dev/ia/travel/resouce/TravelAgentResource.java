package dev.ia.travel.resouce;

import dev.ia.booking.tools.PackageExpert;
import dev.ia.configurations.security.SecurityContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/travel")
public class TravelAgentResource {

    @Inject
    PackageExpert expert;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String ask(String question, @HeaderParam("X-User-Name") String userName){
        if (userName != null && !userName.isEmpty()){
            try{
                SecurityContext.setCurrentUser(userName);
                System.out.println(question);
                return expert.chat(userName, question);
            } finally {
                SecurityContext.clearCurrentUser();
            }
        } else {
            return "Usuário precisa estar autenticado!";
        }

    }
}

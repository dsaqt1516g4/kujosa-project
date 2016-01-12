package edu.upc.eetac.dsa.kujosa;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URISyntaxException;

/**
 * Created by juan on 16/12/15.
 */
@Path("documents")

public class DocumentResource {
        @Context
        private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_DOCUMENT)
    public Response createSting(@FormParam("name") String name, @FormParam("description") String description, @Context UriInfo uriInfo) throws URISyntaxException {
    return null;
    }
    }


package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.dao.StingDAO;
import edu.upc.eetac.dsa.kujosa.dao.StingDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.Comentari;
import edu.upc.eetac.dsa.kujosa.entity.StingCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by sergio on 9/09/15.
 */
@Path("stings")
public class StingResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_STING)
    public Response createSting(@FormParam("subject") String subject, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if (subject == null || content == null)
            throw new BadRequestException("all parameters are mandatory");
        StingDAO stingDAO = new StingDAOImpl();
        Comentari comentari = null;
        AuthToken authToken = null;
        try {
            comentari = stingDAO.createSting(securityContext.getUserPrincipal().getName(), subject, content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comentari.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_STING).entity(comentari).build();
    }

    @GET
    @Produces(BeeterMediaType.BEETER_STING_COLLECTION)
    public StingCollection getStings(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        StingCollection stingCollection = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            stingCollection = stingDAO.getStings(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(BeeterMediaType.BEETER_STING)
    public Response getSting(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Comentari comentari = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            comentari = stingDAO.getStingById(id);
            if (comentari == null)
                throw new NotFoundException("Comentari with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(comentari.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(comentari).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(BeeterMediaType.BEETER_STING)
    @Produces(BeeterMediaType.BEETER_STING)
    public Comentari updateSting(@PathParam("id") String id, Comentari comentari) {
        if (comentari == null)
            throw new BadRequestException("entity is null");
        if (!id.equals(comentari.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if (!userid.equals(comentari.getUserid()))
            throw new ForbiddenException("operation not allowed");

        StingDAO stingDAO = new StingDAOImpl();
        try {
            comentari = stingDAO.updateSting(id, comentari.getSubject(), comentari.getContent());
            if (comentari == null)
                throw new NotFoundException("Comentari with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return comentari;
    }

    @Path("/{id}")
    @DELETE
    public void deleteSting(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        StingDAO stingDAO = new StingDAOImpl();
        try {
            String ownerid = stingDAO.getStingById(id).getUserid();
            if (!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if (!stingDAO.deleteSting(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}

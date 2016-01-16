package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.DocumentDAO;
import edu.upc.eetac.dsa.kujosa.dao.DocumentDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URISyntaxException;
import java.sql.SQLException;

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




    @Path("/{id}")
    @GET
    @Produces(KujosaMediaType.KUJOSA_STING)
    public Response getSting(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Document document = null;
        DocumentDAO documentDAO = new DocumentDAOImpl();
        try {
            document = documentDAO.getDocumentById(id);
            if (document == null)
                throw new NotFoundException("Sting with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(document.getLastModified()));

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
            rb = Response.ok(document).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }



    @Path("/{id}")
    @PUT
    @Consumes(KujosaMediaType.KUJOSA_DOCUMENT)
    @Produces(KujosaMediaType.KUJOSA_DOCUMENT)
    public Document updateDocument(@PathParam("id") String id, Document document) {
        if (document == null)
            throw new BadRequestException("entity is null");
        if (!id.equals(document.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if (!userid.equals(document.getUserid()))
            throw new ForbiddenException("operation not allowed");

        DocumentDAO documentDAO = new DocumentDAOImpl();
        try {
            document = documentDAO.updateDocument(id, document.getName(), document.getPath());
            if (document == null)
                throw new NotFoundException("Document with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return document;
    }

    @Path("/{id}")
    @DELETE
    public void deleteSting(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        DocumentDAO documentDAO = new DocumentDAOImpl();
        try {
            String ownerid = documentDAO.getDocumentById(id).getUserid();
            if (!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if (!documentDAO.deleteDocument(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}

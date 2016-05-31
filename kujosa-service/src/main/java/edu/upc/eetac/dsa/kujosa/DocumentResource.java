package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.DocumentDAO;
import edu.upc.eetac.dsa.kujosa.dao.DocumentDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.Document;
import edu.upc.eetac.dsa.kujosa.entity.DocumentCollection;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 */
@Path("documents")

public class DocumentResource {
    @Context
    private SecurityContext securityContext;

    /*** OK ***/

    @POST
    @Consumes (MediaType.MULTIPART_FORM_DATA)
    @Produces(KujosaMediaType.KUJOSA_DOCUMENT)
    public Response createDocument(@FormDataParam("name") String name, @FormDataParam("description") String description,
                                 @FormDataParam("document") InputStream file, @FormDataParam("document") FormDataContentDisposition fileDetail, @Context UriInfo uriInfo) throws URISyntaxException {
        if (name == null || file == null)
            throw new BadRequestException("all parameters are mandatory");
        DocumentDAO documentDAO = new DocumentDAOImpl();
        Document document = null;
        AuthToken authToken = null;
        try {
            document = documentDAO.createDocument(securityContext.getUserPrincipal().getName(), name, description, file);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + document.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_DOCUMENT).entity(document).build();
    }

    /*** OK ***/

    @Path("/{id}")
    @GET
    @Produces(KujosaMediaType.KUJOSA_DOCUMENT)
    public Response getDocument(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Document document = null;
        DocumentDAO documentDAO = new DocumentDAOImpl();
        try {
            document = documentDAO.getDocumentById(id);
            if (document == null)
                throw new NotFoundException("Document with id = " + id + " doesn't exist");

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

    /*** OK ***/

    @GET
    @Produces(KujosaMediaType.KUJOSA_DOCUMENT_COLLECTION)
    public DocumentCollection getDocuments(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        DocumentCollection documentCollection = null;
        DocumentDAO documentDAO = new DocumentDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            documentCollection = documentDAO.getDocuments(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return documentCollection;
    }

    /*** OK ***/

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_DOCUMENT)
    public Document updateDocument(@PathParam("id") String id, @FormParam("name") String name,  @FormParam("description") String description) {
        Document document=null;
        if (id == null)
            throw new BadRequestException("entity is null");

        DocumentDAO documentDAO = new DocumentDAOImpl();
        try {
           document = documentDAO.updateDocument(id, name, description);
            if (document == null)
                throw new NotFoundException("Document with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return document;
    }

    /*** OK ***/

    @Path("/{id}")
    @DELETE
    public void deleteDocument(@PathParam("id") String id) {
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

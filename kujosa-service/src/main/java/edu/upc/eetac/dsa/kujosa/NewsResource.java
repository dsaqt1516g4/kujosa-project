package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.NewsDAO;
import edu.upc.eetac.dsa.kujosa.dao.NewsDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.UserDAO;
import edu.upc.eetac.dsa.kujosa.dao.UserDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.NewsCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * +-------------------------------------+
 * |           KUJOSA PROJECT            |
 * +-------------------------------------+
 */
@Path("news")
public class NewsResource {
    @Context
    private SecurityContext securityContext;

    /*** OK ***/

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_NEWS)
    public Response createNews(@FormParam("headline") String headline, @FormParam("body") String body, @Context UriInfo uriInfo) throws URISyntaxException {
        if ((headline==null)   ||(body==null))
            throw new BadRequestException("all parameters are mandatory");
        NewsDAO newsDAO = new NewsDAOImpl();
        News news = null;
        AuthToken authToken = null;
        try {
            news = newsDAO.createNews(securityContext.getUserPrincipal().getName(), headline, body);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + news.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_NEWS).entity(news).build();
        }

    /*** OK ***/

    @GET
    @Produces(KujosaMediaType.KUJOSA_NEWS_COLLECTION)
    public NewsCollection getNews(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        NewsCollection newsCollection = null;
        NewsDAO newsDAO = new NewsDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            newsCollection = newsDAO.getNews(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return newsCollection;
    }

    /*** OK ***/

    @Path("/{id}")
    @GET
    @Produces(KujosaMediaType.KUJOSA_NEWS)
    public Response getNews(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        News news = null;
        NewsDAO newsDAO = new NewsDAOImpl();
        try {
            news = newsDAO.getNewsById(id);
            if (news == null)
                throw new NotFoundException("News with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(news.getLastModified()));

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
            rb = Response.ok(news).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    /*** OK ***/

    @Path("/{id}")
    @PUT
    @Consumes({KujosaMediaType.KUJOSA_NEWS})
    @Produces(KujosaMediaType.KUJOSA_NEWS)
    public News updateNews(@PathParam("id") String id, News news) {
        if (news == null)
            throw new BadRequestException("entity is null");
        if (!id.equals(news.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();

        NewsDAO newsDAO = new NewsDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        try {
            if (userDAO.isAdmin(userid)) {
                news = newsDAO.updateNews(id, news.getHeadline(), news.getBody());
            }
            else  {
                throw new ForbiddenException("No està permès");
            }
            if (news == null)
                throw new NotFoundException("News with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return news;
    }

    /*** OK ***/
    @Path("/{id}")
    @DELETE
    public void deleteNews(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        UserDAO userDAO = new UserDAOImpl();
        NewsDAO news = new NewsDAOImpl();
        try {
            String ownerid = news.getNewsById(id).getUserid();
            if (!userDAO.isAdmin(userid) || !userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if (!news.deleteNews(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}



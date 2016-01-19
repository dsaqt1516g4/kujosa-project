package edu.upc.eetac.dsa.kujosa;

import com.sun.org.apache.bcel.internal.generic.NEW;
import edu.upc.eetac.dsa.kujosa.dao.NewsDAO;
import edu.upc.eetac.dsa.kujosa.dao.NewsDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.UserDAO;
import edu.upc.eetac.dsa.kujosa.dao.UserDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.NewsCollection;
import edu.upc.eetac.dsa.kujosa.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * +-------------------------------------+
 * |           KUJOSA PROJECT            |
 * +-------------------------------------+
 * <p/>
 * READY FOR TEST
 * Crete TEST ->OK!
 * getNews->OK!
 * NewsCollection->NOK¡
 */
@Path("news")
public class NewsResource {
    @Context
    private SecurityContext securityContext;
//TO REPAIR

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_NEWS)
    public Response createNews(@FormParam("userid") String username, @FormParam("headline") String headline, @FormParam("body") String body, @Context UriInfo uriInfo) throws URISyntaxException {
        if ((headline == null) || (body == null))
            throw new BadRequestException("all parameters are mandatory");
        String userid = securityContext.getUserPrincipal().getName();
        NewsDAO newsDAO = new NewsDAOImpl();
        News news = null;
        UserDAO us = new UserDAOImpl();

        try {
            if (us.isAdmin(userid)) {
                news = newsDAO.createNews(username, headline, body);
            } else {
                throw new ForbiddenException("operation not allowed");

            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + news.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_NEWS).entity(news).build();
    }

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

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_NEWS)
    public News updateNews(@PathParam("id") String id, @FormParam("title") String header, @FormParam("body") String cuerpaso) {
        String usrID = securityContext.getUserPrincipal().getName();
        NewsDAO newsDAO = new NewsDAOImpl();
        UserDAO us = new UserDAOImpl();
        News news = null;
        try {
            if (us.isAdmin(usrID)) {
                news = newsDAO.getNewsById(id);
            } else
                throw new ForbiddenException("Not permitted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (id == null) {
            throw new BadRequestException("News ID is null");

        }

        if (header == null) {
            header = news.getHeadline();
        }
        if (cuerpaso == null) {
            cuerpaso = news.getBody();
        }
        String userid = securityContext.getUserPrincipal().getName();

        try {
            if (us.isAdmin(userid)) {
                news = newsDAO.updateNews(id, header, cuerpaso);
                if (news == null)
                    throw new NotFoundException("News with id = " + id + " doesn't exist");

                return news;
            } else {
                throw new ForbiddenException("operation not allowed");

            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }


    }


    @Path("/{id}")
    @DELETE
    public void deleteNews(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        UserDAO us = new UserDAOImpl();
        NewsDAO news = new NewsDAOImpl();
        try {
            String ownerid = news.getNewsById(id).getUserid();
            if (!us.isAdmin(userid))
                throw new ForbiddenException("operation not allowed");
            if (!news.deleteNews(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}



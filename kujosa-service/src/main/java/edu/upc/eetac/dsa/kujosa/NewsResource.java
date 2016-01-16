package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.NewsDAO;
import edu.upc.eetac.dsa.kujosa.dao.NewsDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.Newscollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by kushal on 21/12/15.
 */
@Path("news")
public class NewsResource {
        @Context
        private SecurityContext securityContext;

        @POST
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        @Produces(KujosaMediaType.KUJOSA_NEWS)
        public Response createNews(@FormParam("userid") int userid, @FormParam("headline") String headline, @FormParam("body") String body,@Context UriInfo uriInfo) throws URISyntaxException {
            if ((userid==0 ) ||  (headline==null)   ||(body==null))
                throw new BadRequestException("all parameters are mandatory");
            NewsDAO newsDAO = new NewsDAOImpl();
            News news = null;
            try {
                news = newsDAO.createNews(userid, headline, body);
            } catch (SQLException e) {
                throw new InternalServerErrorException();
            }
            URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + news.getHeadline());
            return Response.created(uri).type(KujosaMediaType.KUJOSA_NEWS).entity(news).build();
        }

        @GET
        @Produces(KujosaMediaType.KUJOSA_NEWS_COLLECTION)
        public Newscollection getStings(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
            Newscollection newsCollection = null;
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
        public Response news(@PathParam("id") int userid, @Context Request request) {
            // Create cache-control
            CacheControl cacheControl = new CacheControl();
            News news = null;
            NewsDAO newsDAO = new NewsDAOImpl();
            try {
                news = newsDAO.getNewsbyuser(userid);
                if (news == null)
                    throw new NotFoundException("Sting with id = " + userid + " doesn't exist");

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
        @Path("/{headline}")
        @PUT
        @Consumes(KujosaMediaType.KUJOSA_NEWS)
        @Produces(KujosaMediaType.KUJOSA_NEWS)
        public News updateSting(@PathParam("headline") String headline, News news) {
            if (news == null)
                throw new BadRequestException("news is null");
            if (!headline.equals(news.getHeadline()))
                throw new BadRequestException("path parameter id and entity parameter id doesn't match");

            String userid = securityContext.getUserPrincipal().getName();
            if (!userid.equals(news.getUserid()))
                throw new ForbiddenException("operation not allowed");

            NewsDAO newsDAO = new NewsDAOImpl();
            try {
                String hl=news.getHeadline();
                news = newsDAO.updateNews(Integer.getInteger(userid), hl, news.getBody());
                if (news == null)
                    throw new NotFoundException("News with headline = " + hl + " doesn't exist");
            } catch (SQLException e) {
                throw new InternalServerErrorException();
            }
            return news;
        }
//check
        @Path("/{headline}")
        @DELETE
        public void deleteSting(@PathParam("headline") String id) {
            String userid = securityContext.getUserPrincipal().getName();
            NewsDAO as = new NewsDAOImpl();
            try {
                int ownerid = as.getNewsbyuser(Integer.getInteger(userid)).getUserid();
                if (!userid.equals(ownerid))
                    throw new ForbiddenException("operation not allowed");
                if (!as.deleteNews(id))
                    throw new NotFoundException("User with headline = " + id + " doesn't exist");
            } catch (SQLException e) {
                throw new InternalServerErrorException();
            }
        }
    }



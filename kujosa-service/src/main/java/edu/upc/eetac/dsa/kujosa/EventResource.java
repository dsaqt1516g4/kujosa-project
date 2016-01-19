package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.EventDAO;
import edu.upc.eetac.dsa.kujosa.dao.EventDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.UserDAO;
import edu.upc.eetac.dsa.kujosa.dao.UserDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.Event;
import edu.upc.eetac.dsa.kujosa.entity.EventCollection;
import edu.upc.eetac.dsa.kujosa.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *
 * READY FOR TEST
 */
@Path("events")
public class EventResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_EVENT)
    public Response createEvents(@FormParam("titol") String titol, @FormParam("text") String text,
                                 @FormParam("latitud") long lat, @FormParam("longitud") long lon,
                                @FormParam("ratio") int ratio, @FormParam("startdate") long startdate,
                                @FormParam("enddate") long enddate,@FormParam("userid") String username,
                                @Context UriInfo uriInfo) throws URISyntaxException {
        if (titol == null || text == null)
            throw new BadRequestException("Title and text are mandatories");
        EventDAO eventDAO = new EventDAOImpl();
        Event event = null;
        AuthToken authToken = null;
        UserDAO us = new UserDAOImpl();
        String id_usr= securityContext.getUserPrincipal().getName();


        try {
            if (us.isAdmin(id_usr)) {
                event = eventDAO.createEvent(username, titol, text, lat, lon, startdate, enddate);
            }
            else
            {
                throw new ForbiddenException("NOt permitted operation ");
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + event.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_EVENT).entity(event).build();
    }

    @GET
    @Produces(KujosaMediaType.KUJOSA_EVENT_COLLECTION)
    public EventCollection getEvents(@QueryParam("length") int length,
                                     @QueryParam("before") long before, @QueryParam("after") long after) {
        EventCollection eventCollection = null;
        EventDAO eventDAO = new EventDAOImpl();
        try {
            eventCollection = eventDAO.getEvents(length, before,after);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return eventCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(KujosaMediaType.KUJOSA_EVENT)
    public Response getEvent(@PathParam("id") String id, @Context Request request){
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Event event = null;
        EventDAO eventDAO = new EventDAOImpl();
        try {
            event = eventDAO.getEvent(id);
            if (event == null)
                throw new NotFoundException("Event with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(event.getLastModified()));

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
            rb = Response.ok(event).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(KujosaMediaType.KUJOSA_EVENT)
    @Produces(KujosaMediaType.KUJOSA_EVENT)
    public Event updateEvent(@PathParam("id") String id, Event event) {
        UserDAO us = new UserDAOImpl();

        if (event == null)
            throw new BadRequestException("entity is null");
        if (!id.equals(event.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");
        boolean ok;
        String user_id = securityContext.getUserPrincipal().getName();
        try {
            ok = us.isAdmin(user_id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        if (!ok)
            throw new ForbiddenException("operation not allowed");

        EventDAO eventDAO = new EventDAOImpl();
        try {
            event = eventDAO.updateEvent(id, event.getTitol(), event.getText(), event.getStartDate(), event.getEndDate());
            if (event == null)
                throw new NotFoundException("Event with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return event;
    }

    @Path("/{id}")
    @DELETE
    public void deleteEvent(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        EventDAO eventDAO = new EventDAOImpl();
        UserDAO us = new UserDAOImpl();
        try {

            if (!us.isAdmin(userid))
                throw new ForbiddenException("operation not allowed");
            if (!eventDAO.deleteEvent(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}

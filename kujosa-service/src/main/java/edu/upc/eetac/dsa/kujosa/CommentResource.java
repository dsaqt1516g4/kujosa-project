package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.CommentDAO;
import edu.upc.eetac.dsa.kujosa.dao.CommentDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.StingDAO;
import edu.upc.eetac.dsa.kujosa.dao.StingDAOImpl;
import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.Comment;
import edu.upc.eetac.dsa.kujosa.entity.CommentCollection;
import edu.upc.eetac.dsa.kujosa.entity.Sting;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

/**
 * Created by juan on 14/12/15.
 */
@Path("comments")
public class CommentResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_API_COMMENT)
    public Response createComment(@FormParam("subject") String subject, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if (subject == null || content == null)
            throw new BadRequestException("all parameters are mandatory");
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment = null;
        AuthToken authToken = null;
        try {
            comment = commentDAO.createComment(securityContext.getUserPrincipal().getName(), subject, content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        /* URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_STING).entity(comment).build(); */
        return null;

    }


}

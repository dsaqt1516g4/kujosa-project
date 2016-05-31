package edu.upc.eetac.dsa.kujosa;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import edu.upc.eetac.dsa.kujosa.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.UserDAO;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.dao.UserAlreadyExistsException;
import edu.upc.eetac.dsa.kujosa.dao.UserDAOImpl;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.PathParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
*/
@Path("users")
public class UserResource {
    @Context
    private SecurityContext securityContext;

    /*** OK ****/

    @POST
    @Consumes (MediaType.MULTIPART_FORM_DATA)
    @Produces(KujosaMediaType.KUJOSA_AUTH_TOKEN)
    public Response registerUser(@FormDataParam("loginid") String loginid,  @FormDataParam("email") String email,
                                 @FormDataParam("password") String password, @FormDataParam("fullname") String fullname,
                                 @FormDataParam("image") InputStream image, @FormDataParam("image") FormDataContentDisposition fileDetail, @Context UriInfo uriInfo) throws URISyntaxException {
      System.out.println("username :"+loginid+" email :"+email+" password : "+password+" name :"+fullname);

       if(loginid == null || password == null || email == null || fullname == null)
            throw new BadRequestException("S'han d'emplenar tots els camps");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authToken = null;
        try{
            user = userDAO.createUser(loginid, fullname, email, password, image);
            authToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("Username already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }catch(NullPointerException e) {
            System.out.println(e.toString());
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_AUTH_TOKEN).entity(authToken).build();
    }

    /*** OK ***/

    @Path("/{id}")
    @GET
    @Produces(KujosaMediaType.KUJOSA_USER)
    public User getUser(@PathParam("id") String id) {
        User user = null;
        try {
            user = (new UserDAOImpl()).getUserById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(user == null)
            throw new NotFoundException("User with id = "+ id +" doesn't exist");
        return user;
    }

    /*** OK ***/

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(KujosaMediaType.KUJOSA_USER)
    public User updateUser(@PathParam("id") String id, @FormDataParam("email") String email,
                           @FormDataParam("fullname") String fullname,
                           @FormDataParam("image") InputStream image, @FormDataParam("image") FormDataContentDisposition fileDetail, @Context UriInfo uriInfo) {

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("operation not allowed");

        UserDAO userDAO = new UserDAOImpl();
        User user=null;
        try {
            user = userDAO.updateProfile(userid,email,fullname,image);
            if(user==null){
                throw new NotFoundException("User with id = "+id+" doesn't exist");
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return user;

    }

    /*** OK ***/

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") String id, @Context UriInfo uriInfo) throws SQLException {
        String userid = securityContext.getUserPrincipal().getName();

        //if(!userid.equals(id) ) {
        //    throw new ForbiddenException("Operation not allowed");
        //}

        UserDAO userDAO = new UserDAOImpl();
        try {
            boolean ok = userDAO.isAdmin(userid);
            if(ok==false)
                throw new ForbiddenException("No ets administrador");
            if(!userDAO.deleteUser(id))
                throw new NotFoundException("User with Username = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}
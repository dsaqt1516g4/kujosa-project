package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.dao.UserAlreadyExistsException;
import edu.upc.eetac.dsa.kujosa.dao.UserDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

 /**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -registerUser
 *     -getUser
 *     -updateUser
 *     -deleteUser
 */
@Path("users")
public class UserResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_AUTH_TOKEN)
    public Response registerUser(@FormParam("username") String username,  @FormParam("email") String email, @FormParam("password") String password, @FormParam("nombre") String fullname, @Context UriInfo uriInfo) throws URISyntaxException {
      System.out.println("username :"+username+" email :"+email+" password : "+password+" name :"+fullname);

       if(username == null || password == null || email == null || fullname == null)
            throw new BadRequestException("S'han de plenar tots els camps 222");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authToken = null;
        try{
            user = userDAO.createUser(username, fullname, email, password);
            authToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("Username already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_AUTH_TOKEN).entity(authToken).build();
    }

    @Path("/{id}")
    @GET
    @Produces(KujosaMediaType.KUJOSA_USER)
    public User getUser(@PathParam("username") String username) {
        User user = null;
        try {
            user = (new UserDAOImpl()).getUserByLoginid(username);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(user == null)
            throw new NotFoundException("User with Username = "+ username +" doesn't exist");
        return user;
    }

    @Path("/{id}")
    @PUT
    @Consumes(KujosaMediaType.KUJOSA_USER)
    @Produces(KujosaMediaType.KUJOSA_USER)
    public void updateUser(@PathParam("username") String username,@FormParam("correu") String correu, @FormParam("pass") String pass,@FormParam("image") String image) {
        if(username == null)
            throw new BadRequestException("entity is null");
        UserDAO userDAO = new UserDAOImpl();

        try {
            userDAO.updateUser(username,correu,pass,image);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") String id){
        System.out.println("El usuario :"+id+" se fue a la puta.");
        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("Operation not allowed");
        UserDAO userDAO = new UserDAOImpl();
        try {
            if(!userDAO.deleteUser(id))
                throw new NotFoundException("User with USername = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}

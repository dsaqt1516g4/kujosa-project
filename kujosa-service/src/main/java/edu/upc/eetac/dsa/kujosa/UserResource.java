package edu.upc.eetac.dsa.kujosa;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import edu.upc.eetac.dsa.kujosa.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.UserDAO;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.dao.UserAlreadyExistsException;
import edu.upc.eetac.dsa.kujosa.dao.UserDAOImpl;




import javax.imageio.ImageIO;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.FormParam;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -registerUser TEST -> OK!
 *     -getUser TEST -> OK!
 *     -updateUser TEST -> To REPAIR!
 *     delete user TEST--?
 */
@Path("users")
public class UserResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes (MULTIPART_FORM_DATA)
    @Produces(KujosaMediaType.KUJOSA_AUTH_TOKEN)
    public Response registerUser(@FormDataParam("username") String username,  @FormDataParam("email") String email,
                                 @FormDataParam("password") String password, @FormDataParam("nombre") String fullname,
                                 @FormDataParam("imagen") InputStream imagen, @FormDataParam("imagen") FormDataContentDisposition fileDetail, @Context UriInfo uriInfo) throws URISyntaxException {
      System.out.println("username :"+username+" email :"+email+" password : "+password+" name :"+fullname);

       if(username == null || password == null || email == null || fullname == null)
            throw new BadRequestException("S'han de plenar tots els camps 222");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authToken = null;
        try{
            user = userDAO.createUser(username, fullname, email, password, imagen);
            authToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("Username already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }catch(NullPointerException e) {
            System.out.println(e.toString());
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(KujosaMediaType.KUJOSA_AUTH_TOKEN+username).entity(authToken).build();
    }
     private UUID writeAndConvertImage(InputStream file) {
         BufferedImage image = null;
         try {
             image = ImageIO.read(file);

         } catch (IOException e) {
             throw new InternalServerErrorException("Something has been wrong when reading the file.");
         }
         UUID uuid = UUID.randomUUID();
         String filename = uuid.toString() + ".png";

         try {
             PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("kujosa");
             ImageIO.write(image, "png", new File(prb.getString("uploadFolder") + filename));
         } catch (IOException e) {
             throw new InternalServerErrorException("Something has been wrong when converting the file.");
         }

         return uuid;
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

    @Path("/{username}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Consumes(KujosaMediaType.KUJOSA_USER)
    @Produces(KujosaMediaType.KUJOSA_USER)
    public void updateUser(@PathParam("username") String username,@FormParam("correu") String correu, @FormParam("pass") String pass,@FormParam("image") String image) {
       System.out.println("El user : "+username+" correu : "+correu+" pas :  "+pass+"image : "+image);

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
    public void deleteUser(@PathParam("id") String id, @Context UriInfo uriInfo){
        //System.out.println("El usuario :"+id+" se fue a la puta.");
        String userid = securityContext.getUserPrincipal().getName();

        User us =getUser(userid);
        //Falta comprobar
        boolean ok = us.isAdmin();
        if(!userid.equals(id) ) {
            throw new ForbiddenException("Operation not allowed");
        }
         else if(!ok){
            throw new ForbiddenException("Operation not allowed");
        }

        UserDAO userDAO = new UserDAOImpl();
        try {
            if(!userDAO.deleteUser(id))
                throw new NotFoundException("User with Username = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}

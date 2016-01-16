package edu.upc.eetac.dsa.kujosa;

import edu.upc.eetac.dsa.kujosa.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.dao.AuthTokenDAO;
import edu.upc.eetac.dsa.kujosa.dao.UserDAOImpl;
import edu.upc.eetac.dsa.kujosa.dao.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.SQLException;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 */
@Path("login")
public class LoginResource {
    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(KujosaMediaType.KUJOSA_AUTH_TOKEN)
    public AuthToken login(@FormParam("username") String loginid, @FormParam("password") String password) {
        if(loginid == null || password == null)
            throw new BadRequestException("all parameters are mandatory");

        User user = null;
        AuthToken authToken = null;
        try{
            System.out.println("Login attempt:"+loginid+" com el pass:"+password);
            UserDAO userDAO = new UserDAOImpl();
            user = userDAO.getUserByLoginid(loginid);
            if(user == null)
                throw new BadRequestException("loginid " + loginid + " not found.");
            if(!userDAO.checkPassword(user.getId(), password))
                throw new BadRequestException("incorrect password");

            AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
            authTokenDAO.deleteToken(user.getId());
            authToken = authTokenDAO.createAuthToken(user.getId());
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        return authToken;
    }

    @DELETE
    public void logout(){
        String userid = securityContext.getUserPrincipal().getName();
        AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
        try {
            authTokenDAO.deleteToken(userid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}

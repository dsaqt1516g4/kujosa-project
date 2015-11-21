package edu.upc.eetac.dsa.kujosa.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import javax.ws.rs.container.ContainerRequestContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by sergio on 8/09/15.
 */
public class Authorized {
    private static Authorized instance;
    private List<AuthorizedResource> authorizedResourcesList;

    private Authorized() throws IOException {
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("authorized.json");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        authorizedResourcesList = objectMapper.readValue(in, typeFactory.constructCollectionType(List.class, AuthorizedResource.class));
    }

    public static Authorized getInstance() throws IOException {
        if (instance == null)
            instance = new Authorized();
        return instance;
    }

    public boolean isAuthorized(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();
        if(method.equals("OPTIONS"))
            return true;
        if(path.isEmpty() && method.equals("GET") && requestContext.getHeaderString("X-Auth-Token") == null)
            return true;
        for(AuthorizedResource r : authorizedResourcesList){
            if(r.getPattern().matcher(path).matches() && r.getMethods().contains(method) )
                return true;
        }
        return false;
    }

    /*public static void main(String[] args) {
        try {
            boolean b = Authorized.getInstance().isAuthorized("myresource", "GET");
            System.out.println(b);
            b = Authorized.getInstance().isAuthorized("users", "POST");
            System.out.println(b);
            b = Authorized.getInstance().isAuthorized("users/59185BD556B611E58F194851B7317E6E", "GET");
            System.out.println(b);
            b = Authorized.getInstance().isAuthorized("stings", "GET");
            System.out.println(b);
            b = Authorized.getInstance().isAuthorized("stings/59185BD556B611E58F194851B7317E6E", "GET");
            System.out.println(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

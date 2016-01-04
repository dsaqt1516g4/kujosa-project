package edu.upc.eetac.dsa.kujosa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.kujosa.KujosaMediaType;
import edu.upc.eetac.dsa.kujosa.UserResource;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 14/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCollection {
    @InjectLinks({
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE,
                    rel = "create-user", title = "Create User", type = KujosaMediaType.KUJOSA_USER, method = "createUser")
    })
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }
}


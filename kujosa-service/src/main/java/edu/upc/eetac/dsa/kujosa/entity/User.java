package edu.upc.eetac.dsa.kujosa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.kujosa.BeeterRootAPIResource;
import edu.upc.eetac.dsa.kujosa.LoginResource;
import edu.upc.eetac.dsa.kujosa.StingResource;
import edu.upc.eetac.dsa.kujosa.UserResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by sergio on 7/09/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @InjectLinks({
            @InjectLink(resource = BeeterRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-stings", title = "Current stings"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-sting", title = "Create sting", type=MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "self user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.id}"))
    })
    private List<Link> links;
    private String id;
    private String loginid;
    private String email;
    private String fullname;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

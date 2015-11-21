package edu.upc.eetac.dsa.kujosa.entity;

import edu.upc.eetac.dsa.kujosa.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by sergio on 14/09/15.
 */
public class BeeterRootAPI {
    @InjectLinks({
            @InjectLink(resource = BeeterRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "Beeter Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "login", title = "Login", type= BeeterMediaType.BEETER_AUTH_TOKEN),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-stings", title = "Current stings", type= BeeterMediaType.BEETER_STING_COLLECTION),
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-user", title = "Register", type= BeeterMediaType.BEETER_AUTH_TOKEN),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout", condition="${!empty resource.userid}"),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-sting", title = "Create sting", condition="${!empty resource.userid}", type=BeeterMediaType.BEETER_STING),
            @InjectLink(resource = UserResource.class, method="getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", condition="${!empty resource.userid}", type= BeeterMediaType.BEETER_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))
    })

    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}

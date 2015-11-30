package edu.upc.eetac.dsa.kujosa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.kujosa.BeeterRootAPIResource;
import edu.upc.eetac.dsa.kujosa.LoginResource;
import edu.upc.eetac.dsa.kujosa.StingResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 7/09/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StingCollection {
    @InjectLinks({
            @InjectLink(resource = BeeterRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-comentaris", title = "Current comentaris"),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-comentaris", title = "Current comentaris"),
            @InjectLink(resource = StingResource.class, method = "getComentaris", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer comentaris", bindings = {@Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = StingResource.class, method = "getComentaris", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older comentaris", bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Comentari> comentaris = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Comentari> getComentaris() {
        return comentaris;
    }

    public void setComentaris(List<Comentari> comentaris) {
        this.comentaris = comentaris;
    }
}

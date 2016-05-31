package edu.upc.eetac.dsa.kujosa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.kujosa.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Kushal on 23/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class News {
    //@InjectLinks({
    //        @InjectLink(resource = KujosaRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
    //        @InjectLink(resource = NewsResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-news", title = "Current news"),
    //        @InjectLink(resource = NewsResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-news", title = "Create news", type= MediaType.APPLICATION_FORM_URLENCODED),
    //        @InjectLink(resource = NewsResource.class, method = "getArticle", style = InjectLink.Style.ABSOLUTE, rel = "self news", title = "News", bindings = @Binding(name = "id", value = "${instance.id}")),
    //        @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
    //        @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
    //        @InjectLink(resource = NewsResource.class, method = "getNews", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer news", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
    //        @InjectLink(resource = NewsResource.class, method = "getNews", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older news", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")}),
    //})
    private String id;
    private List<Link> links;
    private String userid;
    private String headline;
    private String body;
    private long lastModified;
    private long creationTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}

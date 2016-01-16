package edu.upc.eetac.dsa.kujosa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.kujosa.KujosaRootAPIResource;
import edu.upc.eetac.dsa.kujosa.LoginResource;
import edu.upc.eetac.dsa.kujosa.UserResource;
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
    //@InjectLinks({ })
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<Link> links;
    private String userid;
    private String headline;
    private String body;
    private long lastModified;
    private long creationTimestamp;

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

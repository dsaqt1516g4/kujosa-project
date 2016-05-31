package edu.upc.eetac.dsa.kujosa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.kujosa.CommentResource;
import edu.upc.eetac.dsa.kujosa.KujosaMediaType;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import javax.ws.rs.core.Link;
import java.util.List;


/**
 * Created by Juan on 02/12/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {
    //@InjectLinks({
    //        @InjectLink(resource = CommentResource.class, style = Style.ABSOLUTE, rel = "self", title = "Comment", type = KujosaMediaType.KUJOSA_COMMENT, method = "getComment" ,bindings = @Binding(name = "id", value = "${instance.id}")),
    //        @InjectLink(value = "/comments/{eventid}", style = Style.ABSOLUTE, rel = "comments", title = "Latest comments", type = KujosaMediaType.KUJOSA_COMMENT_COLLECTION, bindings = @Binding(name = "eventid", value = "${instance.eventid}")),
    //        })
    private List<Link> links;
    private String id;
    private String userid;
    private String eventid;
    private String content;
    private long lastModified;
    private long creationTimestamp;


    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
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

package edu.upc.eetac.dsa.kujosa.entity;

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
public class Comment {
    @InjectLinks({
            @InjectLink(resource = CommentResource.class, style = Style.ABSOLUTE, rel = "self", title = "Comment", type = KujosaMediaType.KUJOSA_API_COMMENT, method = "getComment" ,bindings = @Binding(name = "commentid", value = "${instance.commentid}")),
            @InjectLink(value = "/comments/{eventid}", style = Style.ABSOLUTE, rel = "comments", title = "Latest comments", type = KujosaMediaType.KUJOSA_API_COMMENT_COLLECTION, bindings = @Binding(name = "eventid", value = "${instance.eventid}")),
            @InjectLink(resource = CommentResource.class, style = Style.ABSOLUTE, rel = "get-ratio", title = "Ratio", type = KujosaMediaType.KUJOSA_API_RATIO_COLLECTION, method = "getRatios", bindings = {@Binding(name = "commentid", value ="${instance.commentid}"), @Binding(name = "ratio", value = "ratios")}),
            @InjectLink(resource = CommentResource.class, style = Style.ABSOLUTE, rel = "create-ratio", title = "New ratio", type = KujosaMediaType.KUJOSA_API_RATIO, method = "newRatio", bindings = @Binding(name = "commentid", value ="${instance.commentid}"))
    })
    private List<Link> links;
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }


    private int commentid;
    private int userid;
    private int eventid;
    private String content;
    private int ratio;
    private String image;
    private long lastModified;
    private long creationTimestamp;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

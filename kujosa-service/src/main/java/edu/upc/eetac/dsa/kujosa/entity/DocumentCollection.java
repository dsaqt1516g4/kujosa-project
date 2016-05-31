package edu.upc.eetac.dsa.kujosa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 16/12/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentCollection {
    /* @InjectLinks({
                @InjectLink(resource = DocumentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-document", title = "Create document", type = KujosaMediaType.KUJOSA_API_DOCUMENT),
                @InjectLink(value = "/comments/{eventid}", style = InjectLink.Style.ABSOLUTE, rel = "comments", title = "Latest comments", type = KujosaMediaType.KUJOSA_API_DOCUMENT_COLLECTION))
        }) */
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Document> documents = new ArrayList<>();

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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}

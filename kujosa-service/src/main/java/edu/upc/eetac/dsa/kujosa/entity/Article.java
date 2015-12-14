package edu.upc.eetac.dsa.kujosa.entity;

/**
 * Created by Kushal on 2/12/15.
 */
public class Article {
    //@InjectLinks({})
    private int articleid;
    private String name;
    private String content;
    private String creator;
    private String Tag;
    private long creationTimestamp;

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}

package edu.upc.eetac.dsa.kujosa.entity;

/**
 * Created by Kushal
 * +---------------------+
 * | KUJOSA PROJECT      |
 * +---------------------+
 * | DONE:               |
 * +---------------------+
 * |                     |
 * | -All atributes      |
 * |                     |
 * +---------------------+
 *
 *
 */
public class News {
    //@InjectLinks({})
    private int articleid;
    private String name;
    private String content;
    private String creator;
    private String Tags;
    private long creationTimestamp;

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
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

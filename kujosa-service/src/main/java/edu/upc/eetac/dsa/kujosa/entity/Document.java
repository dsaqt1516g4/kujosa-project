package edu.upc.eetac.dsa.kujosa.entity;

/**
 * Created by juan on 16/12/15.
 */
public class Document {
    public String username;
    public int docid;
    public String name;
    public String description;
    public String path;


    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String us){username=us;}
}

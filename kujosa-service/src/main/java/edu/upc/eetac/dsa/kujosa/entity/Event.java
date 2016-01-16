package edu.upc.eetac.dsa.kujosa.entity;

/**
 * Created by kenshin on 23/11/15.
 */
public class Event {
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    private String id;
    private String userid;
    private String titol;
    private String text;
    private long lat;
    private long lon;
    private long startDate;
    private long endDate;
    private int Ratio;
    private long lastModified;
    private int numVots;
    /* private String [] assistents; */


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public int getRatio() {
        return Ratio;
    }

    public void setRatio(int ratio) {
        Ratio = ratio;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumVots() {
        return numVots;
    }

    public void setNumVots(int numVots) {
        this.numVots = numVots;
    }

    /* public String[] getAssistents() {
        return assistents;
    }

    public void setAssistents(String[] assistents) {
        this.assistents = assistents;
    } */
}

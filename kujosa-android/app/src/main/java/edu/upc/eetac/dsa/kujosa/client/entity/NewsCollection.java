package edu.upc.eetac.dsa.kujosa.client.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/11/15.
 */
public class NewsCollection {
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<News> news = new ArrayList<>();

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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}

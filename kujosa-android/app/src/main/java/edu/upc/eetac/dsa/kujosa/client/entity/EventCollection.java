package edu.upc.eetac.dsa.kujosa.client.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 08/02/16.
 */
public class EventCollection {
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Event> events = new ArrayList<>();
}

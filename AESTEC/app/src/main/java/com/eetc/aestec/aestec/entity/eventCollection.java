package com.eetc.aestec.aestec.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by twerky on 17/01/16.
 */
public class eventCollection {
    private Date startdate;
    private Date enddate;
    private List<event> events= new ArrayList<>();

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public List<event> getEvents() {
        return events;
    }

    public void setEvents(List<event> events) {
        this.events = events;
    }
}

package com.eetc.aestec.aestec.entity;

import java.util.List;

import javax.ws.rs.core.Link;

/**
 * Created by twerky on 25/12/15.
 */
public class AuthToken {
    private List<Link> links;
    private String userid;
    private String token;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

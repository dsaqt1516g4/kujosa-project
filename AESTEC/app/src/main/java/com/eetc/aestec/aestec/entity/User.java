package com.eetc.aestec.aestec.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twerky on 17/01/16.
 */
public class User {
    private String userid;
    private String name;
    private String email;
    private  String password;
    private boolean isAdmin;
    private boolean loginOK;
    private Map<String, Link> links = new HashMap<String, Link>();

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isLoginOK() {
        return loginOK;
    }

    public void setLoginOK(boolean loginOK) {
        this.loginOK = loginOK;
    }
}

package com.eetc.aestec.aestec.client;

import com.eetc.aestec.aestec.entity.Link;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twerky on 25/12/15.
 */
public class RootAPI {

    private Map<String, Link> links;

    public RootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;}
}
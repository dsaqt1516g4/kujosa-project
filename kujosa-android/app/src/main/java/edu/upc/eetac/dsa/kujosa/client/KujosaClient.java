package edu.upc.eetac.dsa.kujosa.client;

import android.util.Log;

import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.kujosa.client.entity.AuthToken;
import edu.upc.eetac.dsa.kujosa.client.entity.Link;
import edu.upc.eetac.dsa.kujosa.client.entity.Root;

/**
 * Created by root on 11/11/15.
 */
public class KujosaClient {
    private final static String BASE_URI = "http://192.168.1.103:8080/kujosa";
    private static KujosaClient instance;
    private Root root;
    private ClientConfig clientConfig = null;
    private Client client = null;
    private AuthToken authToken = null;
    private final static String TAG = KujosaClient.class.toString();

    private KujosaClient() {
        clientConfig = new ClientConfig();
        client = ClientBuilder.newClient(clientConfig);
        loadRoot();
    }


    public boolean login(String userid, String password) {
        String loginUri = getLink(root.getLinks(), "login").getUri().toString();
        WebTarget target = client.target(loginUri);
        Form form = new Form();
        //form.param("login", "alicia");
        //form.param("password", "alicia");
        form.param("login", userid);
        form.param("password", password);
        String json = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        authToken = (new Gson()).fromJson(json, AuthToken.class);
        Log.d(TAG, json);
        return true;
    }

    public static KujosaClient getInstance() {
        if (instance == null)
            instance = new KujosaClient();
        return instance;
    }

    private void loadRoot() {
        WebTarget target = client.target(BASE_URI);
        Response response = target.request().get();

        String json = response.readEntity(String.class);
        root = (new Gson()).fromJson(json, Root.class);
    }

    public Root getRoot() {
        return root;
    }

    public final static Link getLink(List<Link> links, String rel){
        for(Link link : links){
            if(link.getRels().contains(rel)) {
                return link;
            }
        }
        return null;
    }

    public String getNews(String uri) throws KujosaClientException {
        if(uri==null){
            uri = getLink(authToken.getLinks(), "current-news").getUri().toString();
        }
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new KujosaClientException(response.readEntity(String.class));
    }

    public String getComments(String uri) throws KujosaClientException {
        /*
        if(uri==null){
            uri = getLink(authToken.getLinks(), "current-stings").getUri().toString();
        }
        */
        uri = BASE_URI+"/comments";
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new KujosaClientException(response.readEntity(String.class));
    }
    public String getEvent(String uri) throws KujosaClientException {
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new KujosaClientException(response.readEntity(String.class));
    }
}
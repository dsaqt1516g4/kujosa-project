package com.eetc.aestec.aestec.client;

import com.eetc.aestec.aestec.entity.*;
import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by kushal on 25/12/15.
 */
public class AestecClient {

    private static AestecClient instance = null;
    private URL url;

    private AestecClient root = null;
    private final static String BASE_URI = "http://10.83.63.80:8080/kujosa";
    private RootAPI rootAPI;

    private ClientConfig clientConfig = null;
    private Client client = null;
    private AuthToken authToken = null;
    private final static String TAG = AestecClient.class.toString();
    private Context context;

    private AestecClient(Context context) throws IOException, AppException {
        super();

        AssetManager assetManager = context.getAssets();
        Properties config = new Properties();
        config.load(assetManager.open("config.properties"));
        String urlHome = config.getProperty("kujosa.home");
        url = new URL(urlHome);

        Log.d("LINKS", url.toString());
        getRootAPI();
    }

    public static AestecClient getInstance( Context context) throws AppException{
        if (instance == null)
            try {
                instance = new AestecClient(context);
            }catch (IOException e){
        throw new AppException("Can't load config file")  ;
            }
        return instance;
    }

    private void getRootAPI()  throws AppException{
        Log.d(TAG,"getRootAPI()");
        rootAPI =new RootAPI();
        HttpURLConnection urlConnection = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            System.out.println("No //////////////////////////////////////\\\\se connecta o que");
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Aestec API Web Service");
        }
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, rootAPI.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Aestec API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Aestec Root API");
        }


    }




    private JSONObject createJsonUser(User user) throws JSONException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("username", user.getName());
        jsonUser.put("password", user.getPassword());
        jsonUser.put("email", user.getEmail());

        return jsonUser;
    }

    private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
            throws AppException, JSONException {
        for (int i = 0; i < jsonLinks.length(); i++) {
            Link link = null;
            try {
                link = SimpleLinkHeaderParser.parseLink(jsonLinks.getString(i));
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
            String rel = link.getParameters().get("rel");
            String rels[] = rel.split("\\s");
            for (String s : rels)
                map.put(s, link);
        }
    }

    public User login(String userid, String password) throws AppException{

        Log.d(TAG, "LOGIN");
        User user = new User();
        user.setName(userid);
        user.setPassword(password);
        HttpURLConnection urlConnection = null;
        try {
            JSONObject jsonUser = createJsonUser(user);
            URL urlPostUsers = new URL(rootAPI.getLinks().get("login").getTarget());
            urlConnection = (HttpURLConnection) urlPostUsers.openConnection();
            String mediaType = rootAPI.getLinks().get("login").getParameters().get("type");
            urlConnection.setRequestProperty("Accept",
                    mediaType);
            urlConnection.setRequestProperty("Content-Type",
                    mediaType);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            PrintWriter writer = new PrintWriter(
                    urlConnection.getOutputStream());
            writer.println(jsonUser.toString());
            writer.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            jsonUser = new JSONObject(sb.toString());


            user.setLoginOK(jsonUser.getBoolean("loginSuccessful"));


            JSONArray jsonLinks = jsonUser.getJSONArray("links");
            parseLinks(jsonLinks, user.getLinks());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error parsing response");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error getting response");
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return  user;
    }



    public User register(String user, String password, String email) throws AppException{
        Log.d(TAG, "entra en el register");
        User as = new User();
        as.setName(user);
        as.setEmail(email);
        as.setPassword(password);

        HttpURLConnection urlConnection = null;
        try {
            JSONObject jsonUser = createJsonUser(as);
            URL urlPostUsers = new URL(rootAPI.getLinks().get("create-user")
                    .getTarget());
            urlConnection = (HttpURLConnection) urlPostUsers.openConnection();
            String mediaType = rootAPI.getLinks().get("create-user").getParameters().get("type"); //Esta línea no estaba en el gist
            urlConnection.setRequestProperty("Accept",
                    mediaType); //Esto estaba mal en los gists
            urlConnection.setRequestProperty("Content-Type",
                    mediaType);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            PrintWriter writer = new PrintWriter(
                    urlConnection.getOutputStream());
            writer.println(jsonUser.toString());
            writer.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            jsonUser = new JSONObject(sb.toString());

            as.setName(jsonUser.getString("username"));

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error parsing response");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error getting response");
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return as;
    }

    }



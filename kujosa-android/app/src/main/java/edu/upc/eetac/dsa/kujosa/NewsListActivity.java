package edu.upc.eetac.dsa.kujosa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.upc.eetac.dsa.kujosa.client.KujosaClient;
import edu.upc.eetac.dsa.kujosa.client.KujosaClientException;
import edu.upc.eetac.dsa.kujosa.client.entity.News;
import edu.upc.eetac.dsa.kujosa.client.entity.NewsCollection;

public class NewsListActivity extends AppCompatActivity {

    private final static String TAG = NewsListActivity.class.toString();
    private GetStingsTask mGetStingsTask = null;
    private NewsCollection news = new NewsCollection();
    private NewsCollectionAdapter adapter = null;


    class GetStingsTask extends AsyncTask<Void, Void, String> {
        private String uri;
        public GetStingsTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonNewsCollection = null;
            try{
                jsonNewsCollection = KujosaClient.getInstance().getNews(uri);
                Log.d("KUJOSA", jsonNewsCollection);
            }catch(KujosaClientException e){
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonNewsCollection;
        }

        @Override
        protected void onPostExecute(String jsonNewsCollection) {
            JSONObject jo = null;
            JSONArray news2 = null;
            Log.d(TAG, jsonNewsCollection);
            try{
                jo = new JSONObject(jsonNewsCollection);
                news2 = jo.getJSONArray("news");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("KUJOSA LINK", news2.toString());
            NewsCollection newsCollection = (new Gson()).fromJson(jsonNewsCollection, NewsCollection.class);
            for(News news : newsCollection.getNews()){
                NewsListActivity.this.news.getNews().add(NewsListActivity.this.news.getNews().size(), news);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Execute AsyncTask
        mGetStingsTask = new GetStingsTask(null);
        mGetStingsTask.execute((Void) null);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        ListView list = (ListView) findViewById(R.id.list);
        adapter = new NewsCollectionAdapter(this, news);
        list.setAdapter(adapter);
        // set list OnItemClick listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsListActivity.this, NewsDetailActivity.class);
                String uri = KujosaClient.getLink(news.getNews().get(position).getLinks(), "self").getUri().toString();
                intent.putExtra("uri", uri);
                startActivity(intent);
                //Log.d(TAG, KujosaClient.getLink(news.getNews().get(position).getLinks(), "self").getUri().toString());

            }
        });
    }


}

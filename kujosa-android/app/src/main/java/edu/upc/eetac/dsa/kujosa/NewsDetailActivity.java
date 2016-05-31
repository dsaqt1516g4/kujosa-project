package edu.upc.eetac.dsa.kujosa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Date;

import edu.upc.eetac.dsa.kujosa.client.KujosaClient;
import edu.upc.eetac.dsa.kujosa.client.KujosaClientException;
import edu.upc.eetac.dsa.kujosa.client.entity.News;

public class NewsDetailActivity extends AppCompatActivity {

    private static final String TAG = NewsDetailActivity.class.toString();
    TextView textViewName = null;
    TextView textViewDescription = null;
    TextView textViewLikes = null;
    TextView textViewCreation = null;
    GetNewsTask mGetNewsTask = null;

    class GetNewsTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetNewsTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonNews = null;
            try {
                jsonNews = KujosaClient.getInstance().getNews(uri);
            } catch (KujosaClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonNews;
        }

        @Override
        protected void onPostExecute(String jsonNews) {
            Log.d(TAG, jsonNews);
            News news = (new Gson()).fromJson(jsonNews, News.class);


            String user = news.getCreator();
            String headline = news.getHeadline();
            String body = news.getBody();
            long lcreation = news.getCreationTimestamp();
            String creation = String.valueOf(lcreation);

            //likes= news.getLikes();
            //slikes = String.valueOf(likes);

            textViewName.setText(user);
            textViewDescription.setText(headline);
            textViewLikes.setText(body);
            textViewCreation.setText(creation);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        String uri = (String) getIntent().getExtras().get("uri");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewName= (TextView) findViewById(R.id.textViewName);
        textViewDescription= (TextView) findViewById(R.id.textViewDescription);
        textViewLikes= (TextView) findViewById(R.id.textViewLikes);;
        //textViewaddress= (TextView) findViewById(R.id.textViewAddress);
        //textViewPhone= (TextView) findViewById(R.id.textViewPhone);


        // Execute AsyncTask
        mGetNewsTask = new GetNewsTask(uri);
        mGetNewsTask.execute((Void) null);

        //btcomments = (Button) findViewById(R.id.Comments);

        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }

}

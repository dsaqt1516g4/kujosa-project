package edu.upc.eetac.dsa.kujosa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import edu.upc.eetac.dsa.kujosa.client.entity.NewsCollection;

/**
 * Created by root on 11/11/15.
 */
public class NewsCollectionAdapter extends BaseAdapter {
    private NewsCollection newsCollection;
    private LayoutInflater layoutInflater;

    public NewsCollectionAdapter(Context context, NewsCollection newsCollection){
        layoutInflater = LayoutInflater.from(context);
        this.newsCollection = newsCollection;
    }

    @Override
    public int getCount() {
        return newsCollection.getNews().size();
    }

    @Override
    public Object getItem(int position) {
        return newsCollection.getNews().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}


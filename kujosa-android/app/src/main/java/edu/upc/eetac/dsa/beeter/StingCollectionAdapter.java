package edu.upc.eetac.dsa.beeter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import edu.upc.eetac.dsa.beeter.client.entity.StingCollection;

/**
 * Created by root on 11/11/15.
 */
public class StingCollectionAdapter extends BaseAdapter {
    private StingCollection stingCollection;
    private LayoutInflater layoutInflater;

    public StingCollectionAdapter(Context context, StingCollection stingCollection){
        layoutInflater = LayoutInflater.from(context);
        this.stingCollection = stingCollection;
    }

    @Override
    public int getCount() {
        return stingCollection.getStings().size();
    }

    @Override
    public Object getItem(int position) {
        return stingCollection.getStings().get(position);
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


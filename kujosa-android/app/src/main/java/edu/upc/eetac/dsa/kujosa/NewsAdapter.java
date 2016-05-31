package edu.upc.eetac.dsa.kujosa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.kujosa.client.entity.News;

/**
 * Created by root on 10/02/16.
 */
public class NewsAdapter  extends BaseAdapter {
    private ArrayList<News> data;
    private LayoutInflater inflater;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_news, null);
            viewHolder = new ViewHolder();

            viewHolder.tvId = (TextView) convertView
                    .findViewById(R.id.textAddress);
            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.textName);
            viewHolder.tvDescription = (TextView) convertView
                    .findViewById(R.id.textViewDescription);
            viewHolder.tvLikes = (TextView) convertView
                    .findViewById(R.id.textViewLikes);
            viewHolder.tvAddress = (TextView) convertView
                    .findViewById(R.id.textViewAddress);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String id = data.get(position).getId();
        String user = data.get(position).getCreator();
        String headline = data.get(position).getHeadline();
        String body = data.get(position).getBody();
        long creation = data.get(position).getCreationTimestamp();
        String screation = String.valueOf(creation);
        //String phone = data.get(position).getPhone();

        viewHolder.tvId.setText(id);
        viewHolder.tvName.setText(user);
        viewHolder.tvDescription.setText(headline);
        viewHolder.tvLikes.setText(body);
        viewHolder.tvAddress.setText(screation);
        //viewHolder.tvPhone.setText(phone);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvDescription;
        TextView tvLikes;
        TextView tvAddress;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(((News) getItem(position)).getId());
    }


    public NewsAdapter(Context context, ArrayList<News> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
}

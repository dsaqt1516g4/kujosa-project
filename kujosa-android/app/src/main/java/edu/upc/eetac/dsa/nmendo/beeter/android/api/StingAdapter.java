package edu.upc.eetac.dsa.nmendo.beeter.android.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.upc.eetac.dsa.nmendo.beeter.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StingAdapter extends BaseAdapter {
	private static class ViewHolder {
		TextView tvSubject;
		TextView tvUsername;
		TextView tvDate;
	}

	private ArrayList<Sting> data;
	private LayoutInflater inflater;

	/*
	 * // devuelve el número total de filas que se estrán mostrando
	 * 
	 * @Override public int getCount() { // TODO Auto-generated method stub
	 * return 0; }
	 * 
	 * @Override public Object getItem(int position) { // TODO Auto-generated
	 * method stub return null; }
	 */
	// devuelve un valor único para una determinada posición
	// conseguir que los elementos que debemos una lista estén identificados,
	// que sean unicos
	/*
	 * @Override public long getItemId(int position) { // TODO Auto-generated
	 * method stub return 0; }
	 */
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
		return Long.parseLong(((Sting) getItem(position)).getId());
	}

	/*
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { // TODO Auto-generated method stub return null; }
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_sting, null);
			viewHolder = new ViewHolder();
			viewHolder.tvSubject = (TextView) convertView
					.findViewById(R.id.tvSubject);
			viewHolder.tvUsername = (TextView) convertView
					.findViewById(R.id.tvUsername);
			viewHolder.tvDate = (TextView) convertView
					.findViewById(R.id.tvDate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String subject = data.get(position).getSubject();
		String username = data.get(position).getUsername();
		String date = SimpleDateFormat.getInstance().format(
				data.get(position).getLastModified());
		viewHolder.tvSubject.setText(subject);
		viewHolder.tvUsername.setText(username);
		viewHolder.tvDate.setText(date);
		return convertView;
	}

	public StingAdapter(Context context, ArrayList<Sting> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

}

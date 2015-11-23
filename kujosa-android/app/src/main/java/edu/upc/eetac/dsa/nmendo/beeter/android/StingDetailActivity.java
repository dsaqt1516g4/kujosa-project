package edu.upc.eetac.dsa.nmendo.beeter.android;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import edu.upc.eetac.dsa.nmendo.beeter.android.api.BeeterAPI;
import edu.upc.eetac.dsa.nmendo.beeter.android.api.BeeterAndroidException;
import edu.upc.eetac.dsa.nmendo.beeter.android.api.Sting;

public class StingDetailActivity extends Activity {
	private final static String TAG = StingDetailActivity.class.getName();

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.sting_detail_layout); }
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sting_detail_layout);
		String urlSting = (String) getIntent().getExtras().get("url");
		(new FetchStingTask()).execute(urlSting);
	}

	private void loadSting(Sting sting) {
		TextView tvDetailSubject = (TextView) findViewById(R.id.tvDetailSubject);
		TextView tvDetailContent = (TextView) findViewById(R.id.tvDetailContent);
		TextView tvDetailUsername = (TextView) findViewById(R.id.tvDetailUsername);
		TextView tvDetailDate = (TextView) findViewById(R.id.tvDetailDate);

		tvDetailSubject.setText(sting.getSubject());
		tvDetailContent.setText(sting.getContent());
		tvDetailUsername.setText(sting.getUsername());
		tvDetailDate.setText(SimpleDateFormat.getInstance().format(
				sting.getLastModified()));
	}

	private class FetchStingTask extends AsyncTask<String, Void, Sting> {
		private ProgressDialog pd;

		@Override
		protected Sting doInBackground(String... params) {
			Sting sting = null;
			try {
				sting = BeeterAPI.getInstance(StingDetailActivity.this)
						.getSting(params[0]);
			} catch (BeeterAndroidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sting;
		}

		@Override
		protected void onPostExecute(Sting result) {
			loadSting(result);
			if (pd != null) {
				pd.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(StingDetailActivity.this);
			pd.setTitle("Loading...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

	}

	//añadimos de prueba que incluso en los detalles 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.beeter_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miWrite:
			Intent intent = new Intent(this, WriteStingActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
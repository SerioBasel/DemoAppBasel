package com.basel.demoappbasel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.basel.demoappbasel.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BlogReaderActivity extends ListActivity {

	protected String[] mBlogPostTitles;

	public static final int NUMBER_OF_POSTS = 20;

	public static final String TAG = BlogReaderActivity.class.getSimpleName();

	protected JSONObject mBlogData;

	protected ProgressBar mProgressBar;
	private ArrayList<HashMap<String, String>> blogPosts;

	private final String KEY_TITLE = "title";
	private final String KET_AUTHOR = "author";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_reader);
		if (savedInstanceState != null) {
			savedInstanceState.getSerializable("posts");
		}

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		if (isNetWorkAvailable()) {
			mProgressBar.setVisibility(View.VISIBLE);
			GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
			getBlogPostsTask.execute();
		} else {
			Toast.makeText(this, "Network is Unavailable", Toast.LENGTH_LONG)
					.show();
		}

		// Toast.makeText(this, getString(R.string.No_Items),
		// Toast.LENGTH_LONG).show();
	}

	private boolean isNetWorkAvailable() {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}
		return isAvailable;
	}

	public void handleBlogResponse() {
		mProgressBar.setVisibility(View.INVISIBLE);
		if (mBlogData == null) {
			updateDisplayforError();
		} else {
			try {
				JSONArray jsonPosts = mBlogData.getJSONArray("posts");

				blogPosts = new ArrayList<HashMap<String, String>>();

				for (int i = 0; i < jsonPosts.length(); i++) {
					JSONObject post = jsonPosts.getJSONObject(i);
					String title = post.getString(KEY_TITLE);
					title = Html.fromHtml(title).toString();
					String author = post.getString(KET_AUTHOR);
					author = Html.fromHtml(author).toString();

					HashMap<String, String> blogPost = new HashMap<String, String>();
					blogPost.put(KEY_TITLE, title);
					blogPost.put(KET_AUTHOR, author);

					blogPosts.add(blogPost);

				}

				String[] keys = { KEY_TITLE, KET_AUTHOR };
				int[] ids = { android.R.id.text1, android.R.id.text2 };
				SimpleAdapter adapter = new SimpleAdapter(this, blogPosts,
						android.R.layout.simple_list_item_2, keys, ids);

				setListAdapter(adapter);

			} catch (JSONException e) {
			}

		}

	}

	private void updateDisplayforError() {
		Log.i(TAG, "The mBlogData is null in update list");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Error");
		builder.setMessage("There is an error");
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();

		TextView emptyTextView = (TextView) getListView().getEmptyView();
		emptyTextView.setText(getString(R.string.No_Items));
	}

	private class GetBlogPostsTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... params) {
			int resposeCode = -1;
			JSONObject jsonResponse = null;

			try {
				URL blogFeedUrl = new URL(
						"http://blog.teamtreehouse.com/api/get_recent_summary/?count=20");
				HttpURLConnection connection = (HttpURLConnection) blogFeedUrl
						.openConnection();
				connection.connect();

				resposeCode = connection.getResponseCode();
				if (resposeCode == HttpURLConnection.HTTP_OK) {
					InputStream inputStream = connection.getInputStream();
					Reader reader = new InputStreamReader(inputStream);
					int contentLength = connection.getContentLength();
					char[] charArray = new char[contentLength];
					reader.read(charArray);
					String responseData = new String(charArray);
					Log.v(TAG, "responseData");
					Log.v(TAG, responseData);

					jsonResponse = new JSONObject(responseData);

				} else {
					Log.i(TAG, "Unsuccessful Http ResposeCode: " + resposeCode);
				}

			} catch (MalformedURLException e) {
				Log.e(TAG, "Exception caught: ", e);

			} catch (IOException e) {
				Log.e(TAG, "Exception caught: ", e);
			} catch (Exception e) {
				Log.e(TAG, "Exception caught: ", e);
			}
			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mBlogData = result;
			handleBlogResponse();

		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (blogPosts != null) {
			outState.putSerializable("posts", blogPosts);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		JSONArray jsonPosts;
		try {
			jsonPosts = mBlogData.getJSONArray("posts");
			JSONObject jsonPost = jsonPosts.getJSONObject(position);
			String blogUrl = jsonPost.getString("url");
			Intent intent = new Intent(this, WebViewActivity.class);
			intent.setData(Uri.parse(blogUrl));
			startActivity(intent);

		} catch (JSONException e) {
			logException(e);

		}

	}

	private void logException(JSONException e) {
		Log.e(TAG, "Exception caught!" + e);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blog_reader, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_info) {
			aboutAlertDialog();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void aboutAlertDialog() {
		final AlertDialog ad = new AlertDialog.Builder(this)
				.setMessage(R.string.about_blogreader_activity)
				.setTitle(R.string.activity_info)
				.setNeutralButton(R.string.ok, null).setCancelable(true)
				.create();

		ad.show();
	}

}
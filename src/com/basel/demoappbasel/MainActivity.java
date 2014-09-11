package com.basel.demoappbasel;

import com.basel.demoappbasel.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
				.setMessage(R.string.about_main_activity)
				.setTitle(R.string.activity_info)
				.setNeutralButton(R.string.ok, null)
				.setCancelable(true).create();

		ad.show();
	}
 

	public void buttonOne(View view) {
		Intent intent = new Intent(this, BlogReaderActivity.class);
		startActivity(intent);

	}

	public void buttonTwo(View view) {
		Intent intent = new Intent(this, CompassActivity.class);
		startActivity(intent);

	}

	public void buttonThree(View view) {
		Intent intent = new Intent(this, FragmentActivity.class);
		startActivity(intent);

	}
	public void buttonFour(View view) {
		Intent intent = new Intent(this, BroadcastServiceActivity.class);
		startActivity(intent);

	}

}

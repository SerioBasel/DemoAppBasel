package com.basel.demoappbasel;

import com.basel.demoappbasel.R;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FragmentActivity extends android.support.v4.app.FragmentActivity
		implements Communicator {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

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
				.setMessage(R.string.about_fragment_activity)
				.setTitle(R.string.activity_info)
				.setNeutralButton(R.string.ok, null).setCancelable(true)
				.create();

		ad.show();
	}

	@Override
	public void respond(int i) {

		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentTwo fragment = (fragmentTwo) fragmentManager
				.findFragmentById(R.id.fragmentTwo);

		fragment.changeData(i);

	}

}

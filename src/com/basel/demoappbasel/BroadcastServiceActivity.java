package com.basel.demoappbasel;

import com.basel.demoappbasel.R;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BroadcastServiceActivity extends ActionBarActivity {

	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcast_service);
		editText = (EditText) findViewById(R.id.editText);

	}

	public void setAlarm(View view) {
		if (isMyServiceRunning() != true) {
			int i = Integer.parseInt(editText.getText().toString());
			Intent intent = new Intent(this, Receiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
					234324243, intent, 0);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			// set the alarm after i second of the system time.
			alarmManager.set(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis() + (i * 1000), pendingIntent);

			Toast.makeText(this, "Song will Start in " + i + " seconds",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Service is Already Triggered",
					Toast.LENGTH_LONG).show();

		}

	}

	public void stopAlarm(View view) {
		if (isMyServiceRunning()) {
			stopService(new Intent(this, ServiceHelper.class));
			Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "The Service is Not Running",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.broadcast, menu);
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
				.setMessage(R.string.about_broadcast_activity)
				.setTitle(R.string.activity_info)
				.setNeutralButton(R.string.ok, null).setCancelable(true)
				.create();

		ad.show();
	}

	private boolean isMyServiceRunning() { // This method will let you know if
											// the service is running or not.
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (ServiceHelper.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

}

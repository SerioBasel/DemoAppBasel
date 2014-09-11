package com.basel.demoappbasel;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;


		// starting a service for the song
		if (isMyServiceRunning()) {
			//context.stopService(new Intent(context, ServiceHelper.class));
		} else {
			context.startService(new Intent(context, ServiceHelper.class));
			Toast.makeText(context, "start service", Toast.LENGTH_LONG).show();
		}

	}

	private boolean isMyServiceRunning() { // This method will let you know if
											// the service is running or not.
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
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

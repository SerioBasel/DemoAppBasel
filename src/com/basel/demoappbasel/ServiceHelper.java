package com.basel.demoappbasel;

import java.io.IOException;

import com.basel.demoappbasel.R;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class ServiceHelper extends Service {
	public MediaPlayer mediaPlayer = new MediaPlayer();
	private AssetFileDescriptor assetFileDescriptor;
	private static final int NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			assetFileDescriptor = getAssets().openFd("hotel_california.mp3");
			mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
			assetFileDescriptor.getStartOffset(),
			assetFileDescriptor.getLength());
			mediaPlayer.prepare(); // looks like this method is more appropriate
									// to use with a service or a thread
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (mediaPlayer != null) {
			mediaPlayer.start();
		}
		mediaPlayer.start();
		initializeNotification();

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		} else {
			mediaPlayer.stop();
			mediaPlayer.release();
		}

		removeNotification();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void initializeNotification() {

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				// simplest way to create a notification along with the life
				// span of the song
				.setSmallIcon(R.drawable.eagles)
				.setContentTitle("Playing Hotel California")
				.setContentText("The Eagles");
		notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

	}

	private void removeNotification() {
		notificationManager.cancel(NOTIFICATION_ID); // remove the notification
														// as the song stops

	}

}

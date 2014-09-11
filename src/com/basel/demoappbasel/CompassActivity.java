package com.basel.demoappbasel;

import com.basel.demoappbasel.R;

import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends ActionBarActivity implements
		SensorEventListener {
	ImageView compassImage;
	SensorManager sensorManager;
	Sensor accelerometerSensor;
	Sensor magneticSensor;

	private float[] gravity;
	private float[] geomagnetic;

	protected float azimuth;

	protected TextView degreeTextView;
	

	protected char degree = (char) 0x00B0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);
		

		
		compassImage = (ImageView) findViewById(R.id.compassImage);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magneticSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		degreeTextView = (TextView) findViewById(R.id.degreeTextView);

	}


	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		sensorManager.registerListener(this, accelerometerSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, magneticSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		

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
				.setMessage(R.string.about_compass_activity)
				.setTitle(R.string.activity_info)
				.setNeutralButton(R.string.ok, null).setCancelable(true)
				.create();

		ad.show();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			gravity = event.values;
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			geomagnetic = event.values;
		if (gravity != null && geomagnetic != null) {
			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, gravity,
					geomagnetic);
			if (success) {
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);
				float oAzimuth = (float) Math.toDegrees(orientation[0]);
				azimuth = normalizeDegree(oAzimuth);
				degreeTextView.setText((String.valueOf(Math.round(azimuth)) + degree));

				// contains:
				// azimut,
				// pitch and
				// roll

				RotateAnimation rotate = new RotateAnimation(-oAzimuth,
						-oAzimuth, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f); // testhere
				rotate.setDuration(2000);
				rotate.setFillAfter(true);
				compassImage.startAnimation(rotate);

			}

		}

	}


	private float normalizeDegree(float value) {
		if (value >= 0.0f && value <= 180.0f) {
			return value;
		} else {
			return 180 + (180 + value);
		}
	}
}
package com.ibtsic.android;

import com.ibtsic.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class AppActivity extends Activity {

	Button tripplan;
	Button busrouteinformation;
	Button trafficinformation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		final Context context = this;

		tripplan = (Button) findViewById(R.id.button1);

		tripplan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, TripPlan.class);
                startActivity(intent);

			}

		});


		busrouteinformation = (Button) findViewById(R.id.button2);

		busrouteinformation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, BusRouteInformation.class);
                startActivity(intent);

			}

		});



		trafficinformation = (Button) findViewById(R.id.button3);

		trafficinformation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, TrafficInformation.class);
                startActivity(intent);

			}

		});

	}

}
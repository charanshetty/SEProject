package com.ibtsic.android;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibtsic.android.model.Run;
import com.ibtsic.android.net.HttpConnection;
import com.ibtsic.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class TripPlanTable extends Activity {
	private static final int Run = 0;
	private static final int ArrayList = 0;

	String routeID=null;
	private Button viewrealtime;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    routeID = extras.getString("input");}
		 //routeID=savedInstanceState.getString("input");
		setContentView(R.layout.tripplantable);
		Gson gson = new GsonBuilder().create();
		String s = null;


		try {

			List<String> l=new HttpConnection().sendHttpGetRequest("http://192.168.3.40:8080/IbtsicServer/getRunsOnPathAction?pathName="+routeID.toString());
			s = l.get(0);
			System.out.println("soum"+s);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Run[] r = gson.fromJson( s,Run[].class);


		if(r!=null){

		EditText depbus1=(EditText) this.findViewById(R.id.text_dep_bus1);
		depbus1.setText(r[0].startTime.toString());

		EditText arrbus1=(EditText) this.findViewById(R.id.text_arr_bus1);
		arrbus1.setText(r[0].endTime.toString());

//		EditText depbus2=(EditText) this.findViewById(R.id.text_dep_bus2);
//		depbus2.setText(r[1].startTime.toString());
//
//		EditText arrbus2=(EditText) this.findViewById(R.id.text_arr_bus2);
//		arrbus2.setText(r[1].endTime.toString());
//



		}
		addListenerOnButton();

	}
	public void addListenerOnButton() {
		Log.d("idx","insidehere");
		final Context context = this;

		viewrealtime = (Button) findViewById(R.id.button_trip_show_realtime_bus_information);

		viewrealtime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.out.println("bundle"+routeID);
				Intent intent = new Intent(context, TripPlanGooglemaps.class);
				intent.putExtra("routeput", routeID);

                startActivity(intent);
                Log.d("idx","insidehere");
			}	});



	}

}
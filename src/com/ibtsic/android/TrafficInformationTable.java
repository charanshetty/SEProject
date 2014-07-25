package com.ibtsic.android;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibtsic.android.model.Announcement;
import com.ibtsic.android.model.Run;
import com.ibtsic.android.net.HttpConnection;
import com.ibtsic.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

public class TrafficInformationTable extends Activity {
String routeID=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras=getIntent().getExtras();
		routeID=extras.getString("input");
		setContentView(R.layout.trafficinformationtable);

		Gson gson = new GsonBuilder().create();
		String s = null;
		try {

			List<String> l=new HttpConnection().sendHttpGetRequest("http://192.168.3.40:8080/IbtsicServer/getActiveAnnouncementsForPathAction?pathName="+routeID);
			s = l.get(0);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Announcement[] an = gson.fromJson( s,Announcement[].class);




		EditText traffic_description=(EditText) this.findViewById(R.id.text_traffic_information);
		System.out.println(an);
		if(an!=null)
		traffic_description.setText(an[0].description.toString());



	}

}
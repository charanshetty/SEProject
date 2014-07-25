package com.ibtsic.android;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibtsic.android.model.Path;
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

public class BusRouteInformationTable extends Activity {
String pathid=null;
	String source=null;
	String dest=null;
	Path[] r=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busrouteinformationtable);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    source = extras.getString("input1");
		    dest = extras.getString("input2");}

		Gson gson = new GsonBuilder().create();
		String s = null;
		String s1 = null;
		String s2 = null;

		try {
			// for user put the source name
			EditText textsourcename=(EditText) this.findViewById(R.id.text_source_name);

			// for user put the destination name
			EditText textdestinationname=(EditText) this.findViewById(R.id.text_destination_name);




			List<String> l=new HttpConnection().sendHttpGetRequest("http://192.168.3.40:8080/IbtsicServer/getPathsConnectingTwoNodesAction?node1Name="+source.replace(" ","%20").toString()+"&node2Name="+dest.replace(" ","%20").toString());
			System.out.println("prio"+l);
		s = l.get(0);
			s1 = l.get(1);
			s2 = l.get(2);



		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		r = gson.fromJson(s,Path[].class);
		String r1 = s1.toString();
		String r2 = s2.toString();



		EditText path1=(EditText) this.findViewById(R.id.text_path1);
		path1.setText(r[0].name.toString());

		EditText nbatbus1=(EditText) this.findViewById(R.id.text_nbat_bus1);
		nbatbus1.setText(r1.replace("[", ""));

		EditText etrdbus1=(EditText) this.findViewById(R.id.text_etrd_bus1);
		etrdbus1.setText(r2.replace("[", ""));
		addListenerOnButton();

	}



	public void addListenerOnButton() {
		Log.d("idx","insidehere");
		final Context context = this;

		Button viewrealtime = (Button) findViewById(R.id.button_show_realtime_bus_information);

		viewrealtime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, BusRouteGooglemaps.class);
                intent.putExtra("input3", r[0].name);
                intent.putExtra("input4", source);
                intent.putExtra("input5", dest);
				startActivity(intent);

                Log.d("idx","insidehere");
			}	});



	}

}


package com.ibtsic.android;
import java.io.BufferedReader;
import android.location.LocationListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibtsic.android.model.Bus;
import com.ibtsic.android.model.Node;
import com.ibtsic.android.net.DirectionsJSONParser;
import com.ibtsic.android.net.HttpConnection;
import com.ibtsic.android.R;

public class BusRouteGooglemaps extends FragmentActivity implements LocationListener {
	GoogleMap map;
	PolylineOptions lineOptions = new PolylineOptions();
	ArrayList<LatLng> markerPoints;
	TextView tvDistanceDuration;
	AutoCompleteTextView textView;
	ArrayList<LatLng> points = new ArrayList<LatLng>();
	String source=null;
	String dest=null;
String routeID=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemaps);
        Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    routeID = extras.getString("input3");
		    source = extras.getString("input4");
		    dest = extras.getString("input5");
		    System.out.println("routes"+routeID);}        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting Map for the SupportMapFragment
        map = fm.getMap();

        // Enable MyLocation Button in the Map
        map.setMyLocationEnabled(true);

		// Getting URL to the Google Directions API
String url = "http://192.168.3.40:8080/IbtsicServer/getNodesInPathAction?pathName="+routeID;
		try {
			url = downloadUrl(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			Gson g = new GsonBuilder().create();
			Node[] a = g.fromJson(url, Node[].class);
int j=0,i=0;
			for (a[i].name=source; i < a.length; ++i) {

				System.out.println("routed"+a[i].name);

				points.add(new LatLng(a[j].latitude, a[j].longitude));
				j++;
				if(a[i].name.equalsIgnoreCase(dest))
					break;
				}



			lineOptions.addAll(points);
			lineOptions.width(2);
			lineOptions.color(Color.RED);
			map.addPolyline(lineOptions);

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 11));

			MarkerOptions options = new MarkerOptions();
			options.position(points.get(0));
			map.addMarker(options);
			options.position(points.get(points.size() - 1));
			map.addMarker(options);
			String busurl="http://192.168.3.40:8080/IbtsicServer/getRtBusesOnPathAction?pathName="+routeID;
			String buspos=null;
			buspos=downloadUrl(busurl);
			Gson b = new GsonBuilder().create();
			Bus[] bus = b.fromJson(buspos, Bus[].class);
			if(bus!=null){
			for(i=0;i<bus.length;i++){
			LatLng busposition=new LatLng(bus[i].latitude, bus[i].longitude);
			System.out.println("gew"+busposition);
		//	options.position(busposition);
//			map.addMarker(options);
			Marker position = map.addMarker(new MarkerOptions()
            .position(busposition)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			GroundOverlayOptions newarkMap = new GroundOverlayOptions()
	        .image(BitmapDescriptorFactory.fromResource(R.drawable.icon_bus))
	        .transparency((float) 0.1)
	        .position(busposition, 2000f, 2500f);

	// Add an overlay to the map, retaining a handle to the GroundOverlay object.
			GroundOverlay imageOverlay = map.addGroundOverlay(newarkMap);}
			}
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** A method to download json data from url */
	private String downloadUrl(String url) throws IOException {
		String data = null;
		String s = null;
		// InputStream iStream = null;
		HttpConnection urlConnection = null;
		try {
			s = new HttpConnection().sendHttpGetRequest(url).get(0);
			data = s;
		} catch (Exception e) {
			System.out.println("exception1 occured");
		}

		return data;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}
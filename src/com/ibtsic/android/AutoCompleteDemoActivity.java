package com.ibtsic.android;

import java.util.ArrayList;
import java.util.List;

import com.ibtsic.android.net.HttpConnection;
import com.ibtsic.android.R;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class AutoCompleteDemoActivity extends Activity {

	TextView tv;
	List<String> l1=new ArrayList<String>();
	AutoCompleteTextView textView1;
	ArrayAdapter<String> adapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete_demo);

        tv=(TextView)findViewById(R.id.textView1);
        textView1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        textView1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				try
				{
					Thread t=new Thread(
						new HttpConnection("sendHttpGetRequest",
							"http://www.w3schools.com",
							l1));
					t.start();
				}
				catch(Exception e)
				{
				}
			}
		});

        adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, l1);
		textView1.setThreshold(1);
        textView1.setAdapter(adapter1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auto_complete_demo, menu);
		return true;
	}
}

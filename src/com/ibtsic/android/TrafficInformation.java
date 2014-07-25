package com.ibtsic.android;

import java.util.ArrayList;
import java.util.List;

import com.ibtsic.android.net.HttpConnection;
import com.ibtsic.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class TrafficInformation extends Activity {
	TextView busroute;
	Button buttontrafficinformation;
	List<String> l1=new ArrayList<String>();

	AutoCompleteTextView textView1;
	ArrayAdapter<String> adapter1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trafficinformation);
		busroute=(TextView)findViewById(R.id.text_routeno);
        textView1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewX);
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
						"http://192.168.3.40:8080/IbtsicServer/getPathNamesWithPrefixAction?pathNamePrefix="+textView1.getText().toString(),
						adapter1));
				t.start();

			}
			catch(Exception e)
			{
				System.out.println("exception1 occured");
			}
		}
	});
    adapter1 = new ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line, l1);
	textView1.setThreshold(1);
    textView1.setAdapter(adapter1);
    adapter1.notifyDataSetChanged();



	addListenerOnButton();

}




	public void addListenerOnButton() {

		final Context context = this;

		buttontrafficinformation = (Button) findViewById(R.id.button_traffic_information);

		buttontrafficinformation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, TrafficInformationTable.class);
				if(textView1.getText()!=null){
					System.out.println("bundle"+textView1.getText());
				intent.putExtra("input",textView1.getText().toString());
                startActivity(intent);}

			}

		});


}
}
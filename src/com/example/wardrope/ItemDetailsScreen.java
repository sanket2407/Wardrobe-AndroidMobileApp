package com.example.wardrope;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ItemDetailsScreen extends Activity {
	ArrayList<Item> itemDetailsArray = new ArrayList<Item>();
	ArrayList<Item> matchItemDetails = new ArrayList<Item>();
	
	ArrayList<Item> idWithMatchArray;
	int[] matchIDs;
	TextView textViewId, textViewCat, textViewDesc,textViewPrice,textViewDate, textViewType, textViewSType;
	Bitmap bm ;
	String encodedImage ;
	int count = 0;
	int id = -1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details_screen);
		DatabaseHandler dbHelper = new DatabaseHandler(
				this.getApplicationContext());
		setTitle("Item Details");
		
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

		TableLayout tableLayout = new TableLayout(this);
		tableLayout.setLayoutParams(tableParams);

		TableRow tableRow = new TableRow(this);
		TableRow row1 = new TableRow(this);
		TableRow row2 = new TableRow(this);
		TableRow row3 = new TableRow(this);
		TableRow row4 = new TableRow(this);
		TableRow row5 = new TableRow(this);
		TableRow row6 = new TableRow(this);
		TableRow row7 = new TableRow(this);
		TableRow row8 = new TableRow(this);
		
		tableRow.setLayoutParams(rowParams);
		row1.setLayoutParams(rowParams);
		row2.setLayoutParams(rowParams);
		row3.setLayoutParams(rowParams);
		row4.setLayoutParams(rowParams);
		row5.setLayoutParams(rowParams);
		row6.setLayoutParams(rowParams);
		row7.setLayoutParams(rowParams);
		row8.setLayoutParams(rowParams);
		
	    Bundle bundle = this.getIntent().getExtras();
		id = bundle.getInt("param1");
		itemDetailsArray = dbHelper.getDetailsIdWise(id+"");
		Item item = itemDetailsArray.get(0);
		
		String category = item.get_category();
		String description = item.get_desc();
		String price = item.get_price();
		String date = item.get_date();
		String type = item.get_type();
		String sType = item.get_sType();
		bm = item.get_image();
		

		ImageView image = new ImageView(this);
    	image.setImageBitmap(bm);    	
    	tableRow.addView(image);
	
    	TextView textId = new TextView(this);
		textId.setText("ID: ");
		textId.setTextSize(20);
		row1.addView(textId);
		textViewId = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewId.setText(id+"");
		textViewId.setTextSize(20);
		row1.addView(textViewId);
		
		TextView textCat = new TextView(this);
		textCat.setText("Category: ");
		textCat.setTextSize(20);
		row2.addView(textCat);
		textViewCat = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewCat.setText(category);
		textViewCat.setTextSize(20);
		row2.addView(textViewCat);
		
		TextView textView1 = new TextView(this);
		textView1.setText("Description: ");
		textView1.setTextSize(20);
		row3.addView(textView1);
		textViewDesc = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewDesc.setText(description);
		textViewDesc.setTextSize(20);
		row3.addView(textViewDesc);
		
		TextView textPrice = new TextView(this);
		textPrice.setText("Price: ");
		textPrice.setTextSize(20);
		row4.addView(textPrice);
		textViewPrice = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewPrice.setText(price);
		textViewPrice.setTextSize(20);
		row4.addView(textViewPrice);
		
		TextView textDate = new TextView(this);
		textDate.setText("Date: ");
		textDate.setTextSize(20);
		row5.addView(textDate);
		textViewDate = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewDate.setText(date);
		textViewDate.setTextSize(20);
		row5.addView(textViewDate);
		
		TextView textType = new TextView(this);
		textType.setText("Type: ");
		textType.setTextSize(20);
		row6.addView(textType);
		textViewType = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewType.setText(type);
		textViewType.setTextSize(20);
		row6.addView(textViewType);
		
		
		TextView textSType = new TextView(this);
		textSType.setText("Sub Type: ");
		textSType.setTextSize(20);
		row7.addView(textSType);
		textViewSType = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewSType.setText(sType);
		textViewSType.setTextSize(20);
		row7.addView(textViewSType);
		
		TextView textViewspace = new TextView(this);
		//textView.setTextColor(Color.BLUE);
		textViewspace.setText("\n");
		textViewspace.setTextSize(20);
		row8.addView(textViewspace);
	
		tableLayout.addView(tableRow);
		tableLayout.addView(row1);
		tableLayout.addView(row2);
		tableLayout.addView(row3);
		tableLayout.addView(row4);
		tableLayout.addView(row5);
		tableLayout.addView(row6);
		tableLayout.addView(row7);
		tableLayout.addView(row8);
		

		setContentView(tableLayout);
    	
		if(getIntent().hasExtra("count")) {
			count = bundle.getInt("count");
		}
    	String temp = id+"";
		idWithMatchArray = dbHelper.getMatched(temp);
		matchIDs = new int[idWithMatchArray.size()];
		
		if((idWithMatchArray.size()) != 0)
		{
		for(int j=0; j< idWithMatchArray.size(); j++){
			Item matchItem = idWithMatchArray.get(j);
			int tempmid = matchItem.get_mid();
			matchIDs[j] = tempmid;
		}
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.MATCH_PARENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT);
	    Button btn = new Button(this);
	    btn.setId(1);
	    final int id_ = btn.getId();
	    btn.setText("Click To Fetch Matchings");
	    			    
	    //btn.setBackgroundColor(Color.rgb(70, 80, 90));
	    tableLayout.addView(btn, params);
	    btn = ((Button) findViewById(id_));
	    btn.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	        	sendMessage(view);
	        }
	    });
		
		
		}
		
		else{
			TextView noMatchingLabel = new TextView(this);
			noMatchingLabel.setText("Oops nothing to match it with... You should go for some shopping!");
			noMatchingLabel.setTextSize(20);
			noMatchingLabel.setTextColor(Color.RED);
			tableLayout.addView(noMatchingLabel);
			setContentView(tableLayout);
			
		}
		
	}
	
	 public void sendMessage(View view) {

		 
		 Bundle bundle = new Bundle();
			bundle.putIntArray("matchIdArray", matchIDs);
			bundle.putInt("currentSelectedId", id);
			
			Intent myIntent = new Intent(ItemDetailsScreen.this, DisplayMatchings.class);
			myIntent.putExtras(bundle);
			startActivityForResult(myIntent, 0);
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_details_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			Intent myIntent = new Intent(ItemDetailsScreen.this, HomeScreen.class);
			startActivityForResult(myIntent, 0);
			break;
		case R.id.action_match:
			matchIt();
			break;
		default:
			break;
		}

		return true;
	}
	
public void matchIt() {

		
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit(); // Put the values
																// from the UI
		String strDesc = textViewDesc.getText().toString();
		editor.putString("Desc", strDesc);
		String strPrice = textViewPrice.getText().toString();
		editor.putString("Price", strPrice);
		String strDate = textViewDate.getText().toString();
		editor.putString("Date", strDate);

		
		Bundle bundle = new Bundle();
		bundle.putInt("currentSelectedId", id);

		
		Intent myIntent = new Intent(ItemDetailsScreen.this, MatchScreen.class);
		
//		ByteArrayOutputStream bs = new ByteArrayOutputStream();
//		bm.compress(Bitmap.CompressFormat.PNG, 50, bs);
		//								myIntent.putExtra("byteArray", bs.toByteArray());

		myIntent.putExtras(bundle);
		
		startActivityForResult(myIntent, 0);
	}
}

package com.example.wardrope;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class DisplayMatchings extends Activity {
	
	ArrayList<Item> dataArrayTemp = new ArrayList<Item>();
	int selectedId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_matchings);
		
		
		DatabaseHandler dbHelper = new DatabaseHandler(
				this.getApplicationContext());
		setTitle("Item Matchings");
		
		Bundle bundle = this.getIntent().getExtras();
		int[] matchIDs = bundle.getIntArray("matchIdArray");
		selectedId = bundle.getInt("currentSelectedId");
		
		for(int k=0; k < matchIDs.length;k++){			
			dataArrayTemp.add((dbHelper.getDetailsIdWise(matchIDs[k]+"")).get(0));
			
		}
		
		final ListView lv1 = (ListView) findViewById(R.id.listV_main);		
		lv1.setAdapter(new ListViewWithButtonAdapter(this, dataArrayTemp, selectedId));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_screen, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			Intent myIntent = new Intent(DisplayMatchings.this, HomeScreen.class);
			startActivityForResult(myIntent, 0);
			break;
		default:
			break;
		}

		return true;
	}
	
	@Override
	public void onBackPressed() {
	   Log.d("CDA", "onBackPressed Called");
	   Bundle bundle = this.getIntent().getExtras();
	   bundle.putInt("param1", selectedId);
	   Intent setIntent = new Intent(this, ItemDetailsScreen.class);
	   setIntent.putExtras(bundle);
	   startActivity(setIntent);
	}
}

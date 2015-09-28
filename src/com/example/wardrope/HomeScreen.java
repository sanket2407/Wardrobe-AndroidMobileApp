package com.example.wardrope;



import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HomeScreen extends Activity {
	
	String strCategory;
	GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		DatabaseHandler db = new DatabaseHandler(this);
		
		View backgroundimage = findViewById(R.id.homscreen);
		Drawable background = backgroundimage.getBackground();
		background.setAlpha(60);

		Resources r = getResources();
		String[] bases = r.getStringArray(R.array.category);
		int count;
		String categoryName;
		String[] displayInformation = new String[bases.length];
		
		for(int index = 0; index < bases.length; index ++)
		{
			count = db.getItemsCount(bases[index]);
			categoryName =  bases[index] + " ( " + count + " )";
			
			displayInformation[index] = categoryName;
		}
		 
		gridView = (GridView) findViewById(R.id.gridView1);

		gridView.setAdapter(new ImageAdapter(this, displayInformation));

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				System.out.println("parent " + parent.getTag());
				System.out.println(" v " + v.getTag());
				switch (position) {
				case 0:
					strCategory = "All";
					break;
				case 1:
					strCategory = "Apparel";
					break;
				case 2:
					strCategory = "Jewelry";
					break;
				case 3:
					strCategory = "Footwear";
					break;
				case 4:
					strCategory = "Other";
					break;

				}
				Bundle bundle = new Bundle();
				bundle.putString("param1", strCategory);

				Intent myIntent = new Intent(HomeScreen.this,
						ViewScreen.class);
				myIntent.putExtras(bundle);
				startActivityForResult(myIntent, 0);

			}
		});
	}

	public void btnAdd(View v) {
		// date.setText(new Date().toString());
		Intent myIntent = new Intent(HomeScreen.this, AddScreen.class);
		//Intent myIntent = new Intent(HomeScreen.this, TestAct.class);

		startActivity(myIntent);

	}


	public void btnSearch(View v) {
		Intent myIntent = new Intent(HomeScreen.this, SearchScreen.class);
		startActivity(myIntent);

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_add:
	    	Intent addIntent = new Intent(HomeScreen.this, AddScreen.class);
			startActivity(addIntent);
	      break;
	    case R.id.action_search:
	    	Intent searchIntent = new Intent(HomeScreen.this, SearchScreen.class);
			startActivity(searchIntent);
	      break;

	    default:
	      break;
	    }

	    return true;
	  } 

	

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home_screen);
//	}
//
//	public void btnAdd(View v) {
//		// date.setText(new Date().toString());
//		Intent myIntent = new Intent(HomeScreen.this, AddScreen.class);
//		//Intent myIntent = new Intent(HomeScreen.this, TestAct.class);
//
//		startActivity(myIntent);
//
//	}
//
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.home_screen, menu);
//		return true;
//	}

}

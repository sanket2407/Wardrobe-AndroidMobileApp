package com.example.wardrope;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ViewScreen extends Activity {

	ArrayList<Item> image_details;
	TextView title;
	String param1;
	ArrayList<Item> itemDetailsArray = new ArrayList<Item>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_screen);



		DatabaseHandler dbHelper = new DatabaseHandler(
				this.getApplicationContext());
		Bundle bundle = this.getIntent().getExtras();
		param1 = bundle.getString("param1");
		setTitle(param1);

		if (param1.equalsIgnoreCase("All")) {
			image_details = dbHelper.getAllItems();
		}else if(param1.equalsIgnoreCase("Searched Items"))
		{
			Item item = bundle.getParcelable("itemtobesearched");
			image_details = dbHelper.searchItem(item);
		}
		else {
			image_details = dbHelper.getCategoryWise(param1);

		}

		if (image_details.size() == 0) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Alert");

			if(param1.equalsIgnoreCase("Searched Items"))
			{
				alertDialogBuilder
				.setMessage("No Items found.")
				.setCancelable(false)

				.setNegativeButton("OK",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {

						Intent myIntent = new Intent(
								ViewScreen.this,
								SearchScreen.class);
						startActivity(myIntent);

						dialog.cancel();
					}
				});
			}
			else{
				// set dialog message
				alertDialogBuilder
				.setMessage("No Items Stored in this Category.")
				.setCancelable(false)

				.setNegativeButton("OK",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {

						Intent myIntent = new Intent(
								ViewScreen.this,
								HomeScreen.class);
						startActivity(myIntent);

						dialog.cancel();
					}
				});
			}
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

		}
		final ListView lv1 = (ListView) findViewById(R.id.listV_main);
		lv1.setAdapter(new ListViewAdapter(this, image_details));

		lv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Item item = new Item();
				item = image_details.get(position);
				int itemId = item.get_id();

				Bundle bundle = new Bundle();
				bundle.putInt("param1", itemId);

				Intent myIntent = new Intent(ViewScreen.this, ItemDetailsScreen.class);
				myIntent.putExtras(bundle);
				startActivityForResult(myIntent, 0);

			}
		});


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
			Intent myIntent = new Intent(ViewScreen.this, HomeScreen.class);
			startActivityForResult(myIntent, 0);
			break;
		default:
			break;
		}

		return true;
	} 

}

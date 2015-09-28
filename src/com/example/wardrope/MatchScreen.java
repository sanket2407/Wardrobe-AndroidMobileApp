package com.example.wardrope;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MatchScreen extends Activity {

	ArrayList<Item> dataArray;
	// ArrayList<Item> dataArrayTemp;

	ArrayList<Item> dataArrayTemp = new ArrayList<Item>();

	String my_sel_items;
	String tempDesc;
	int currentId = -1;
	Button done;
	ListView lstView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_screen);

		DatabaseHandler db = new DatabaseHandler(this);


		if(getIntent().hasExtra("currentSelectedId"))
		{
			Bundle bundle = getIntent().getExtras();
			currentId = bundle.getInt("currentSelectedId");
			bundle.remove("currentSelectedId");
		}
		dataArray = db.getItemsNotYetMatched(currentId);

		if (dataArray.size()==0)
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Alert");

			// set dialog message
			alertDialogBuilder
			.setMessage("No items in the wardrobe.")
			.setCancelable(false)

			.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {

					if (currentId == -1){
						Intent myIntent = new Intent(MatchScreen.this, AddScreen.class);


						if(getIntent().hasExtra("count")) {


							Bundle bundle = getIntent().getExtras();
							int count = bundle.getInt("count");

							bundle = new Bundle();
							bundle.putInt("count", count);
							myIntent.putExtras(bundle);
							startActivity(myIntent);
						}
					}
					else
					{
						Bundle bundle = new Bundle();
						bundle.putInt("param1", currentId);
						Intent myIntent = new Intent(MatchScreen.this, ItemDetailsScreen.class);
						myIntent.putExtras(bundle);
						startActivityForResult(myIntent, 0);

					}

					//								if(getIntent().hasExtra("count")) {

					//										    Bitmap bm = BitmapFactory.decodeByteArray(
					//										        getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);        
					//										
					//											ByteArrayOutputStream bs = new ByteArrayOutputStream();
					//											bm.compress(Bitmap.CompressFormat.PNG, 50, bs);
					//											myIntent.putExtra("byteArray", bs.toByteArray());
					//
					//										
					//										}




					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();


		}

		done = (Button) findViewById(R.id.button1);

		lstView = (ListView) findViewById(R.id.listV_main);
		lstView.setAdapter(new ListViewAdapter(this, dataArray));
		lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,
		// listContent);

		lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// lstView.setAdapter(adapter);

		// done.setOnClickListener(new Button.OnClickListener(){
		// public void onClick(View v) {
		//
		// String selected = "";
		//
		// int cntChoice = lstView.getCount();
		//
		// SparseBooleanArray sparseBooleanArray =
		// lstView.getCheckedItemPositions();
		//
		// for(int i = 0; i < cntChoice; i++){
		//
		// if(sparseBooleanArray.get(i)) {
		//
		// selected += lstView.getItemAtPosition(i).toString() + "\n";
		//
		// }
		// }
		//
		// Toast.makeText(MatchScreen.this,
		//
		// selected,
		//
		// Toast.LENGTH_LONG).show();
		// }});
		//

		lstView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(@SuppressWarnings("rawtypes") AdapterView arg0, View arg1, int arg2,
					long arg3) {

				int cntChoice = lstView.getCount();
				String selected = "";

				Item item = new Item();
				SparseBooleanArray sparseBooleanArray = lstView
						.getCheckedItemPositions();

				for (int i = 0; i < cntChoice; i++) {

					if (sparseBooleanArray.get(i) == true) {
						item = (Item) lstView.getItemAtPosition(i);
						// String str = item.get_desc();

						selected += item.get_desc().toString() + "\n";

						Toast.makeText(MatchScreen.this,

								selected,

								Toast.LENGTH_LONG).show();

					}
					;

				}
			}
		});
	}

	// @SuppressWarnings("unchecked")
	public void selectDone(View v) {

		int cntChoice = lstView.getCount();

		Item item = new Item();

		SparseBooleanArray sparseBooleanArray = lstView
				.getCheckedItemPositions();

		for (int i = 0; i < cntChoice; i++) {

			if (sparseBooleanArray.get(i) == true) {
				item = (Item) lstView.getItemAtPosition(i);
				// String str = item.get_desc();
				item.set_id(item.get_id());
				item.set_category(item.get_category());

				item.set_desc(item.get_desc());

				dataArrayTemp.add(item);
				// selected += item.get_desc().toString() + "\n";

			}

		}
		// else if(sparseBooleanArray.get(i) == false)
		// {
		// unchecked+= lstView.getItemAtPosition(i).toString() + "\n";
		//
		// }

		if (currentId == -1){
			Intent intent = new Intent()
			.setClass(MatchScreen.this, AddScreen.class);

			intent.putParcelableArrayListExtra("selectedItems", dataArrayTemp);

			if(getIntent().hasExtra("count")) {


				Bundle bundle = getIntent().getExtras();
				int count = bundle.getInt("count");

				bundle = new Bundle();
				bundle.putInt("count", count);
				intent.putExtras(bundle);
			}
			startActivityForResult(intent, 0);
		}
		else
		{
			DatabaseHandler db = new DatabaseHandler(this);
			Bundle bundle = new Bundle();
			bundle.putInt("param1", currentId);
			if (dataArrayTemp != null) {
				int match_Id = 0;
				Item selectedItem;
				for (int i = 0; i < dataArrayTemp.size(); i++) {
					selectedItem = dataArrayTemp.get(i);
					match_Id = selectedItem.get_id();

					db.addMatchItem(new Item(match_Id, currentId));

				}
			}
			Intent myIntent = new Intent(MatchScreen.this, ItemDetailsScreen.class);
			myIntent.putExtras(bundle);
			startActivityForResult(myIntent, 0);

		}
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
			Intent myIntent = new Intent(MatchScreen.this, HomeScreen.class);
			startActivityForResult(myIntent, 0);
			break;
		default:
			break;
		}

		return true;
	}
}

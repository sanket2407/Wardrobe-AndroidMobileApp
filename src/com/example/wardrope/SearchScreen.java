package com.example.wardrope;

import java.util.ArrayList;

import com.example.wardrope.DatabaseHandler;
import com.example.wardrope.HomeScreen;
import com.example.wardrope.Item;
import com.example.wardrope.R;
import com.example.wardrope.SearchScreen;
import com.example.wardrope.ViewScreen;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchScreen extends Activity implements OnItemSelectedListener {

	Spinner spinCategory;
	String category;
	// String selectedItem;
	String type;
	String stype;

	Spinner spinner2;
	ArrayAdapter<CharSequence> adapter1;
	ArrayAdapter<CharSequence> adapter2;
	ArrayAdapter<CharSequence> adapter3;
	Spinner spinner3;
	ArrayAdapter<CharSequence> adapter4;
	ArrayAdapter<CharSequence> adapter5;
	ArrayAdapter<CharSequence> adapter6, adapter7, adapter8;
	EditText txtDesc;
	EditText txtPrice;
	Button btnSearch;
	ArrayList<Item> dataArrayTemp = new ArrayList<Item>();

	String Desc = "";

	DatabaseHandler db = new DatabaseHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_screen);

		View backgroundimage = findViewById(R.id.searchscreen);
		Drawable background = backgroundimage.getBackground();
		background.setAlpha(80);
		
		LinearLayout linear = new LinearLayout(this);
		linear.setOrientation(LinearLayout.VERTICAL);

		TextView textView = new TextView(this);
		textView.setText("Category:");
		linear.addView(textView);
		spinCategory = new Spinner(this);
		linear.addView(spinCategory);

		TextView textView1 = new TextView(this);
		textView1.setText("Description:");
		linear.addView(textView1);
		txtDesc = new EditText(this);
		linear.addView(txtDesc);

		TextView textView3 = new TextView(this);
		textView3.setText("Price:");
		linear.addView(textView3);
		txtPrice = new EditText(this);

		linear.addView(txtPrice);

		TextView textView5 = new TextView(this);
		textView5.setText("Type:");
		linear.addView(textView5);
		spinner2 = new Spinner(this);
		linear.addView(spinner2);

		TextView textView6 = new TextView(this);
		textView6.setText("Sub Type:");
		linear.addView(textView6);
		spinner3 = new Spinner(this);
		linear.addView(spinner3);

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);

		txtDesc.setText(preferences.getString("Desc", null));
		txtPrice.setText(preferences.getString("Price", null));

		btnSearch = new Button(this);
		btnSearch.setText(R.string.search_button_text);
		linear.addView(btnSearch);

		btnSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String desc = null, price = null;

				if(txtDesc.getText() != null)
					desc = txtDesc.getText().toString();
				if(txtPrice.getText() != null)
					price = txtPrice.getText().toString();
				Item item = new Item();
				item.set_category(category);
				item.set_desc(desc);
				item.set_price(price);
				item.set_type(type);
				item.set_sType(stype);
				Bundle bundle = new Bundle();
				bundle.putString("param1", "Searched Items");
				bundle.putParcelable("itemtobesearched", item);

				Intent myIntent = new Intent(SearchScreen.this,
						ViewScreen.class);
				myIntent.putExtras(bundle);
				startActivityForResult(myIntent, 0);

			}
		});
		setContentView(linear);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.category, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinCategory.setAdapter(adapter);
		spinCategory.setOnItemSelectedListener(this);

		adapter1 = ArrayAdapter.createFromResource(this, R.array.typeClothes,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter2 = ArrayAdapter.createFromResource(this, R.array.typeJewellery,
				android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter3 = ArrayAdapter.createFromResource(this, R.array.typeFootwear,
				android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		adapter7 = ArrayAdapter.createFromResource(this, R.array.typeOther,
				android.R.layout.simple_spinner_item);
		adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter4 = ArrayAdapter.createFromResource(this, R.array.stypeClothes,
				android.R.layout.simple_spinner_item);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter5 = ArrayAdapter.createFromResource(this, R.array.stypeJewelley,
				android.R.layout.simple_spinner_item);
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter6 = ArrayAdapter.createFromResource(this, R.array.stypeFootwear,
				android.R.layout.simple_spinner_item);
		adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		adapter8 = ArrayAdapter.createFromResource(this, R.array.stypeOther,
				android.R.layout.simple_spinner_item);
		adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


		spinCategory.setSelection(preferences.getInt("spinCat", 0));
		spinner2.setSelection(preferences.getInt("spinType", 0));
		spinner3.setSelection(preferences.getInt("spinSType", 0));

		spinCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long rowId) {

				category = spinCategory.getItemAtPosition(position).toString();

				Log.d("CAT", category);
				if (category.equalsIgnoreCase("Apparel")) {

					spinner2.setAdapter(adapter1);
					spinner3.setAdapter(adapter4);

					spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

				}

				if (category.equalsIgnoreCase("Jewelry")) {

					spinner2.setAdapter(adapter2);
					spinner3.setAdapter(adapter5);

					spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});
					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

				}

				if (category.equalsIgnoreCase("Footwear")) {

					spinner2.setAdapter(adapter3);
					spinner3.setAdapter(adapter6);

					spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

				}
				
				if (category.equalsIgnoreCase("Other")) {

					spinner2.setAdapter(adapter7);
					spinner3.setAdapter(adapter8);

					spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// count++;
		super.onActivityResult(requestCode, resultCode, data);


	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_screen, menu);
		return true;
	}
*/
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

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
			Intent myIntent = new Intent(SearchScreen.this, HomeScreen.class);
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
	   Intent setIntent = new Intent(this, HomeScreen.class);
	   startActivity(setIntent);
	}

}

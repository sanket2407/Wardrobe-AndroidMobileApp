package com.example.wardrope;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.DigitsKeyListener;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AddScreen extends Activity implements OnItemSelectedListener {

	Spinner spinCategory;
	String category;
	// String selectedItem;
	String type;
	String stype;

	int count = 0;
	String textEncode;
	ImageView image;

	Spinner spinner2;
	ArrayAdapter<CharSequence> adapter1;
	ArrayAdapter<CharSequence> adapter2;
	ArrayAdapter<CharSequence> adapter3;
	Spinner spinner3;
	ArrayAdapter<CharSequence> adapter4;
	ArrayAdapter<CharSequence> adapter5;
	ArrayAdapter<CharSequence> adapter6;
	ArrayAdapter<CharSequence> adapter7;
	ArrayAdapter<CharSequence> adapter8;

	EditText txtDesc;
	EditText txtDate;

	EditText txtPrice;
	Button btnStore;
	Button btnMatch;
	ArrayList<Item> dataArrayTemp = new ArrayList<Item>();

	String Desc = "";
	int flag =0 ;

	Bitmap bm;
	ImageButton iv;
	DatabaseHandler db = new DatabaseHandler(this);
	LinearLayout linear;

	String encodedImage ;
	TextView textView4;
	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 999;

	DatePicker dp;
	
	SharedPreferences preferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_screen);

		linear = new LinearLayout(this);

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
		textView3.setText("Price: ( $ )");
		linear.addView(textView3);
		txtPrice = new EditText(this);
		txtPrice.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
		linear.addView(txtPrice);



		textView4 = new TextView(this);
		textView4.setText("Date:");
		linear.addView(textView4);

		dp = new DatePicker(this);

		// linear.addView(dp);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		//		textView4.setText(new StringBuilder()
		//				// Month is 0 based, just add 1
		//				.append(month + 1).append("-").append(day).append("-")
		//				.append(year).append(" "));

		// set current date into datepicker
		dp.init(year, month, day, null);


		txtDate = new EditText(this);

		txtDate.setKeyListener(DigitsKeyListener.getInstance("0123456789/-"));

		txtDate.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}

		});


		//	txtDate.setKeyListener(DigitsKeyListener.getInstance("00/55/55"));

		//txtDate.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
		linear.addView(txtDate);



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

		 preferences = getPreferences(MODE_PRIVATE);

		txtDesc.setText(preferences.getString("Desc", null));
		txtPrice.setText(preferences.getString("Price", null));
		txtDate.setText(preferences.getString("Date", null));


		btnStore = new Button(this);
		btnStore.setText("Take Picture");
		linear.addView(btnStore);

		image = new ImageView(this);


		if(getIntent().hasExtra("count")) {


			Bundle bundle = getIntent().getExtras();
			count = bundle.getInt("count");
		}

		SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
		String previouslyEncodedImage = shre.getString("image_data", "");

		if( !previouslyEncodedImage.equalsIgnoreCase("")  ){
			byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
			bm = BitmapFactory.decodeByteArray(b, 0, b.length);

			image.setImageBitmap(bm);	    	

		}

		dataArrayTemp = getIntent()
				.getParcelableArrayListExtra("selectedItems");

		TextView valueTV1 = new TextView(this);
		TextView valueTV = new TextView(this);

		valueTV1.setText("Matched With:");
		valueTV1.setTextSize(20);
		valueTV.setText(" ");
		valueTV.setTextSize(18);


		linear.addView(valueTV1);
		if (dataArrayTemp != null) {

			for (int i = 0; i < dataArrayTemp.size(); i++) {
				Item item = dataArrayTemp.get(i);

				Desc += item.get_desc() + " | ";
			}

			valueTV.setText(Desc);
			linear.addView(valueTV);

		}




		//		if(getIntent().hasExtra("byteArray")) {
		//			   // ImageView previewThumbnail = new ImageView(this);
		//			    Bitmap bm = BitmapFactory.decodeByteArray(
		//			        getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);   
		//			    
		//			    image = new ImageView(this);
		//			    image.setImageBitmap(bm);
		//			    
		//			    linear.addView(image);
		////		    	
		//			setContentView(linear);
		//	//
		//		}


		btnStore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				img(view);

			}
		});

		linear.addView(image);


		setContentView(linear);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.category2, android.R.layout.simple_spinner_item);
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
							
							int spin2 = preferences.getInt("spinType", 0);
							if (spin2 > 0)
							{
								spinner2.setSelection(preferences.getInt("spinType", 0));
								type = spinner2.getItemAtPosition(position)
										.toString();
								Log.d("Type:", type);


							}
							else{

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);
							}

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

							
							int spin3 = preferences.getInt("spinSType", 0);
							if (spin3 > 0)
							{
								spinner3.setSelection(preferences.getInt("spinSType", 0));
								stype = spinner3.getItemAtPosition(position)
										.toString();
								Log.d("Type:", stype);


							}
							else{

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);
							}
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

							int spin2 = preferences.getInt("spinType", 0);
							if (spin2 > 0)
							{
								spinner2.setSelection(preferences.getInt("spinType", 0));
								type = spinner2.getItemAtPosition(position)
										.toString();
								Log.d("Type:", type);


							}
							else{

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);
							}

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

							int spin3 = preferences.getInt("spinSType", 0);
							if (spin3 > 0)
							{
								spinner3.setSelection(preferences.getInt("spinSType", 0));
								stype = spinner3.getItemAtPosition(position)
										.toString();
								Log.d("Type:", stype);


							}
							else{

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);
							}

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

							int spin2 = preferences.getInt("spinType", 0);
							if (spin2 > 0)
							{
								spinner2.setSelection(preferences.getInt("spinType", 0));
								type = spinner2.getItemAtPosition(position)
										.toString();
								Log.d("Type:", type);


							}
							else{

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);
							}

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

							int spin3 = preferences.getInt("spinSType", 0);
							if (spin3 > 0)
							{
								spinner3.setSelection(preferences.getInt("spinSType", 0));
								stype = spinner3.getItemAtPosition(position)
										.toString();
								Log.d("Type:", stype);


							}
							else{

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);
							}

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

							int spin2 = preferences.getInt("spinType", 0);
							if (spin2 > 0)
							{
								spinner2.setSelection(preferences.getInt("spinType", 0));
								type = spinner2.getItemAtPosition(position)
										.toString();
								Log.d("Type:", type);


							}
							else{

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);
							}

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

							int spin3 = preferences.getInt("spinSType", 0);
							if (spin3 > 0)
							{
								spinner3.setSelection(preferences.getInt("spinSType", 0));
								stype = spinner3.getItemAtPosition(position)
										.toString();
								Log.d("Type:", stype);


							}
							else{

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);
							}

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

	// }
	public void matchIt() {


		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit(); // Put the values
		// from the UI
		String strDesc = txtDesc.getText().toString();
		editor.putString("Desc", strDesc);
		String strPrice = txtPrice.getText().toString();
		editor.putString("Price", strPrice);
		String strDate = txtDate.getText().toString();
		editor.putString("Date", strDate);


		if (count == 0)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if(bm != null){
				bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);   
				byte[] b = baos.toByteArray(); 
				encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


				SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
				Editor edit=shre.edit();
				edit.putString("image_data",encodedImage);
				edit.commit();
			}
		}
		int spinCat = spinCategory.getSelectedItemPosition();
		editor.putInt("spinCat", spinCat);
		int spinType = spinner2.getSelectedItemPosition();
		editor.putInt("spinType", spinType);
		int spinSType = spinner3.getSelectedItemPosition();
		editor.putInt("spinSType", spinSType);
		editor.commit();

		count =1 ;

		Bundle bundle = new Bundle();
		bundle.putInt("count", count);


		Intent myIntent = new Intent(AddScreen.this, MatchScreen.class);

		//		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		//		bm.compress(Bitmap.CompressFormat.PNG, 50, bs);
		//								myIntent.putExtra("byteArray", bs.toByteArray());

		myIntent.putExtras(bundle);

		startActivityForResult(myIntent, 0);



	}

	public void storeItem() {


		if (txtDesc.getText().length() == 0 || txtDate.getText().length() == 0
				|| txtPrice.getText().length() == 0 || bm ==null ) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Alert");

			// set dialog message
			alertDialogBuilder
			.setMessage("Fill All Information and Take Picture to store.")
			.setCancelable(false)

			.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}

		else {

			String desc = txtDesc.getText().toString();
			String price = txtPrice.getText().toString();
			String date = txtDate.getText().toString();

			DatabaseHandler db = new DatabaseHandler(this);

			// Contact im = new Contact(bm,"img"+count);
			// db.createImgEntry(im);

			db.addItem(new Item(category, desc, price, date, type, stype),
					new Item(bm));

			int current_Id = 0;

			Item itm = new Item();

			itm = db.getLastID();

			current_Id = itm.get_id();

			if (dataArrayTemp != null) {
				int match_Id = 0;

				for (int i = 0; i < dataArrayTemp.size(); i++) {
					Item item = dataArrayTemp.get(i);

					Desc = item.get_desc();
					match_Id = item.get_id();

					db.addMatchItem(new Item(match_Id, current_Id));

				}
			}
			// Testtinggg
			// String id = Integer.toString(current_Id);
			// ArrayList<Item> matchedItems = new ArrayList();
			// Item i = new Item();
			// matchedItems = db.getMatched(id);
			// //matchedItems = db.getAll();
			//
			// for(int t = 0 ; t < matchedItems.size(); t++)
			// {
			//
			// i = matchedItems.get(t);
			//
			// int m = i.get_id();
			// int n = i .get_mid();
			// String nt = Integer.toString(m);
			// String nt2 = Integer.toString(n);
			//
			// Log.d("Valuesssss are 1", nt );
			// Log.d("Valuesssss are 2", nt2 );
			//
			//
			// }
			//

			SharedPreferences preferences = getPreferences(MODE_PRIVATE);

			SharedPreferences.Editor editor = preferences.edit(); // Put the
			// values
			editor.clear();
			editor.commit();

			SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
			Editor edit=shre.edit();
			edit.clear();
			edit.commit();



			Intent myIntent = new Intent(AddScreen.this, HomeScreen.class);
			startActivity(myIntent);

		}
	}

	public void img(View V) {

		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(i, 0);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// count++;

		linear.removeView(image);
		super.onActivityResult(requestCode, resultCode, data);

		bm = (Bitmap) data.getExtras().get("data");
		image = new ImageView(this);

		image.setImageBitmap(bm);    	



		linear.addView(image);

		//setContentView(linear);


	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_screen, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			storeItem();
			break;
		case R.id.action_match:
			matchIt();
			break;
		case R.id.action_discard:
			Intent myIntent = new Intent(AddScreen.this, HomeScreen.class);
			startActivityForResult(myIntent, 0);
			break;
		default:
			break;
		}

		return true;
	} 


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			txtDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

			// set selected date into datepicker also
			dp.init(year, month, day, null);

		}
	};


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

}

package com.example.wardrope;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "DB_Wardrope1";

	// Contacts table name
	private static final String TABLE_WARDROPE = "TBL_Wardrope";

	private static final String TABLE_WARDROPE_MATCH = "TBL_Wardrope_Mtch";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_DESC = "desc";
	private static final String KEY_PRICE = "price";
	private static final String KEY_DATE = "date";
	private static final String KEY_TYPE = "type";
	private static final String KEY_SUB_TYPE = "sType";
	private static final String KEY_IMAGE = "image";

	private static final String KEY_M_ID = "m_id";

	private static final String KEY_ID_MATCH = "match_id";

	// private static final String KEY_ID_FK = "category"; ----- Not sure
	// whether to use same name or diffrnt

	public DatabaseHandler(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WARDROPE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
				+ KEY_DESC + " TEXT," + KEY_PRICE + " TEXT," + KEY_DATE
				+ " TEXT," + KEY_TYPE + " TEXT," + KEY_SUB_TYPE + " TEXT,"
				+ KEY_IMAGE + " BLOB" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);

		String CREATE_TABLE_WARDROPE_MATCH = "create table "
				+ TABLE_WARDROPE_MATCH + " (" + KEY_M_ID
				+ " integer primary key autoincrement, " + KEY_ID_MATCH
				+ " INTEGER not null, " + KEY_ID + " integer,"
				+ " FOREIGN KEY (" + KEY_ID + ") REFERENCES " + TABLE_WARDROPE
				+ " (" + KEY_ID + "));";
		db.execSQL(CREATE_TABLE_WARDROPE_MATCH);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WARDROPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WARDROPE_MATCH);

		// Create tables again
		onCreate(db);
	}

	// Adding new item
	void addItem(Item item, Item img) {

		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			img.get_image().compress(Bitmap.CompressFormat.PNG, 100, out);
			ContentValues values = new ContentValues();

			values.put(KEY_CATEGORY, item.get_category());
			values.put(KEY_DESC, item.get_desc()); //
			values.put(KEY_PRICE, item.get_price()); //
			values.put(KEY_DATE, item.get_date()); //
			values.put(KEY_TYPE, item.get_type()); //
			values.put(KEY_SUB_TYPE, item.get_sType()); //
			values.put(KEY_IMAGE, out.toByteArray());

			// Inserting Row
			db.insertOrThrow(TABLE_WARDROPE, null, values);
			db.close(); // Closing database connection
		} catch (Exception e) {

			System.out.println("Errrror is " + e);
		}
	}

	// void addMatchItem(int match_Id, int current_Id) {
	void addMatchItem(Item item) {

		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();

			values.put(KEY_ID_MATCH, item.get_mid()); //

			values.put(KEY_ID, item.get_id()); //

			// Inserting Row
			db.insertOrThrow(TABLE_WARDROPE_MATCH, null, values);
			db.close(); // Closing database connection
		} catch (Exception e) {

			System.out.println("Errrror is " + e);
		}
	}

	void removeMatchItem(int id, int mid) {

		try {
			SQLiteDatabase db = this.getWritableDatabase();

			String deleteQuery = "DELETE FROM  "
					+ TABLE_WARDROPE_MATCH + " WHERE " + KEY_ID + " = " + id + " AND " + KEY_ID_MATCH + " = " + mid;

			db.execSQL(deleteQuery);
			db.close(); // Closing database connection
		} catch (Exception e) {

			System.out.println("Errrror is " + e);
		}
	}

	public Item getLastID() {

		String selectQuery = "select MAX(" + KEY_ID + ") FROM  "
				+ TABLE_WARDROPE;

		SQLiteDatabase db = this.getWritableDatabase();
		Item item = new Item();

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {

			item.set_id(Integer.parseInt(cursor.getString(0)));
		}
		return item;

	}

	// Getting All items
	public ArrayList<Item> getAllItems() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_WARDROPE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				// item.set_id(cursor.getString(0));

				item.set_id(Integer.parseInt(cursor.getString(0)));
				item.set_category(cursor.getString(1));
				item.set_desc(cursor.getString(2));
				item.set_price(cursor.getString(3));
				item.set_date(cursor.getString(4));
				item.set_type(cursor.getString(5));
				item.set_sType(cursor.getString(6));

				byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
				Bitmap bmp = BitmapFactory
						.decodeByteArray(blob, 0, blob.length);

				item.set_image(bmp);

				// Adding contact to list
				itemList.add(item);
			} while (cursor.moveToNext());
		}

		// return contact list
		return itemList;
	}

	public ArrayList<Item> getItemsNotYetMatched(int currentItemId) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		// Select All Query
		StringBuffer queryBuffer = new StringBuffer("SELECT  * FROM " + TABLE_WARDROPE );

		if(currentItemId != -1)
		{
			queryBuffer.append(" WHERE " + KEY_ID +
					" NOT IN ( SELECT " + KEY_ID_MATCH + " FROM " + TABLE_WARDROPE_MATCH + " WHERE " + KEY_ID + " = " + currentItemId + ") AND " + KEY_ID + " <> " + currentItemId  );
		}

		String selectQuery = queryBuffer.toString();

		Log.d(" QUERY -->  " , selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				// item.set_id(cursor.getString(0));

				item.set_id(Integer.parseInt(cursor.getString(0)));
				item.set_category(cursor.getString(1));
				item.set_desc(cursor.getString(2));
				item.set_price(cursor.getString(3));
				item.set_date(cursor.getString(4));
				item.set_type(cursor.getString(5));
				item.set_sType(cursor.getString(6));

				byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
				Bitmap bmp = BitmapFactory
						.decodeByteArray(blob, 0, blob.length);

				item.set_image(bmp);

				// Adding contact to list
				itemList.add(item);
			} while (cursor.moveToNext());
		}

		// return contact list
		return itemList;
	}



	public ArrayList<Item> getMatched(String id) {
		ArrayList<Item> contactList = new ArrayList<Item>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_WARDROPE_MATCH, new String[] { KEY_ID,
				KEY_ID_MATCH }, KEY_ID + "=?", new String[] { id }, null, null,
				null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				item.set_id(Integer.parseInt(cursor.getString(0)));
				item.set_mid(Integer.parseInt(cursor.getString(1)));

				// Adding contact to list
				contactList.add(item);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Getting items Count
	public int getItemsCount(String category) {

		String countQuery;
		if (category.equalsIgnoreCase("All")) {
			countQuery = "SELECT  * FROM " + TABLE_WARDROPE;

		} else {
			countQuery = "SELECT  * FROM " + TABLE_WARDROPE
					+ " WHERE " + KEY_CATEGORY + " = '" + category + "'";
		}
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cursorCount = cursor.getCount();
		cursor.close();
		// return count
		return cursorCount;
	}

	public ArrayList<Item> searchItem(Item item) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +
		// " WHERE desc = '" +category +"'";

		StringBuffer queryBuffer = new StringBuffer("SELECT * FROM " + TABLE_WARDROPE + " WHERE ");
		StringBuffer searchClauseBuffer = new StringBuffer();

		int queryParamCount = 0;
		if(item.get_category()!=null && !item.get_category().equalsIgnoreCase("All"))
		{
			
			if(queryParamCount > 0)
				searchClauseBuffer.append(" AND ");

			queryParamCount++;
			searchClauseBuffer.append(" " + KEY_CATEGORY).append(" =\"" + item.get_category() + "\"");

			if(item.get_type()!=null && item.get_type().length()>0)
			{
				if(queryParamCount > 0)
					searchClauseBuffer.append(" AND ");

				queryParamCount++;
				searchClauseBuffer.append(" " + KEY_TYPE).append(" =\"" + item.get_type() + "\"");
			}
			if(item.get_sType()!=null && item.get_sType().length()>0)
			{
				if(queryParamCount > 0)
					searchClauseBuffer.append(" AND ");

				queryParamCount++;
				searchClauseBuffer.append(" " + KEY_SUB_TYPE).append(" =\"" + item.get_sType() + "\"");
			}
		}
		if(item.get_desc()!=null && item.get_desc().length()>0)
		{
			if(queryParamCount > 0)
				searchClauseBuffer.append(" AND ");

			queryParamCount++;
			searchClauseBuffer.append(" " + KEY_DESC).append(" LIKE \"%" + item.get_desc() + "%\"");
		}			
		if(item.get_price()!=null && item.get_price().length()>0)
		{
			if(queryParamCount > 0)
				searchClauseBuffer.append(" AND ");

			queryParamCount++;
			searchClauseBuffer.append( " " + KEY_PRICE).append(" =\"" + item.get_price() + "\"");
		}

		SQLiteDatabase db = this.getReadableDatabase();

		queryBuffer.append(searchClauseBuffer);

		String query = queryBuffer.toString();

		if(query.endsWith("WHERE ") && item.get_category().equalsIgnoreCase("All"))
			query = "SELECT * FROM " + TABLE_WARDROPE ;

		Log.d("query  ", query);
		Cursor cursor = db.rawQuery(query,null);


		Item fetchedItem;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				fetchedItem = new Item();
				fetchedItem.set_id(Integer.parseInt(cursor.getString(0)));
				fetchedItem.set_category(cursor.getString(1));
				fetchedItem.set_desc(cursor.getString(2));
				fetchedItem.set_price(cursor.getString(3));
				fetchedItem.set_date(cursor.getString(4));
				fetchedItem.set_type(cursor.getString(5));
				fetchedItem.set_sType(cursor.getString(6));



				byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
				Bitmap bmp = BitmapFactory
						.decodeByteArray(blob, 0, blob.length);

				fetchedItem.set_image(bmp);

				// Adding contact to list
				itemList.add(fetchedItem);
			} while (cursor.moveToNext());
		}

		// return contact list
		return itemList;
	}

	public ArrayList<Item> getCategoryWise(String category) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +
		// " WHERE desc = '" +category +"'";

		SQLiteDatabase db = this.getReadableDatabase();

		// Cursor cursor = db.rawQuery(selectQuery,null);

		Cursor cursor = db.query(TABLE_WARDROPE, new String[] { KEY_ID,
				KEY_CATEGORY, KEY_DESC, KEY_PRICE, KEY_DATE, KEY_TYPE, KEY_SUB_TYPE, KEY_IMAGE },
				KEY_CATEGORY + "=?", new String[] { category }, null, null,
				null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				item.set_id(Integer.parseInt(cursor.getString(0)));
				item.set_category(cursor.getString(1));
				item.set_desc(cursor.getString(2));
				item.set_price(cursor.getString(3));
				item.set_date(cursor.getString(4));
				item.set_type(cursor.getString(5));
				item.set_sType(cursor.getString(6));



				byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
				Bitmap bmp = BitmapFactory
						.decodeByteArray(blob, 0, blob.length);

				item.set_image(bmp);

				// Adding contact to list
				itemList.add(item);
			} while (cursor.moveToNext());
		}

		// return contact list
		return itemList;
	}

	// getDetailsIdWise() added by Sweta Patel to get item details based on id
	public ArrayList<Item> getDetailsIdWise(String id) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +
		// " WHERE desc = '" +category +"'";

		SQLiteDatabase db = this.getReadableDatabase();

		// Cursor cursor = db.rawQuery(selectQuery,null);

		Cursor cursor = db.query(TABLE_WARDROPE, new String[] { KEY_ID, KEY_CATEGORY, KEY_DESC, KEY_PRICE, KEY_DATE, KEY_TYPE, KEY_SUB_TYPE, KEY_IMAGE },
				KEY_ID + "=?", new String[] { id }, null, null,
				null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				// item.set_id(cursor.getString(0));

				item.set_id(Integer.parseInt(cursor.getString(0)));
				item.set_category(cursor.getString(1));
				item.set_desc(cursor.getString(2));
				item.set_price(cursor.getString(3));
				item.set_date(cursor.getString(4));
				item.set_type(cursor.getString(5));
				item.set_sType(cursor.getString(6));

				byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
				Bitmap bmp = BitmapFactory
						.decodeByteArray(blob, 0, blob.length);

				item.set_image(bmp);

				// Adding contact to list
				itemList.add(item);
			} while (cursor.moveToNext());
		}

		// return contact list
		return itemList;
	}
}


package com.example.wardrope;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

	int _id;
	int _mid;

	String _category;
	String _desc;
	String _price;
	String _date;
	String _type;
	String _sType;
	Bitmap _image;

	// Empty constructor
	public Item() {

	}

	public Item(int id, String category, String desc, String price,
			String date, String type, String stype, Bitmap img) {
		this._id = id;
		this._desc = desc;
		this._category = category;
		this._date = date;
		this._price = price;
		this._type = type;
		this._sType = stype;
		this._image = img;

	}

	public Item(String category, String desc, String price, String date,
			String type, String stype) {
		this._desc = desc;
		this._category = category;
		this._date = date;
		this._price = price;
		this._type = type;
		this._sType = stype;

	}

	public Item(int mid, int id) {
		this._id = id;
		this._mid = mid;
	}

	public Item(Bitmap img) {
		this._image = img;

	}

	public Item(Parcel in) {
		_id = in.readInt();

		_category = in.readString();
		_desc = in.readString();
	}

	public int get_id() {
		return this._id;
	}

	public void set_id(int i) {
		this._id = i;
	}

	public int get_mid() {
		return this._mid;
	}

	public void set_mid(int i) {
		this._mid = i;
	}

	public String get_category() {
		return this._category;
	}

	public void set_category(String category) {
		this._category = category;
	}

	public String get_desc() {
		return this._desc;
	}

	public void set_desc(String desc) {
		this._desc = desc;
	}

	public String get_price() {
		return this._price;
	}

	public void set_price(String price) {
		this._price = price;
	}

	public String get_date() {
		return this._date;
	}

	public void set_date(String date) {
		this._date = date;
	}

	public String get_type() {
		return this._type;
	}

	public void set_type(String type) {
		this._type = type;
	}

	public String get_sType() {
		return this._sType;
	}

	public void set_sType(String sType) {
		this._sType = sType;
	}

	public Bitmap get_image() {
		return this._image;
	}

	public void set_image(Bitmap image) {
		this._image = image;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
		public Item createFromParcel(Parcel in) {
			// Log.d (TAG, "createFromParcel()");
			return new Item(in);
		}

		public Item[] newArray(int size) {
			// Log.d (TAG, "createFromParcel() newArray ");
			return new Item[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// Log.d (TAG, "writeToParcel");
		dest.writeInt(_id);

		dest.writeString(_category);
		dest.writeString(_desc);

	}
}

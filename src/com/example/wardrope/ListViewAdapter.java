package com.example.wardrope;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private static ArrayList<Item> itemDetailsrrayList;

	private LayoutInflater l_Inflater;

	public ListViewAdapter(Context context, ArrayList<Item> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.itemlist, null);
			holder = new ViewHolder();
			holder.txt_itemDescription = (TextView) convertView
					.findViewById(R.id.itemDescription);
			holder.txt_itemCategory = (TextView) convertView
					.findViewById(R.id.itemCategory);

			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_itemDescription.setText(itemDetailsrrayList.get(position)
				.get_desc());
		holder.txt_itemCategory.setText(itemDetailsrrayList.get(position)
				.get_category());
		holder.itemImage.setImageBitmap(itemDetailsrrayList.get(position)
				.get_image());

		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemDescription;
		TextView txt_itemCategory;
		ImageView itemImage;
	}
}

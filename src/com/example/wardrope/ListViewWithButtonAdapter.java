package com.example.wardrope;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListViewWithButtonAdapter extends BaseAdapter implements OnClickListener  {
	private static ArrayList<Item> itemDetailsrrayList;

	private LayoutInflater l_Inflater;
	DatabaseHandler db;
	int selectedId;

	public ListViewWithButtonAdapter(Context context, ArrayList<Item> results, int selectedId) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
		this.selectedId = selectedId;
		db = new DatabaseHandler(context);
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
			convertView = l_Inflater.inflate(R.layout.item_list_with_button, null);
			holder = new ViewHolder();
			holder.txt_itemDescription = (TextView) convertView
					.findViewById(R.id.itemDesc);
			holder.txt_itemCategory = (TextView) convertView
					.findViewById(R.id.itemCat);

			holder.itemImage = (ImageView) convertView.findViewById(R.id.pic);
			holder.delete = (Button) convertView.findViewById(R.id.btnDelete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Item entry = itemDetailsrrayList.get(position);
		holder.txt_itemDescription.setText(entry.get_desc());
		holder.txt_itemCategory.setText(entry.get_category());
		holder.itemImage.setImageBitmap(entry.get_image());

		
		 Button btnRemove = (Button) convertView.findViewById(R.id.btnDelete);
	        btnRemove.setFocusableInTouchMode(false);
	        btnRemove.setFocusable(false);	
	        btnRemove.setOnClickListener(this);
	        // Set the entry, so that you can capture which item was clicked and
	        // then remove it
	        // As an alternative, you can use the id/position of the item to capture
	        // the item
	        // that was clicked.
	        btnRemove.setTag(entry);

	        holder.delete = btnRemove;
		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemDescription;
		TextView txt_itemCategory;
		ImageView itemImage;
		Button delete;
	}

	@Override
	public void onClick(View view) {
		 Item entry = (Item) view.getTag();
	        itemDetailsrrayList.remove(entry);
	        // listPhonebook.remove(view.getId())
	        db.removeMatchItem(selectedId, entry.get_id());
	        notifyDataSetChanged();

		
	}
}

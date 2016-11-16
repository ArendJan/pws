package com.svshizzle.pws.smartfridge.adapter;

/**
 * Created by Arend-Jan on 28-10-2016.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.model.Item;

public class HomeListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Item> data;
    private static LayoutInflater inflater=null;


    public HomeListAdapter(Activity a, ArrayList<Item> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.home_row, null);

        TextView title = (TextView)vi.findViewById(R.id.Name); // Name
        TextView barcode = (TextView)vi.findViewById(R.id.Barcode); // barcode
        TextView text = (TextView)vi.findViewById(R.id.Text);
        Item item =  data.get(position);

        // Setting all values in listview
        title.setText(item.getTitle());
        barcode.setText(item.getBarcode());
        text.setText(item.getOpen() + " open & " +item.getClosed() + " closed");
        return vi;
    }
}
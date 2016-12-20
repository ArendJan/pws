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
import android.widget.CheckBox;
import android.widget.TextView;
import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.model.ShoppingListItem;

public class ListListAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<ShoppingListItem> data;
    private LayoutInflater inflater=null;
    private boolean[] checkBoxState;




    public ListListAdapter(Activity a, ArrayList<ShoppingListItem> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        checkBoxState = new boolean[d.size()];

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row=convertView;
        ShoppingListHolder holder;
        if (row == null){

            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.shopping_row, parent, false);

            holder = new ShoppingListHolder();


            holder.txtTitle = (TextView) row.findViewById(R.id.shopping_list_row_title);
            holder.chkSelect = (CheckBox) row.findViewById(R.id.shopping_list_row_checkbox);
            holder.amount = (TextView) row.findViewById(R.id.shopping_list_row_amount);

            row.setTag(holder);

        }
        else{
            holder = (ShoppingListHolder) row.getTag();
        }

        ShoppingListItem item = data.get(position);
        holder.chkSelect.setChecked(item.isChecked());
        holder.txtTitle.setText(item.getTitle());
        holder.amount.setText(item.getAmountString());
        checkBoxState[position] = item.isChecked();
        holder.chkSelect.setChecked(checkBoxState[position]);
        holder.chkSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean value = ((CheckBox)view).isChecked();
                checkBoxState[position] = !value;
                if(((CheckBox)view).isChecked()){
                    checkBoxState[position]=true;
                    data.get(position).setCheck(true);
                }else{
                    checkBoxState[position]=false;
                    data.get(position).setCheck(false);
                }

            }
        });
        holder.chkSelect.setTag(position);
        return row;
    }


    public ArrayList<ShoppingListItem> getData(){
        return data;
    }




    private static class ShoppingListHolder {

        TextView amount;
         TextView txtTitle;
         CheckBox chkSelect;
    }
}
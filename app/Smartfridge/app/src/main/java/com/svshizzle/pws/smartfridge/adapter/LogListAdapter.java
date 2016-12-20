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
import com.svshizzle.pws.smartfridge.model.LogItem;
import org.json.JSONException;
import org.json.JSONObject;

public class LogListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<LogItem> data;
    private static LayoutInflater inflater=null;


    public LogListAdapter(Activity a, ArrayList<LogItem> d) {
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
            vi = inflater.inflate(R.layout.log_row, null);

        TextView title = (TextView)vi.findViewById(R.id.log_row_title); // Name
        TextView tijd = (TextView)vi.findViewById(R.id.log_row_time); // barcode
        TextView extratext = (TextView)vi.findViewById(R.id.log_row_extratext);
        LogItem item =  data.get(position);

        // Setting all values in listview
        switch (item.getScript()) {
            case "markJob.php":
                title.setText(markJob(item));
                break;
            case "itemChange.php":
                title.setText(changeItem(item));
                break;
            case "addJob.php":
                title.setText(addJob(item));
                break;
            default:

                title.setText(item.getScript());
                break;
        }

        tijd.setText(item.getTime());
        extratext.setText("");

        return vi;
    }

    private String markJob(LogItem item){
        try {
            JSONObject object = item.getJobDetails();

            switch (object.getString("Type")){
                case "shutdown":
                    return activity.getString(R.string.log_item_markJob_shutdown, String.valueOf(object.getInt("ID")));
                case "restart":
                    return activity.getString(R.string.log_item_markJob_reboot, String.valueOf(object.getInt("ID")));
                case "text":
                    return activity.getString(R.string.log_item_markJob_text, object.getString("Text"), String.valueOf(object.getInt("ID")));
                case "barcode":
                    return activity.getString(R.string.log_item_markJob_barcode, object.getString("Barcode"), String.valueOf(object.getInt("ID")));
                default:
                    return activity.getString(R.string.log_item_markJob, String.valueOf(object.getInt("ID")));
            }

        }catch (JSONException e){


            return "oops";
        }
    }
    private String changeItem(LogItem item){
        try {
            JSONObject object = item.getItemDetails();
            JSONObject params = item.getParameters();

            switch (params.getString("Action")){
                case "add":
                    return activity.getString(R.string.log_item_itemChange_add, object.getString("Title"));
                case "del":
                    return activity.getString(R.string.log_item_itemChange_del, object.getString("Title"));
                case "open":
                    return activity.getString(R.string.log_item_itemChange_open, object.getString("Title"));

                default:
                    return activity.getString(R.string.log_item_itemChange, object.getString("Title"));
            }

        }catch (JSONException e){


            return "oops";
        }
    }
    private String addJob(LogItem item){
        try {

            JSONObject object = item.getParameters();

            switch (object.getString("Type")) {
                case "shutdown":
                    return activity.getString(R.string.log_item_createJob_shutdown);
                case "restart":
                    return activity.getString(R.string.log_item_createJob_reboot);
                case "text":
                    return activity.getString(R.string.log_item_createJob_text, object.getString("Text"));
                case "barcode":
                    return activity.getString(R.string.log_item_createJob_barcode, object.getString("Barcode"));
                default:
                    return activity.getString(R.string.log_item_createJob);
            }
        }catch (JSONException e){


            return "oops";
        }
    }
}
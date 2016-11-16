package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.adapter.HomeListAdapter;
import com.svshizzle.pws.smartfridge.adapter.LogListAdapter;
import com.svshizzle.pws.smartfridge.api.Smartfridge;
import com.svshizzle.pws.smartfridge.model.Item;
import com.svshizzle.pws.smartfridge.model.LogItem;

import java.util.ArrayList;


public class LogFragment extends Fragment {
    ListView listView;
    LogListAdapter adapter;
    Activity activity;

    public LogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        View rootView = inflater.inflate(R.layout.activity_log_fragment, container, false);
        final ArrayList<LogItem> itemArrayList = new ArrayList<>();
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void getLogDone(ArrayList<LogItem> items) {


                    Log.d("dit", "is eets");
                    for(int x = 0;x<items.size();x++){
                        if(!items.get(x).getScript().equals("contains.php")) {
                            itemArrayList.add(items.get(x));
                        }
                    }
                    listView = (ListView)activity.findViewById(R.id.logListView);
                    adapter = new LogListAdapter(activity, itemArrayList);
                    listView.setAdapter(adapter);


            }
        };
        smartfridge.getLog();



        return rootView;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




}


package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.adapter.HomeListAdapter;
import com.svshizzle.pws.smartfridge.adapter.LogListAdapter;
import com.svshizzle.pws.smartfridge.api.Smartfridge;
import com.svshizzle.pws.smartfridge.api.SmartfridgeSave;
import com.svshizzle.pws.smartfridge.model.Item;
import com.svshizzle.pws.smartfridge.model.LogItem;

import org.json.JSONException;

import java.util.ArrayList;


public class LogFragment extends Fragment {
    ListView listView;
    LogListAdapter adapter;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    public LogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_log_fragment, container, false);
        activity = getActivity();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                createSwipeRefresh(activity);
                getLog();
                Smartfridge smartfridge = new Smartfridge(getActivity());

                try {


                    ArrayList<LogItem> arrayList = smartfridge.processLog(SmartfridgeSave.getLogBackup(getActivity()));
                    createList(arrayList);
                }catch (JSONException e){
                    Log.d("exception", e.getLocalizedMessage());
                }


            }
        }.execute();



        return rootView;
    }
    private void createSwipeRefresh(Activity activity){
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.logScrollView);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLog();
            }
        });
    }
    private void getLog(){
        swipeRefreshLayout.setRefreshing(true);

        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void getLogDone(ArrayList<LogItem> items) {
createList(items);
            }
            @Override
            public void getLogError(String e){
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Oops, refreshing failed. Errormessage:"+e, Toast.LENGTH_LONG).show();
            }
        };
        smartfridge.getLog();
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void createList(ArrayList<LogItem> items){
        final ArrayList<LogItem> itemArrayList = new ArrayList<>();

        Log.d("dit", "is eets");
        for(int x = 0;x<items.size();x++){
            if(!items.get(x).getScript().equals("contains.php")) {
                itemArrayList.add(items.get(x));
            }
        }
        listView = (ListView)activity.findViewById(R.id.logListView);
        adapter = new LogListAdapter(activity, itemArrayList);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);

    }



}


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
import com.svshizzle.pws.smartfridge.api.Smartfridge;
import com.svshizzle.pws.smartfridge.api.SmartfridgeSave;
import com.svshizzle.pws.smartfridge.model.Item;

import com.svshizzle.pws.smartfridge.model.LogItem;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Activity activity;
    ListView listView;
    HomeListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home_fragment, container, false);


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
                requestList();
                Smartfridge smartfridge = new Smartfridge(getActivity());

                try {


                    ArrayList<Item> arrayList = smartfridge.processContains(SmartfridgeSave.getContainsBackup(getActivity()));
                    createList(arrayList);
                }catch (JSONException e){
                    Log.d("exception", e.getLocalizedMessage());
                }
            }
        }.execute();

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

    void requestList(){

        Smartfridge smartfridge = new Smartfridge(getActivity()){

            @Override
            public void containsDone(ArrayList<Item> items) {

                createList(items);
            }

            @Override
            public void containsError(String e) {

                Log.d("ebola", e);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Oops, refreshing failed. Errormessage:"+e, Toast.LENGTH_LONG).show();
                //Shit, vincent did something wrong.
            }
        };
        if(!smartfridge.isSignedin()){
           Log.d("not", "signedin");
        }
        Log.d("uid", smartfridge.getUserid());
        smartfridge.contains();





    }
    void createList(ArrayList<Item> items){
        final ArrayList<Item> itemArrayList = new ArrayList<>();
        Log.d("dit", "is eets");
        for(int x = 0;x<items.size();x++){
            itemArrayList.add(items.get(x));
        }
        listView = (ListView)activity.findViewById(R.id.homeListView);
        adapter = new HomeListAdapter(activity, itemArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = itemArrayList.get(i);
                ItemDialog dialog = new ItemDialog(getActivity(), item);
                dialog.show();
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    void createSwipeRefresh(Activity rootView) {




                swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.homeScrollView);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        requestList();
                    }
                });



    }


}


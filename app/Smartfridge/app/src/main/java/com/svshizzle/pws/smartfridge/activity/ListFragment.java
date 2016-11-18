package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;


import android.app.LauncherActivity;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ListFragment extends Fragment {
    Activity activity;

    public ListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("whut?", "deze heeft eets");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list_fragment, container, false);

        activity = getActivity();
        Log.d("listfragment", "is rolling");
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                makeList();



            }
        }.execute();



        return rootView;
    }

    private void makeList(){
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void getLogDone(ArrayList<LogItem> items) {

                createList(items);
            }
            @Override
            public void getLogError(String e){

                Toast.makeText(getActivity(), "Oops, refreshing failed. Errormessage:"+e, Toast.LENGTH_LONG).show();
            }
        };
        smartfridge.getLog("ASC");
    }

    private void createList(ArrayList<LogItem> items){
        ArrayList<ListItems> listItemses= new ArrayList<ListItems>();
        for(int x = 0; x<items.size();x++){
            LogItem item = items.get(x);
            if(item.getScript().equals("itemChange.php")) {
                try {

                    JSONObject jsonObject = item.getItemDetails();
                    String barcode = jsonObject.getString("Barcode");

                    String action = item.getParameters().getString("Action");
                    int change = 1;
                    if (action.equals("add") || action.equals("open")) {
                        change = -1;
                    }
                    int index = indexOfListItem(listItemses, barcode);
                    if (index == -1) {
                        listItemses.add(new ListItems(change, barcode));
                    } else {
                        listItemses.get(index).setAmount(change + listItemses.get(index).getAmount());
                    }


                } catch (JSONException e) {
                    Log.d("createlist error", e.getLocalizedMessage());
                }


            }else if(item.getScript().equals("logReset.php")){
                listItemses = new ArrayList<ListItems>();
            }
        }
        for(int x = 0;x<listItemses.size();x++){
            Log.d("createlist:", listItemses.get(x).toString());
        }

    }
    private int indexOfListItem(ArrayList<ListItems> listItemses, String barcode){
        for(int y = 0; y<listItemses.size(); y++){
            if(listItemses.get(y).getBarcode().equals(barcode)){
                return y;
            }
        }
        return -1;
    }

    private class ListItems{
        private int amount = 0;
        private String barcode = "";

        public ListItems(int amount, String barcode) {
            this.amount = amount;
            this.barcode = barcode;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }
        public void increase(){
            this.amount++;
        }
        public void decrease(){
            this.amount--;
        }

        @Override
        public String toString() {
            return "barcode:"+barcode+"aantal:"+amount;
        }
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


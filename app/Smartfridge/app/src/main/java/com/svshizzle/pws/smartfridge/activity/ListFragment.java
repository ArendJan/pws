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

public class ListFragment extends Fragment {
    Activity activity;

    public ListFragment() {

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
        smartfridge.getLog();
    }

    private void createList(ArrayList<LogItem> items){
        ArrayList<ListItems> listItemses= new ArrayList<ListItems>();
        for(int x = 0; x<items.size();x++){
            LogItem item = items.get(x);
            if(item.getScript().equals("itemChange.php")){
                if(listItemses.indexOf())
                JSONObject jsonObject = item.getItemDetails();
                String barcode = jsonObject.getString("Barcode");
                String action = jsonObject.getString("Action");

                if(action.equals("add")){

                }
            }
        }
    }

    private class ListItems{
        private int amount = 0;
        private String barcode = "";

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


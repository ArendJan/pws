package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;


import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.adapter.HomeListAdapter;
import com.svshizzle.pws.smartfridge.adapter.ListListAdapter;
import com.svshizzle.pws.smartfridge.api.Smartfridge;
import com.svshizzle.pws.smartfridge.api.SmartfridgeSave;
import com.svshizzle.pws.smartfridge.model.Item;

import com.svshizzle.pws.smartfridge.model.LogItem;
import com.svshizzle.pws.smartfridge.model.ShoppingListItem;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    Activity activity;
    ArrayList<ShoppingListItem> shoppingListItems;
    ListListAdapter adapter;
    ProgressDialog dialog;
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

            @Override
            public void containsError(String e) {

            }

            @Override
            public void containsDone(ArrayList<Item> items) {

            }
        };
        smartfridge.getLog("ASC");
    }

    private void createList(final ArrayList<LogItem> items){
        ArrayList<ListItems> listItemses= new ArrayList<ListItems>();
        for(int x = 0; x<items.size();x++){
            LogItem item = items.get(x);
            if(item.getScript().equals("itemChange.php")) {
                try {

                    JSONObject jsonObject = item.getItemDetails();
                    String barcode = jsonObject.getString("Barcode");

                    String action = item.getParameters().getString("Action");
                    int change = 0;
                    if (action.equals("add") || action.equals("open")) {
                        change = -1;
                    }else if(action.equals("del") || action.equals("delOpen") || action.equals("delClosed")){
                        change = 1;
                    }
                    int index = indexOfListItem(listItemses, barcode);
                    if (index == -1 &&change==1) {
                        String title = item.getItemDetails().getString("Title");
                        Log.d("Title", title);
                        listItemses.add(new ListItems(change, barcode, title));
                    } else if(index!=-1){
                        listItemses.get(index).setAmount(change + listItemses.get(index).getAmount());
                    }


                } catch (JSONException e) {
                    Log.d("createlist error", e.getLocalizedMessage());
                }


            }else if(item.getScript().equals("logReset.php")){
                listItemses = new ArrayList<ListItems>();
            }
        }

        shoppingListItems = new ArrayList<>();

        for(int x = 0;x<listItemses.size();x++){
            Log.d("dit", listItemses.get(x).toString());
            if(listItemses.get(x).getAmount()>0) {
                ShoppingListItem item = new ShoppingListItem(listItemses.get(x).getBarcode(), listItemses.get(x).getTitle(), listItemses.get(x).getAmount());

                shoppingListItems.add(item);

            }
        }
        Log.d("aantal2 =", "eets"+shoppingListItems.size());
        final ListView listView =(ListView) activity.findViewById(R.id.shoppingListListView);

        adapter = new ListListAdapter(activity, shoppingListItems);
        listView.setAdapter(adapter);

        Button b = (Button) activity.findViewById(R.id.shoppingListButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog= new ProgressDialog(activity);
                dialog.setTitle("Sending this delicious list to the fridge...");
                dialog.setMessage("Starting...");
                dialog.setCancelable(false);
                dialog.show();
                ArrayList<ShoppingListItem> itemses = adapter.getData();
                ArrayList<String> list = new ArrayList<String>();


                for(int x = 0; x<itemses.size();x++){
                    ShoppingListItem n = itemses.get(x);
                    Log.d("ding=", n.getTitle());

                    if(n.isChecked()){
                        list.add(n.getAmount()+" "+n.getTitle());
                        dialog.setMessage("Adding item:"+ n.getTitle());

                    }

                }
                if(list.size()==0){
                    Toast.makeText(activity, "With an empty shopping list even my cat can do it :P", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    return;
                }
                Log.d("size", list.size()+"aa");
                Smartfridge smartfridge = new Smartfridge(activity){
                    @Override
                    public void listJobDone() {
                        Log.d("Yeah!!", "We dit it");

                        Toast.makeText(activity, "The list is uploaded!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }

                    @Override
                    public void listJobError(String e) {
                        Log.d("fuck", e);
                        Toast.makeText(activity, "Oops, either the coding is bad or your wifi has something. Error:"+e, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                };
                smartfridge.listJob(list);
                dialog.setMessage("Uploading list...");
            }
        });






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
        private String title = "";
        private int amount = 0;
        private String barcode = "";

        public ListItems(int amount, String barcode, String title) {
            this.amount = amount;
            this.barcode = barcode;
            this.title = title;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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


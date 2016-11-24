package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;


import android.icu.text.StringPrepParseException;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    Activity activity;
    ListView listView;
    HomeListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Switch aSwitch;
    private View view;
    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_home_fragment, container, false);
        view = rootView;

        activity = getActivity();
        Log.d("wtf", "is aan de hand");
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d("hier gebeurt iets", "maar wat");
                createSwipeRefresh(activity);
                createSwitch(activity);
                requestList(false);
                checkActive();
                createActive(SmartfridgeSave.getActiveBackup(activity));
                Smartfridge smartfridge = new Smartfridge(getActivity());
                Log.d("dit", "moet wel gebeuren");

                try {

                    Log.d("what", "moet gebeuren");
                    ArrayList<Item> arrayList = smartfridge.processContains(SmartfridgeSave.getContainsBackup(getActivity()));
                    Log.d("get", "createlist");
                    createList(arrayList);
                    Log.d("einde", "van verhaal");
                }catch (JSONException e){
                    Log.d("exception", e.getLocalizedMessage());
                    Log.d("Exception", "inception");
                }catch (Exception e){
                    Log.d("kut", e.getLocalizedMessage());
                    Log.d("Exception", "inception2");
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

    void requestList(boolean swit){
        Log.d("request", "start");
swipeRefreshLayout.setRefreshing(true);
        Smartfridge smartfridge = new Smartfridge(getActivity()){

            @Override
            public void containsDone(ArrayList<Item> items) {

                createList(items);
            }

            @Override
            public void containsError(String e) {

                Log.d("ebola", e);
                if(swipeRefreshLayout== null){return;}
                swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(getActivity(), "Oops, refreshing failed. Errormessage:"+e, Toast.LENGTH_LONG).show();
                //Shit, vincent did something wrong.
            }
        };
        if(!smartfridge.isSignedin()){
           Log.d("not", "signedin");
        }
        Log.d("uid", smartfridge.getUserid());
        String sort = "opened+closed";
        if(swit ){
            sort = "everything";
        }
        smartfridge.contains(sort);





    }
    void createList(ArrayList<Item> items){
        final ArrayList<Item> itemArrayList = new ArrayList<>();
        Log.d("dit", "is eets");
        for(int x = 0;x<items.size();x++){
            itemArrayList.add(items.get(x));
        }
        createSwipeRefresh(activity);
        listView = (ListView)activity.findViewById(R.id.homeListView);
        if(listView==null || swipeRefreshLayout==null){
            Log.d("kukut", "null");
            return;
        }

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
        Log.d("createlist", "end");
    }

    void createSwipeRefresh(Activity rootView) {




                swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.homeScrollView);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        requestList(aSwitch.isChecked());
                        checkActive();
                    }
                });



    }

    void checkActive(){

        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void getActiveDone(String output) {
                createActive(output);
            }

            @Override
            public void getActiveError(String error) {
                Toast.makeText(getActivity(), "Oops, getAcitive error:"+error, Toast.LENGTH_LONG).show();
            }
        };
        smartfridge.getActive();
    }
    void createSwitch(Activity activity){
        aSwitch = (Switch) activity.findViewById(R.id.homeSwitch);
        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                requestList(isChecked);
                // true if the switch is in the On position
            }
        });
    }

    void createActive(String output) {
        Log.d("what", output);
        Log.d("What is", "mos");
        try {


            TextView textView = (TextView) getActivity().findViewById(R.id.homeActiveTextView);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            JSONObject object = new JSONObject(output);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = format.parse(object.getString("Time"));
            Date date2 = format.parse(currentDateandTime);
            Log.d("tijd`1", date1.toString());
            Log.d("Tijd2", date2.toString());
            long millis = date2.getTime() - date1.getTime();
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            String text = "Online! Last seen " + hms + " ago.";
            if (millis > 10 * 60 * 1000) {
                text = "Down: last seen " + hms + " ago.";
            }
            textView.setText(text);

        } catch (JSONException e) {
            //Fuck
            Log.d("oops", e.getLocalizedMessage());
        } catch (ParseException e) {
            Log.d("lelele", e.getLocalizedMessage());
        }catch (Exception e){
            Log.d("eets", e.getLocalizedMessage());
        }

    }


}


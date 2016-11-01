package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;



import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.adapter.HomeListAdapter;
import com.svshizzle.pws.smartfridge.model.Item;

import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    String APIURL = "";
    String UserId = "";
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



        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences(getResources().getString(R.string.SharedPreferencesName), 0);
        UserId = sharedPreferences.getString(getResources().getString(R.string.SharedPreferencesUserId), "");
        APIURL = sharedPreferences.getString(getResources().getString(R.string.SharedPreferencesAPIURL), "");

        activity = getActivity();

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                createSwipeRefresh(activity);
                createListContains();
                super.onPostExecute(aVoid);
            }
        }.execute();

        return rootView;
    }

    private void createListContains() {
        swipeRefreshLayout.setRefreshing(true);
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId", UserId);

        }catch (JSONException e ){
        }

        RequestClassPost request = new RequestClassPost(getActivity(),jsonObject ){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(requestReturn.isError()){

                }else {
                    createList(requestReturn.getResponse());
                }
            }
        };
        String url = APIURL + "contains";
        request.execute(url);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void createList(String json){
        final ArrayList<Item> itemArrayList = new ArrayList<>();
        try{
            JSONArray reader = new JSONArray(json);
            for(int x = 0; x<reader.length(); x++){

                JSONObject object = reader.getJSONObject(x);
                Item item = new Item(object.getInt("Closed"),object.getInt("Open"), object.getString("Name"), object.getInt("Id"), object.getString("Barcode"));
                itemArrayList.add(item);
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
        }catch (JSONException e){

            //Shit, vincent did a shit job
        }finally {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    void createSwipeRefresh(Activity rootView) {




                swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.homeScrollView);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        createListContains();
                    }
                });



    }


}


package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.request.RequestClass;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    String APIURL = "";
    String UserId = "";
    Activity activity;
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
        Log.d("'asdfadsf", "ebola");
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId", UserId);

        }catch (JSONException e ){
            Toast.makeText(getActivity(),"LOLZ, something went wrong", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getActivity(),"LOLZ, something went wrong", Toast.LENGTH_LONG).show();
        RequestClassPost request = new RequestClassPost(getActivity(),jsonObject ){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(requestReturn.isError()){
                    Log.d("adsf", "erriooooroorororoor");
                }
                createList(requestReturn.getResponse());
            }
        };
        String url = APIURL + "contains";
        request.execute(url);
        Log.d("adsf", "asdfasdfasdf");
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

    void createList(String json){
        Log.d("adsf", "asdf");
//        Log.d("asdfasdf", json);
        try{
            JSONArray reader = new JSONArray(json);
            for(int x = 0; x<reader.length(); x++){
                JSONObject item = reader.getJSONObject(x);
                Toast.makeText(activity, "id is"+item.get("Id")+"name is "+item.get("Name"), Toast.LENGTH_LONG).show();
                Log.d("adsf","id is"+item.get("Id")+"name is "+item.get("Name"));
            }
        }catch (JSONException e){
            Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.d("asdf", e.getLocalizedMessage());
            Log.d("asdf", e.getStackTrace().toString());
            Log.d("adsf", json);

            //Shit, vincent did a shit job
        }
    }



}


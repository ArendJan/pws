package com.svshizzle.pws.smartfridge.api;

import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.activity.Login;
import com.svshizzle.pws.smartfridge.activity.MainActivity;
import com.svshizzle.pws.smartfridge.model.Item;
import com.svshizzle.pws.smartfridge.request.RequestClass;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Arend-Jan on 5-11-2016.
 */

public class Smartfridge {
    Activity activity;
    private String userid = "";
    private String apiUrl = "";
    private boolean signedIn = false;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }



    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    public String CONTAINS = "apicontains";

    public Smartfridge(){}
    public Smartfridge(Activity activity){
        this.activity = activity;
        init();
    }
    public void init(){
        signedIn = SmartfridgeSave.getSignedin(activity);

        if(signedIn) {


            apiUrl = SmartfridgeSave.getAPIURL(activity);
            userid = SmartfridgeSave.getUserId(activity);
            return;
        }
        else{
            userid = "";
            apiUrl = "";
            Log.d("notsignedinin", "oops");
        }
    }

    public boolean isSignedin(){
        return  signedIn;
    }


    public void contains(){
        JSONObject jsonObject = new JSONObject();
        try {

            Log.d("userid", userid);

            jsonObject.put("UserId", userid);

        }catch (JSONException e ){
        }

        RequestClassPost request = new RequestClassPost(activity,jsonObject ){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(requestReturn.isError()){
                    containsError(requestReturn.getResponse());
                }else {
                    ArrayList<Item> items = new ArrayList<Item>();
                    try {

                        Log.d("asdf", requestReturn.getResponse());
                        JSONArray reader = new JSONArray(requestReturn.getResponse());
                        for (int x = 0; x < reader.length(); x++) {

                            JSONObject object = reader.getJSONObject(x);
                            Item item = new Item();
                            item.loadFromJson(object);
                            items.add(item);
                        }
                    }catch (JSONException e){
                        containsError(e.getLocalizedMessage());
                        return;
                    }
                    containsDone(items);

                }
            }
        };
        String url = "http://pws.svshizzle.com/api/contains";
        request.execute(url);
    }

    public void containsDone(ArrayList<Item> items){
        Log.d("containsDone", "Not overridden");
    }
    public void containsError(String e) {
        Log.d("containsError", "Not overridden");
        if (e != null) {
            Log.d("error=", e);
        }
    }

    public void setSettings(String url, String uid){
        this.userid = uid;
        this.apiUrl = url;
        this.signedIn = true;
        SmartfridgeSave.setSignedin(activity, true);
        SmartfridgeSave.setUserId(activity, uid);
        SmartfridgeSave.setAPIURL(activity, apiUrl);

        if(SmartfridgeSave.getAPIURL(activity).equals(apiUrl)){
            Log.d("hehe", "eindelijk!!!");
        }
        else{
            Log.d("hehe", "kuuuuuuuuuuuuuuuuuuuuuuuuuuutttttttttttttttt");
        }
    }


    public void changeItem(String change, String barcode){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Barcode", barcode);
            jsonObject.put("Action", change);
        }catch (JSONException e ){
        }
        RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(!requestReturn.isError()) {
                    Log.d("return", requestReturn.getResponse());
                    JSONObject object;
                    try {


                        object = new JSONObject(requestReturn.getResponse());
                    }catch (JSONException e){
                        changeItemError(e.getLocalizedMessage());
                        return;
                    }

                    changeItemDone(new Item().loadFromJson(object));
                }else{
                    changeItemError(requestReturn.getResponse());
                }
            }
        };
        requestClassPost.execute(apiUrl + "itemChange");

    }

    public void changeItemDone(Item item){

    }
    public void changeItemError(String e){

    }


    public void changeTitle(String title, String barcode){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Barcode", barcode);
            jsonObject.put("Title", title);
        }catch (JSONException e ){
        }
        RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(!requestReturn.isError()) {
                    Log.d("return", requestReturn.getResponse());
                    JSONObject object = new JSONObject();
                    try {


                        object = new JSONObject(requestReturn.getResponse());
                    }catch (JSONException e){}

                    changeTitleDone(new Item().loadFromJson(object));
                }else{
                    changeTitleError(requestReturn.getResponse());
                }
            }
        };
        requestClassPost.execute(apiUrl + "titleChange");
    }
    public void changeTitleDone(Item item){

    }
    public void changeTitleError(String e){

    }



}

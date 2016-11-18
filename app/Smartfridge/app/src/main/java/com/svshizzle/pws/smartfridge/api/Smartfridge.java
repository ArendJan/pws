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
import com.svshizzle.pws.smartfridge.model.LogItem;
import com.svshizzle.pws.smartfridge.request.RequestClass;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Arend-Jan on 5-11-2016.
 */

public class Smartfridge {
    Activity activity;
    private String userid = "";
    private String apiUrl = "";
    private boolean signedIn = false;

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
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

                    try {

                        Log.d("asdf", requestReturn.getResponse());
                        containsDone(processContains(requestReturn.getResponse()));
                        if(isJSONValid(requestReturn.getResponse())) {
                            SmartfridgeSave.setContainsBackup(activity, requestReturn.getResponse());
                        }

                    }catch (JSONException e){
                        containsError(e.getLocalizedMessage());

                    }


                }
            }
        };
        String url = "http://pws.svshizzle.com/api/contains";
        request.execute(url);
    }
    public ArrayList<Item> processContains(String output)throws JSONException{
        ArrayList<Item> items = new ArrayList<Item>();
        JSONArray reader = new JSONArray(output);
        for (int x = 0; x < reader.length(); x++) {

            JSONObject object = reader.getJSONObject(x);
            Item item = new Item();
            item.loadFromJson(object);
            items.add(item);
        }
        return items;
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


    public void printJob(String text){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Type", "text");
            jsonObject.put("Text", text);
        }catch (JSONException e ){
        }
        RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(!requestReturn.isError()) {
                    Log.d("return", requestReturn.getResponse());


                    printJobDone();
                }else{
                    printJobError(requestReturn.getResponse());
                }
            }
        };
        requestClassPost.execute(apiUrl + "addJob");
    }

    public void printJobError(String e){

    }
    public void printJobDone(){

    }

    public void shutdown(){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Type", "shutdown");
            jsonObject.put("Text", "");
            jsonObject.put("List", "");
            jsonObject.put("barcode", "");

        }catch (JSONException e ){
        }
        RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(!requestReturn.isError()) {

                    shutdownDone();
                }else{
                    shutdownError(requestReturn.getResponse());
                }
            }
        };
        requestClassPost.execute(apiUrl + "addJob");
    }
    public void shutdownDone(){}

    public void shutdownError(String e){

    }

    public void restart(){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Type", "restart");
            jsonObject.put("Text", "");
            jsonObject.put("List", "");
            jsonObject.put("barcode", "");

        }catch (JSONException e ){
        }
        RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                if(!requestReturn.isError()) {

                    restartDone();
                }else{
                    restartError(requestReturn.getResponse());
                }
            }
        };
        requestClassPost.execute(apiUrl + "addJob");
    }
    public void restartDone(){

    }
    public void restartError(String e){

    }

    public void barcode(String barcode){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Type", "barcode");
            jsonObject.put("Barcode", barcode);
            jsonObject.put("Text", "");
            jsonObject.put("List", "");

        }catch (JSONException e ){
        }
        RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                Log.d("barcode", requestReturn.getResponse());
                if(!requestReturn.isError()) {

                    barcodeDone();
                }else{
                    barcodeError(requestReturn.getResponse());
                }
            }
        };
        requestClassPost.execute(apiUrl + "addJob");
    }

    public void barcodeDone(){

    }
    public void barcodeError(String e){

    }

    public String createBarcode(){
        int[] numbers = new int[13];
        Random random = new Random();
        for(int x =0; x<12; x++){
            numbers[x] = random.nextInt(10);

        }
        int evensum = numbers[0] + numbers[2] + numbers[4] + numbers[6] + numbers[8] + numbers[10];
        int unevensum = numbers[1] + numbers[3] + numbers[5] + numbers[7] + numbers[9] + numbers[11];
        numbers[12] = (10-((evensum + unevensum*3) %10)) %10;
        return IntArrayToString(numbers);
    }
    private String IntArrayToString(int[] array) {
        String strRet="";
        for(int i : array) {
            strRet+=Integer.toString(i);
        }
        return strRet;
    }

    public void createItem(String barcode, String title){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Title", title);
            jsonObject.put("Barcode", barcode);

        }catch (JSONException e ){
        }
        RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);
                Log.d("barcode", requestReturn.getResponse());
                if(!requestReturn.isError()) {

                    createItemDone();
                }else{
                    createItemError(requestReturn.getResponse());
                }
            }
        };
        requestClassPost.execute(apiUrl + "createItem");
    }
    public void createItemDone(){

    }
    public void createItemError(String e){

    }
    public void getLog(){
        getLog("DESC");
    }
    public void getLog(String order){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserId",userid);
            jsonObject.put("Sort", order);

        }catch (JSONException e ){
        }
        final RequestClassPost requestClassPost = new RequestClassPost(activity, jsonObject){
            @Override
            protected void onPostExecute(RequestReturn requestReturn) {
                super.onPostExecute(requestReturn);

                if(!requestReturn.isError()) {

                    try {
                        ArrayList<LogItem> logs = processLog(requestReturn.getResponse());
                        getLogDone(logs);
                        if(isJSONValid(requestReturn.getResponse())) {
                            SmartfridgeSave.setLogBackup(activity, requestReturn.getResponse());
                        }
                    }catch (JSONException e){
                        getLogError(e.getLocalizedMessage());

                    }
                    }else{
                        getLogError(requestReturn.getResponse());
                    }
                }

        };
        requestClassPost.execute(apiUrl + "getLog");
    }
    public void getLogDone(ArrayList<LogItem> items){

    }
    public void getLogError(String e){

    }

    public ArrayList<LogItem> processLog(String output) throws JSONException{
        ArrayList<LogItem> logs = new ArrayList<LogItem>();
            JSONArray reader = new JSONArray(output);
            for (int x = 0; x < reader.length(); x++) {

                JSONObject object = reader.getJSONObject(x);
                LogItem log = new LogItem();
                log.loadFromJson(object);
                logs.add(log);
            }
return logs;
    }


}

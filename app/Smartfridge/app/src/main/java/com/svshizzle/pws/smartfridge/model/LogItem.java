package com.svshizzle.pws.smartfridge.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arend-Jan on 14-11-2016.
 */

public class LogItem {
    private int id = 0;
    private String time = "";
    private String script = "";
    private JSONObject parameters;
    private String userId = "";
    private JSONObject jobDetails;
    private JSONObject itemDetails;

    public LogItem() {
    }

    public LogItem(int id, String time, String script, JSONObject parameters, String userId) {
        this.id = id;
        this.time = time;
        this.script = script;
        this.parameters = parameters;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public JSONObject getParameters() {
        return parameters;
    }

    public void setParameters(JSONObject parameters) {
        this.parameters = parameters;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LogItem loadFromJson(JSONObject object){
        try {


            this.time = object.getString("Time");
            this.id = object.getInt("ID");
            this.script = object.getString("Script");
            this.parameters = object.getJSONObject("Params");
            this.userId = object.getString("UserId");
            Log.d("script=", script);
            if("markJob.php".equals(script)){

                this.jobDetails = object.getJSONObject("JobDetails");
            }
            if("itemChange.php".equals(script)){
                this.itemDetails = object.getJSONObject("ItemDetails");
            }

            return this;
        }catch (JSONException e){
            Log.d("what?", e.getLocalizedMessage());
        }
        return this;
    }

    public JSONObject getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JSONObject jobDetails) {
        this.jobDetails = jobDetails;
    }

    public JSONObject getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(JSONObject itemDetails) {
        this.itemDetails = itemDetails;
    }
}

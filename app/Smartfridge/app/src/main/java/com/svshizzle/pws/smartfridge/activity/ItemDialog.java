package com.svshizzle.pws.smartfridge.activity;

/**
 * Created by Arend-Jan on 29-10-2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;


import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.model.Item;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemDialog extends Dialog implements
        android.view.View.OnClickListener {
    String APIURL = "";
    String UserId = "";
    public Activity c;
    public Item item;

    public ItemDialog(Activity a, Item item) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_dialog);
        SharedPreferences sharedPreferences =  c.getSharedPreferences(c.getString(R.string.SharedPreferencesName), 0);
        UserId = sharedPreferences.getString(c.getString(R.string.SharedPreferencesUserId), "");
        APIURL = sharedPreferences.getString(c.getString(R.string.SharedPreferencesAPIURL), "");

        TextView openNumberView = (TextView) findViewById(R.id.ItemDialogOpenAmount);
        TextView closedNumberView = (TextView) findViewById(R.id.ItemDialogClosedAmount);
        Button openIncrease = (Button) findViewById(R.id.ItemDialogOpenIncreaseButton);
        Button openDecrease = (Button) findViewById(R.id.ItemDialogOpenDecreaseButton);

        openDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("UserId", UserId);
                    jsonObject.put("Barcode", item.getBarcode());
                    jsonObject.put("Action", "del");
                }catch (JSONException e ){
                }
                RequestClassPost requestClassPost = new RequestClassPost(c, jsonObject){
                    @Override
                    protected void onPostExecute(RequestReturn requestReturn) {
                        super.onPostExecute(requestReturn);
                        Log.d("return", requestReturn.getResponse());
                        Log.d("asdf", "geenreturn");
                    }
                };
                requestClassPost.execute(APIURL + "itemChange");

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addHoursYes:

                dismiss();
                break;
            case R.id.addHoursNo:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}


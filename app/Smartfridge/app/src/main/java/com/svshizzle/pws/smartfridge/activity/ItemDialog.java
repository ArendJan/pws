package com.svshizzle.pws.smartfridge.activity;

/**
 * Created by Arend-Jan on 29-10-2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;


import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.model.Item;

public class ItemDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;

    public ItemDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_dialog);
        Item item = new Gson().fromJson(savedInstanceState.getString("item"), Item.class);
        


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


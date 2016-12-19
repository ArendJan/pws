package com.svshizzle.pws.smartfridge.activity;

/**
 * Created by Arend-Jan on 29-10-2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;


import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.api.Smartfridge;
import com.svshizzle.pws.smartfridge.model.Item;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public static Item item;

    public ItemDialog(Activity a, Item item) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_dialog);
        Button openIncrease = (Button) findViewById(R.id.ItemDialogOpenIncreaseButton);
        Button openDecrease = (Button) findViewById(R.id.ItemDialogOpenDecreaseButton);
        Button closedIncrease = (Button) findViewById(R.id.ItemDialogClosedIncreaseButton);
        Button closedDecrease = (Button) findViewById(R.id.ItemDialogClosedDecreaseButton);
        Button titleChange = (Button) findViewById(R.id.ItemDialogTitleButton);
        Button okButton = (Button) findViewById(R.id.ItemDialogOk);
        okButton.setOnClickListener(this);
        titleChange.setOnClickListener(this);
        openDecrease.setOnClickListener(this);
        openIncrease.setOnClickListener(this);
        closedDecrease.setOnClickListener(this);
        closedIncrease.setOnClickListener(this);

        createScreen(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ItemDialogOk:
                Log.d("ok", "of je sok");
                changeText();
                dismiss();
                break;
            case R.id.ItemDialogOpenDecreaseButton:
                buttonChange("delOpen");


                break;
            case R.id.ItemDialogOpenIncreaseButton:
                buttonChange("open");
                break;
            case R.id.ItemDialogClosedDecreaseButton:
                buttonChange("delClosed");
                break;
            case R.id.ItemDialogClosedIncreaseButton:
                buttonChange("add");
                break;
            case R.id.ItemDialogTitleButton:
                changeText();
                break;
            default:
                Log.d("oops", "default is not ok");
                break;
        }

    }

    private void buttonChange(final String action){

        Smartfridge smartfridge = new Smartfridge(activity){
            @Override
            public void changeItemDone(Item item) {
                createScreen(item);
            }

            @Override
            public void changeItemError(String e) {
                Toast.makeText(activity, activity.getString(R.string.noInternetMessage)+e, Toast.LENGTH_LONG).show();
            }
        };
        smartfridge.changeItem(action, item.getBarcode());


    }
    private void createScreen(Item item){
        TextView openNumberView = (TextView) findViewById(R.id.ItemDialogOpenAmount);
        TextView closedNumberView = (TextView) findViewById(R.id.ItemDialogClosedAmount);
        TextView title = (TextView) findViewById(R.id.ItemDialogTitle);
        TextView barcode = (TextView) findViewById(R.id.ItemDialogBarcode);
        EditText titleEdit = (EditText) findViewById(R.id.ItemDialogTitleEdit);
        if(openNumberView == null || closedNumberView == null || title == null || barcode == null || titleEdit == null){
            return;
        }
        titleEdit.setText(item.getTitle());
        title.setText(item.getTitle());
        openNumberView.setText(Integer.toString(item.getOpen()));
        closedNumberView.setText(Integer.toString(item.getClosed()));
        barcode.setText(item.getBarcode());

    }

    private void changeText(){
        ViewSwitcher viewSwitcher = (ViewSwitcher) findViewById(R.id.ItemDialogViewSwitcher);
        TextView textView = (TextView) findViewById(R.id.ItemDialogTitle);
        EditText editText = (EditText) findViewById(R.id.ItemDialogTitleEdit);

        if(viewSwitcher.getCurrentView() != editText ){
            viewSwitcher.showPrevious();
        }else if(viewSwitcher.getCurrentView() != textView) {
            textView.setText(editText.getText());
            viewSwitcher.showNext();
            if (!editText.getText().toString().equals(item.getTitle())) {
                Smartfridge smartfridge = new Smartfridge(activity) {
                    @Override
                    public void changeTitleDone(Item item) {
                        ItemDialog.item = item;
                        createScreen(item);
                    }


                };
                smartfridge.changeTitle(editText.getText().toString(), item.getBarcode());
            }
        }
    }
}


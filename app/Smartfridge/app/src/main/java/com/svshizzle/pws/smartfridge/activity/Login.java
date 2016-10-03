package com.svshizzle.pws.smartfridge.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.request.RequestClass;
import com.svshizzle.pws.smartfridge.request.RequestReturn;

public class Login extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button button =(Button) findViewById(R.id.loginOkButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOnClick();
            }
        });
    }


    void buttonOnClick(){
        progressDialog = ProgressDialog.show(this,getResources().getString(R.string.loginDialogTitle), getResources().getString(R.string.loginDialogTextAPIURLCheck), false);
        //TODO:Dialog
        EditText UserId =(EditText) findViewById(R.id.loginUserIdField);
        String userId = UserId.getText().toString();
        if(userId.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.loginUserIdEmpty), Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
        final EditText APIURL = (EditText) findViewById(R.id.loginAPIURLField);
        final String apiurl = APIURL.getText().toString();
        if(userId.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.loginAPIURLEmpty), Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

        //TODO: check server api, /api/checkServer
        RequestClass requestCheck = new RequestClass(this){
            @Override
            protected void onPostExecute(RequestReturn result) {
                super.onPostExecute(result);
                if(!result.isError() && result.getResponse().equals("y")){
                    requestCheckUserId(apiurl);
                }
                else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginAPIURLFaulty), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        };
        String url = apiurl + "serverCheck";
        requestCheck.execute(url);

        //TODO: check UserId.


        //TODO: register the phone to the userId for notifications.

    }
    void requestCheckUserId(String apiurl){
        progressDialog.setMessage(getResources().getString(R.string.loginDialogUserIdCheck));
        RequestClass requestCheckUserId = new RequestClass(this){
            @Override
            protected void onPostExecute(RequestReturn result) {
                super.onPostExecute(result);
                if(!result.isError() && result.getResponse().equals("y")){
                    //TODO:fire register notifications
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginUserIdFaulty), Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
            }
        };
        requestCheckUserId.execute(apiurl+"useridCheck");

    }

}

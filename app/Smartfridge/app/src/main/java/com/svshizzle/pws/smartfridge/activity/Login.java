package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.api.Smartfridge;
import com.svshizzle.pws.smartfridge.request.RequestClass;
import com.svshizzle.pws.smartfridge.request.RequestClassPost;
import com.svshizzle.pws.smartfridge.request.RequestReturn;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    ProgressDialog progressDialog;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = this;
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
        EditText UserId =(EditText) findViewById(R.id.loginUserIdField);
        String userId = UserId.getText().toString();

        final EditText APIURL = (EditText) findViewById(R.id.loginAPIURLField);
        final String apiurl = APIURL.getText().toString();
        new CheckLogin().execute(apiurl, userId);

    }

    private class checkReturn{
        boolean error = false;
        String errorText = "";
        String Userid = "";
        String APIURL = "";

        String getErrorText() {
            return errorText;
        }

        checkReturn(boolean error, String errorText) {

            this.error = error;
            this.errorText = errorText;
        }
        checkReturn(boolean error, String errorText, String userid, String apiurl) {

            this.error = error;
            this.errorText = errorText;
            this.Userid = userid;
            this.APIURL = apiurl;

        }
        boolean isError() {
            return error;
        }

    }

    private class CheckLogin extends AsyncTask<String, String, checkReturn>{
        @Override
        protected checkReturn doInBackground(String... strings) {
            publishProgress(getResources().getString(R.string.loginDialogTextAPIURLCheck));
            String APIURL = strings[0];
            String UserId = strings[1];
            if(APIURL.isEmpty()){
                return new checkReturn(true, getResources().getString(R.string.loginAPIURLEmpty));

            }
            if(UserId.isEmpty()){
                return new checkReturn(true, getResources().getString(R.string.loginUserIdEmpty));

            }






            RequestClass requestClass = new RequestClass();
            RequestReturn output = requestClass.getData(APIURL + getResources().getString(R.string.APIServerCheck));



            if(output.isError()|| !output.getResponse().equals("y")){

                return new checkReturn(true, getResources().getString(R.string.loginAPIURLFaulty));
            }
            publishProgress(getResources().getString(R.string.loginDialogUserIdCheck));
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("UserId",UserId);

            }catch (JSONException e ){
                //TODO:exception
            }
            RequestClassPost requestClassPost = new RequestClassPost(getApplication(), jsonObject);
            output = requestClassPost.doInBackground(APIURL + getResources().getString(R.string.APIUserIdCheck));

            boolean correctUserId;
            try {
                jsonObject = new JSONObject(output.getResponse());
                correctUserId = jsonObject.getString("check").equals("y");
            }catch (JSONException exception){

                return new checkReturn(true, getResources().getString(R.string.loginUserIdFaulty));
            }
            if(output.isError() || !correctUserId){

                return new checkReturn(true, getResources().getString(R.string.loginUserIdFaulty));
            }

            return new checkReturn(false,"", UserId, APIURL);
        }



        @Override
        protected void onPostExecute(checkReturn s) {
            super.onPostExecute(s);
            if(s.isError()){
                Toast.makeText(getApplicationContext(), s.getErrorText(), Toast.LENGTH_LONG).show();

            }else{
                Smartfridge smartfridge = new Smartfridge(activity);
                smartfridge.setSettings(s.APIURL, s.Userid);
                Intent intent = new Intent(Login.this, MainActivity.class);

                Login.this.startActivity(intent);
                finish();
            }
            progressDialog.dismiss();
            //TODO:als het klaar is.

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);
        }
    }
}

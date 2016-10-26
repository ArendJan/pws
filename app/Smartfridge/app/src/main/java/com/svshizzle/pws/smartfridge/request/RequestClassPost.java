package com.svshizzle.pws.smartfridge.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Arend-Jan on 26-10-2016.
 * ____  ____  _____ _      ____           _  ____  _
 * /  _ \/  __\/  __// \  /|/  _ \         / |/  _ \/ \  /|
 * | / \||  \/||  \  | |\ ||| | \|_____    | || / \|| |\ ||
 * | |-|||    /|  /_ | | \||| |_/|\____\/\_| || |-||| | \||
 * \_/ \|\_/\_\\____\\_/  \|\____/      \____/\_/ \|\_/  \|
 */
public class RequestClassPost extends AsyncTask<String, String, RequestReturn> {


    private boolean error = false; //If there was an error.
    private Context activity;
    private JSONObject jsonObject;
    public RequestClassPost(Context x, JSONObject jsonObject){
        this.activity = x;
        this.jsonObject = jsonObject;
    }
    public RequestReturn getData(String html){
        String responseString ="";

        try {
            JSONObject postdata = new JSONObject();
            postdata.put("JSON", "{\"UserId\":\"kaasblokje\"}");
            Log.d("postdata", postdata.toString());
            URL url;
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL (html);

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestProperty( "Content-type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty( "Accept", "*/*" );

            urlConn.setDoInput (true);
            urlConn.setDoOutput (true);
            urlConn.setUseCaches (false);
            urlConn.setRequestMethod("POST");
            urlConn.connect();
//Create JSONObject here
            // Send POST output.
            printout = new DataOutputStream(urlConn.getOutputStream ());
            printout.writeBytes(URLEncoder.encode(postdata.toString(),"UTF-8"));
            printout.flush ();
            printout.close ();


            BufferedReader br = new BufferedReader(new InputStreamReader((urlConn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);

            }
            responseString = sb.toString();

            //If something goes wrong.
        } catch (Exception e) {
            error = true;
            responseString = e.getLocalizedMessage();
            Log.d("errorPOST", responseString);

        }
        return new RequestReturn(responseString, error);
    }
    @Override
    protected RequestReturn doInBackground(String... uri) {


        return getData(uri[0]);

    }


}


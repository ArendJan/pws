package com.svshizzle.pws.smartfridge.request;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Arend-Jan on 29-3-2016.
 * ____  ____  _____ _      ____           _  ____  _
 * /  _ \/  __\/  __// \  /|/  _ \         / |/  _ \/ \  /|
 * | / \||  \/||  \  | |\ ||| | \|_____    | || / \|| |\ ||
 * | |-|||    /|  /_ | | \||| |_/|\____\/\_| || |-||| | \||
 * \_/ \|\_/\_\\____\\_/  \|\____/      \____/\_/ \|\_/  \|
 */
public class RequestClass extends AsyncTask<String, String, RequestReturn> {


        private boolean error = false; //If there was an error.

 public RequestClass(){

 }
    public RequestReturn getData(String html){
        String responseString ="";

        try {
            URLConnection connection = new URL(html).openConnection();
            connection.setConnectTimeout(6000);
            connection.setReadTimeout(6000);
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);

            }
            responseString = sb.toString();

            //If something goes wrong.
        } catch (Exception e) {
            error = true;

        }
        return new RequestReturn(responseString, error);
    }
        @Override
        protected RequestReturn doInBackground(String... uri) {



                throw new IllegalArgumentException();


            //return getData(uri[0]);

        }


    }


package com.svshizzle.pws.smartfridge.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    public static String requestUrl(String url, String postParameters)
            {



        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(url);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();

            Log.d("asdfasdfxzcv", postParameters);
            // handle POST parameters
            if (postParameters != null) {

                Log.d("llel", "dit");

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setFixedLengthStreamingMode(
                        postParameters.getBytes().length);
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                //send the POST out
                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                out.print(postParameters);
                out.close();
            }

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                // throw some exception
            }


                InputStream in =
                        new BufferedInputStream(urlConnection.getInputStream());
                return getResponseText(in);



        } catch (MalformedURLException e) {
            Log.d("kut", e.getLocalizedMessage());
            // handle invalid URL
        } catch (SocketTimeoutException e) {
            Log.d("kut", e.getLocalizedMessage());// hadle timeout
        } catch (IOException e) {
            Log.d("kut", e.getLocalizedMessage());// handle I/0
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
                Log.d("kut", "Fuckkkkkkkkkkk");
        return null;
    }
    @Override
    protected RequestReturn doInBackground(String... uri) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("JSON",jsonObject.toString());

        return new RequestReturn(requestUrl(uri[0], createQueryStringForParameters(map)), false);

    }
    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        Log.d("getres", "scanner");
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    public static String createQueryStringForParameters(Map<String, String> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (String parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }

                parametersAsQueryString.append(parameterName)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode(
                                parameters.get(parameterName)));

                firstParameter = false;
            }
        }
        Log.d("anoes",parametersAsQueryString.toString());
        return parametersAsQueryString.toString();
    }

}


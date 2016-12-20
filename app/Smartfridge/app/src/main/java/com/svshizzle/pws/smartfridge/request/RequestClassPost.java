package com.svshizzle.pws.smartfridge.request;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    private Context activity;
    private JSONObject jsonObject;
    public RequestClassPost(Context x, JSONObject jsonObject){
        this.activity = x;
        this.jsonObject = jsonObject;
    }

    private static String requestUrl(String url, String postParameters) {

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(url);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();


            // handle POST parameters
            if (postParameters != null) {



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
                //TODO:exceptuino
            }


                InputStream in =
                        new BufferedInputStream(urlConnection.getInputStream());
                return getResponseText(in);



        } catch (IOException ignored) {
            //TODO:exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
                //TODO:GEEN NULLL
    }
    @Override
    public  RequestReturn doInBackground(String... uri) {
        if(!isNetworkAvailable(activity)){
            return new RequestReturn("No internet connection", true);
        }
        Map<String, String> map = new HashMap<>();
        map.put("JSON",jsonObject.toString());
        String output = requestUrl(uri[0], createQueryStringForParameters(map));
        if(output == null){
            return new RequestReturn("null", true);
        }
        return new RequestReturn(output, false);

    }
    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    private static String createQueryStringForParameters(Map<String, String> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (String parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }
                try {


                    parametersAsQueryString.append(parameterName)
                            .append(PARAMETER_EQUALS_CHAR)
                            .append(URLEncoder.encode(
                                    parameters.get(parameterName), "UTF-8"));
                }catch (UnsupportedEncodingException ignored){

                }
                firstParameter = false;
            }
        }

        return parametersAsQueryString.toString();
    }
    private boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}

